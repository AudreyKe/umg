package com.weshare.umg.service.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.weshare.id.UniqueIdUtils;
import com.weshare.json.JsonUtils;
import com.weshare.umg.config.ExecuterStore;
import com.weshare.umg.config.RocketMqConfig;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.entity.*;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.MqRequest;
import com.weshare.umg.request.sms.QuerySmsSendRequest;
import com.weshare.umg.request.SmsAcRequest;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.response.SmsSendResponse;
import com.weshare.umg.response.send.SmsSendApiResponse;
import com.weshare.umg.response.send.SmsSendApiResponseDTO;
import com.weshare.umg.service.entity.*;
import com.weshare.umg.service.rocketmq.UmgProducer;
import com.weshare.umg.util.*;
import com.weshare.wsmq.api.destination.Destination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 10:36
 * @Description:
 */
@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    SmsSpService smsSpService;

    @Autowired
    TemplateService templateService;

    @Autowired
    UmgProducer umgProducer;

    @Autowired
    SmsEntityService smsEntityService;

    @Autowired
    SmsPolicy smsPolicy;

    @Autowired
    SmsConfig smsConfig;

    @Autowired
    AppService appService;

    @Autowired
    SmsSendDetailsService smsSendDetailsService;

    @Autowired
    SmsDetailsService smsDetailsService;

    /**接收 sms 数据*/
    public CodeEnum acceptHttpSms(SmsAcRequest acRequest) {
        CodeEnum codeEnum;
        try {
            codeEnum = acceptDefaultSms(acRequest);
            if(codeEnum.equals(CodeEnum.SUCCESS_CODE)){
                Destination destination = RocketMqConfig.destinationMap.get(RocketMqConfig.SMS_REQUEST_DEST);
                umgProducer.pushMQ(destination,acRequest.getUniqueId(),JsonUtils.convertObjectToJSON(acRequest));
            }
        }catch (UmgException ue){
            codeEnum = CodeEnum.getCodeEnum(ue.getCode());
            logger.error("accept http sms error! uniqueId={}, appcode={},custId={}",acRequest.getUniqueId(),acRequest.getAppCode(),acRequest.getAppCode(),ue);
        }
        if(!codeEnum.equals(CodeEnum.SUCCESS_CODE)){
            smsEntityService.updateExce(acRequest.getUniqueId(),SendStatus.EXCE.getStatus(),codeEnum.getMessage());
        }
        return codeEnum;
    }

    /**接收 sms 数据*/
    public CodeEnum acceptDefaultSms(SmsAcRequest acRequest) {
        SmsEntity entity = RequestUtils.initSmsEntity(acRequest,smsConfig);
        CodeEnum codeEnum  = validateSmsSendRequest(acRequest);
        try {
            if(codeEnum.equals(CodeEnum.SUCCESS_CODE)){
                TemplateEntity templateEntity = templateService.getTemplateByCode(acRequest.getTplCode());
                RequestUtils.addSmsEntity(acRequest,templateEntity,entity);
            }
        }catch (Exception e){
            if(e instanceof UmgException){
                UmgException ug = (UmgException)e;
                codeEnum = CodeEnum.getCodeEnum(ug.getCode());
            }else {
                codeEnum = CodeEnum.SYSTEM_ERROR;
            }
            logger.error("accept Default sms error! uniqueId={}, appcode={},custId={}",acRequest.getUniqueId(),acRequest.getAppCode(),acRequest.getAppCode(),e);
        }
        if(!codeEnum.equals(CodeEnum.SUCCESS_CODE)){
            entity.setErrorMsg(codeEnum.getMessage());
            entity.setStatus(SendStatus.EXCE.getStatus());
        }
        smsEntityService.save(entity);
        return codeEnum;
    }


    /**校验 sign*/
    public void validateSign(Long timestamp, String appCode,String sign)throws UmgException {
        if(StringUtils.isBlank(appCode)){
            logger.error("{}!",CodeEnum.APPCODE_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.APPCODE_ISEMPTY);
        }
        AppEntity appEntity = appService.getAppByCode(appCode);
        if(appEntity == null){
            logger.error("{}!",CodeEnum.APPCODE_NOT_EXISTS.getMessage());
            throw new UmgException(CodeEnum.APPCODE_NOT_EXISTS);
        }

        if(StringUtils.isBlank(sign)){
            logger.error("{}!",CodeEnum.SIGN_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.SIGN_ISEMPTY);
        }

        if(timestamp == null){
            logger.error("{}!",CodeEnum.TIMESTAMP_ISEMPTY.getMessage());
            throw new UmgException(CodeEnum.TIMESTAMP_ISEMPTY);
        }

        String umgSign = EncryptUtils.sha256(timestamp + appEntity.getToken());
        if(!umgSign.equals(sign)){
            logger.error("{}!",CodeEnum.SIGN_ERROR.getMessage());
            throw new UmgException(CodeEnum.SIGN_ERROR);
        }
    }

    /**校验 sms 数据*/
    public CodeEnum validateSmsSendRequest(SmsAcRequest acRequest) {
        CodeEnum result = CodeEnum.SUCCESS_CODE;
        try {
            ValidaUtils.validateSmsRequest(acRequest);
            TemplateEntity templateEntity = templateService.getTemplateByCode(acRequest.getTplCode());
            ValidaUtils.validateSmsTpl(acRequest,templateEntity);
            validateSign(acRequest.getTimestamp(),acRequest.getAppCode(),acRequest.getSign());
        }catch (UmgException e){
            result = CodeEnum.getCodeEnum(e.getCode());
            logger.error("validate sms error! uniqueId={}, appcode={},custId={}",acRequest.getUniqueId(),acRequest.getAppCode(),acRequest.getCustId(),e);
        }
        return result;
    }

    /**接收 mq sms 数据*/
    public void acceptMQSms(String body){
        CodeEnum codeEnum = CodeEnum.SUCCESS_CODE;
        String  uniqueId = null;
        try {
            SmsAcRequest acRequest = JsonUtils.convertJsonToObject(body, SmsAcRequest.class);
            if(StringUtils.isBlank(acRequest.getUniqueId())){
                acRequest.setUniqueId(UniqueIdUtils.createUniqueId());
            }
            uniqueId = acRequest.getUniqueId();
            codeEnum = acceptDefaultSms(acRequest);

            if(codeEnum.equals(CodeEnum.SUCCESS_CODE)){
                sendSms(acRequest);
            }
        }catch (UmgException ue){
            codeEnum = CodeEnum.getCodeEnum(ue.getCode());
            logger.error("accept mq sms error! uniqueId={},object={}",uniqueId,body,ue);
        }catch (Exception e){
            codeEnum = CodeEnum.SYSTEM_ERROR;
            logger.error("accept mq sms error! uniqueId={},object={}",uniqueId,body,e);
        }
        if(!codeEnum.equals(CodeEnum.SUCCESS_CODE)){
            smsEntityService.updateExce(uniqueId,SendStatus.EXCE.getStatus(),codeEnum.getMessage());
        }

    }

    /**发 sms */
    private void sendSms(SmsAcRequest acRequest) throws UmgException {
        try{
            TemplateEntity templateEntity = templateService.getTemplateByCode(acRequest.getTplCode());
            List<SmsSpEntity> list = smsSpService.fandEnableAll();
            SmsRequestBo smsRequestBo = RequestUtils.handleSmsRequest(acRequest,templateEntity,list);
            sendSms(smsRequestBo);
        }catch (UmgException ue){
            throw ue;
        } catch (Exception e){
            logger.error("sendSms sms error! uniqueId={}",acRequest.getUniqueId(),e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    /**发 sms */
    private void sendSms(SmsRequestBo smsRequestBo){
        SmsSendResponse response = new SmsSendResponse();
        Boolean sendSucess = false;

        List<Integer> splist = smsRequestBo.getSPList();
        if(splist==null || splist.size()==0){
            response.getErrMap().put("umg",CodeEnum.SMS_SP_UNENABLE.getMessage());
            logger.error("sendSms 没有sp可用! uid={}",smsRequestBo.getUniqueId());
        }

        Iterator<Integer> it = splist.iterator();
        while(it.hasNext() && !sendSucess) {
            int code = it.next();
            try {
                 List<SmsSendDetails> res = ExecuterStore.EXECUSTORE.get(code).sendSms(smsRequestBo);
                 sendSucess = true;
                 response.setCode(code).setReList(res).setSentDate(new Date());
            }catch (Exception e) {
                sendSucess = false;
                if(e instanceof UmgException){
                    response.getErrMap().put(SmsSp.getByCode(code).getName(),e.getMessage());
                }else{
                    response.getErrMap().put(SmsSp.getByCode(code).getName(),CodeEnum.SYSTEM_ERROR.getMessage());
                }
                logger.error("sendSms executer error! sp={},uniqueId={}",SmsSp.getByCode(code).getName(),smsRequestBo.getUniqueId(),e);
            }
        }
        response.setStatus(sendSucess).setUniqueId(smsRequestBo.getUniqueId());
        smsEntityService.updateSendSmsRes(response);
        smsPolicy.sendRespPolicy(response,smsRequestBo);
    }


    /**查询发送清单*/
    public List<SmsSendApiResponse> querySmsSendDetails(QuerySmsSendRequest sendRequest) throws UmgException{
        String custId = sendRequest.getCustId();
        String appcode = sendRequest.getAppCode();
        validateSign(sendRequest.getTimestamp(),appcode,sendRequest.getSign());
        List<SmsSendApiResponse> list = new ArrayList<>();
        try {

            if(StringUtils.isBlank(custId)){
                logger.error("{}!",CodeEnum.CUSTID_ISEMPTY.getMessage());
                throw new UmgException(CodeEnum.CUSTID_ISEMPTY);
            }
            List<SmsEntity> smsList = smsEntityService.findSmsEntity(custId,appcode);
            List<SmsSendDetails> smsSendDetailsList = smsSendDetailsService.findSmsSendDetails(custId,appcode);
            List<SmsDetails> smsDetails = smsDetailsService.findSmsSendDetails(custId,appcode);
            //各个手机号回执详情清单 key(mobile+resId)  与 SmsSendDetails 关联
            Map<String, SmsDetails> detailsMap = smsDetails.stream().collect(Collectors.toMap(a->(a.getMobile()+a.getResId()), a -> a,(k1,k2)->k1));
            //各个手机号发送详情清单 key(uniqueId) 与 SmsEntity 关联
            Map<String, List<SmsSendApiResponseDTO>> dtoMap =  smsSendDetailsList.stream().map(send->{
                SmsSendApiResponseDTO dto = new SmsSendApiResponseDTO();
                dto.setUniqueId(send.getUniqueId());
                dto.setSentDate(send.getSentDate());
                dto.setDownStatus(DownStatus.WAIT.getStatus());
                String resId = send.getResId();
                String mobile = send.getMobile();
                dto.setResId(resId);
                dto.setMobile(mobile);
                String key = mobile+resId;
                if(detailsMap.containsKey(key)){
                    SmsDetails details = detailsMap.get(key);
                    dto.setDownStatus(details.getStatus());
                    dto.setUserAcceptDate(details.getUserAcceptDate());
                }
                return dto;
            }).collect(Collectors.groupingBy(SmsSendApiResponseDTO::getUniqueId));

            //umg对短信接口接收的请求体 校验，解析等过程信息
            list = smsList.stream().map(sms-> {
                        SmsSendApiResponse apiResponse = new SmsSendApiResponse();
                        int status = sms.getStatus();
                        String uniqueId = sms.getUniqueId();
                        String errorMsg = status == SendStatus.SUEECSS.getStatus() ? null : sms.getErrorMsg();
                        apiResponse.setAcceptDate(sms.getCreateTime());
                        apiResponse.setContent(sms.getContent());
                        apiResponse.setStatus(status);
                        apiResponse.setUniqueId(uniqueId);
                        apiResponse.setErrorMsg(errorMsg);
                        apiResponse.setSpCode(sms.getSpCode());
                        apiResponse.setCustId(sms.getCustId());
                        apiResponse.setAppCode(sms.getAppCode());
                        if(dtoMap.containsKey(uniqueId)){
                            List<SmsSendApiResponseDTO> dtos = dtoMap.get(uniqueId);
                            apiResponse.setDtos(dtos);
                        }
                        return apiResponse;
                    }).collect(Collectors.toList());
        }catch (UmgException ue){
            throw ue;
        }catch (Exception e){
            logger.error("querySmsSendDetails error! custId={}, appcode={},custId={}",sendRequest.getCustId(),sendRequest.getAppCode(),e);
            throw  new UmgException(CodeEnum.SYSTEM_ERROR);
        }

        return list;
    }


    /**接收 sms Report*/
    public void acceptHttpReport(String body,SmsSp smsSp){
        int code = smsSp.getCode();
        List<SmsSpReport> smsSpReports = ExecuterStore.EXECUSTORE.get(code).serializeReport(body);
        smsSpReports.forEach(r->r.setSpCode(smsSp.getCode()));

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(smsSpReports));
        umgProducer.pushSmsDetailsDest(array,MqOperation.SMS_REPORT);
    }
    /**通过接口查询 Report*/
    public void pushHttpReport(){
            try {
                List<SmsSpEntity> list = smsSpService.fandEnableAll();
                for (SmsSpEntity sp : list) {
                    Integer code = sp.getCode();
                    List<SmsSpReport> smsSpReports = ExecuterStore.EXECUSTORE.get(code).getReport();
                    smsSpReports.forEach(r->r.setSpCode(code));

                    JSONArray array = JSONArray.parseArray(JSON.toJSONString(smsSpReports));
                    umgProducer.pushSmsDetailsDest(array,MqOperation.SMS_REPORT);
                }
            }catch (Exception e){
                logger.error("获取报告异常!code={}",e);
            }
    }


    /**接收 mq smsDetailsDest topic*/
    public void acceptMQSmsDetailsDest(String body) throws UmgException {
        MqRequest request = SerializeMQUtils.smsDetailsDeserialize(body);
        MqOperation operation = request.getOperation();
        switch (operation){
            case SMS_REPORT:
                smsPolicy.smsReportPolicy(request.getArray());
                break;
            case SMS_SEND_DETAILS:
                smsPolicy.smsSendDetailsPolicy(request.getArray());
                break;
            default:
                throw new UmgException(CodeEnum.RMQ_OPER_ERROR);
        }
    }



}
