package com.weshare.umg.service.sms;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.weshare.json.JsonUtils;
import com.weshare.umg.config.RocketMqConfig;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.entity.*;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.request.mail.MailRequest;
import com.weshare.umg.request.mail.MailRequestBuilder;
import com.weshare.umg.response.BalanceResponse;
import com.weshare.umg.response.SmsSendResponse;
import com.weshare.umg.service.entity.*;
import com.weshare.umg.service.mail.MailService;
import com.weshare.umg.service.rocketmq.UmgProducer;
import com.weshare.umg.service.sms.montnets.MonRequestUtils;
import com.weshare.umg.util.*;
import com.weshare.wsmq.api.destination.Destination;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 09:54
 * @Description:
 */
@Component
public class SmsPolicy {
    private static final Logger logger = LoggerFactory.getLogger(MonRequestUtils.class);

    @Autowired
    SmsEntityService smsEntityService;

    @Autowired
    UmgProducer umgProducer;

    @Autowired
    SmsSpService smsSpService;

    @Autowired
    SmsDetailsService smsDetailsService;

    @Autowired
    SmsSendDetailsService smsSendDetailsService;

    @Autowired
    SmsReportService smsReportService;

    @Autowired
    SmsConfig smsConfig;

    @Autowired
    MailService mailService;
    /**发送sms后发送结果处理*/
    @Async
    public void sendRespPolicy(SmsSendResponse smsSendResponse, SmsRequestBo smsRequestBo){
        String uniqueId = smsSendResponse.getUniqueId();
        try{
            SmsEntity smsEntity = smsEntityService.querySmsEntity(uniqueId);
            //发送成功
            if(smsSendResponse.getStatus()){
                //发送清单数据 push mq
                List<SmsSendDetails> reList = smsSendResponse.getReList();
                reList.forEach(details->{
                    details.setSpCode(smsSendResponse.getCode());
                    details.setUniqueId(uniqueId);
                    details.setSentDate(smsSendResponse.getSentDate());
                    details.setContent(smsRequestBo.getContent());
                    details.setCustId(smsRequestBo.getCustId());
                    details.setAppCode(smsRequestBo.getAppCode());
                    details.setAcceptDate(smsEntity.getCreateTime());
                });

                JSONArray array = JSONArray.parseArray(JSON.toJSONString(reList));
                umgProducer.pushSmsDetailsDest(array,MqOperation.SMS_SEND_DETAILS);
            }
            //发送失败
            else{
                if(smsEntity.getRetry() >= smsEntity.getMaxRetry()){
                    return;
                }
                Destination destination = RocketMqConfig.destinationMap.get(RocketMqConfig.SMS_REQUEST_DEST);
                umgProducer.pushMQ(destination,smsRequestBo.getUniqueId(),smsRequestBo.getBody());
                smsEntityService.incSmsRetry(uniqueId);
            }

        }catch (Exception e){
          logger.error("分析sms发送结果错误!uniqueId={},resp={}",uniqueId,JsonUtils.convertObjectToJSON(smsSendResponse),e);
        }
    }


    /**发送详情处理策略*/
    public void smsSendDetailsPolicy(JSONArray array) throws UmgException {
        Assert.notNull(array,"array can't empty");
        try{
            List<SmsSendDetails> reList = JsonUtils.toList(array.toJSONString(), SmsSendDetails.class);
            smsSendDetailsService.batchInsert(reList);
            //尝试查询下行报告 生成sms清单
            reList.forEach(smsSendDetails -> smsDetailsService.mergeReportBySmsSendDetails(smsSendDetails));
        }catch (Exception e){
            logger.error("分析smsSendDetailsPolicy 错误!resp={}",array.toJSONString());
            throw e;
        }
    }
    /**发送回调报告处理策略*/
    public void smsReportPolicy(JSONArray array)throws UmgException {
        Assert.notNull(array,"array can't empty");
        try{
            List<SmsSpReport> smsReports = JsonUtils.toList(array.toJSONString(), SmsSpReport.class);
            smsReportService.batchInsert(smsReports);
            //尝试查询发送报告 生成sms清单
            smsReports.forEach(smsReport -> smsDetailsService.mergeSmsSendDetailsByReport(smsReport));
        }catch (Exception e){
            logger.error("分析smsReportPolicy 错误!resp={}",array.toJSONString());
            throw e;
        }
    }

    /**余额处理策略*/
    public void balancePolicy(BalanceResponse response,SmsSpEntity smsSp){
        try {
            int type = smsSp.getType();
            int code = smsSp.getCode();
            Integer cordon = smsSp.getCordon();

            Integer number = response.getNumber();
            Float money = response.getMoney();
            //0：条数计费，1：金额计费
            if(type == 0){
                Integer cordonNumber = smsSp.getCordonNumber();
                cordon = number == 0 ? CordonEnum.UNENABLE.getCo() : number > cordonNumber ? CordonEnum.ENABLE.getCo() : CordonEnum.WARM.getCo();
            }else if(type == 1){
                Float cordonMoney = smsSp.getCordonMoney();
                cordon = money == 0.1 ? CordonEnum.UNENABLE.getCo() : number > cordonMoney ? CordonEnum.ENABLE.getCo() : CordonEnum.WARM.getCo();
            }
            smsSpService.updateSmsSpByCode(cordon,money, number, code);

            if(!cordon.equals(smsSp.getCordon())){
                String warnStr = type == 0? number + " 条" : money + " 元";
                String content = MailTemplateUtils.transformContent(MailTemplateUtils.BALANCE_WARN,SmsSp.getByCode(code).getName(),warnStr);
                sendWarnMail(content);
            }

        }catch (Exception e){
            logger.error("余额监控error!code={}",response.getCode(),e);
        }
    }





//    public void reportPolicy(SmsExecuter executer,Integer spcode){
//        List<SmsSpReport> smsReports = null;
//        try {
//            Boolean retry = true;
//            while (retry){
//                smsReports = executer.getReport();
//                smsReports.forEach(e->e.setSpCode(spcode));
//                smsReportService.batchInsert(smsReports);
//                //尝试查询发送报告 生成sms清单
//                smsReports.forEach(smsReport -> smsDetailsService.mergeSmsSendDetailsByReport(smsReport));
//                retry = smsReports.size() >= smsConfig.getReportNum();
//            }
//        }catch (Exception e){
//            logger.error("获取报告异常!spcode={},report={}",spcode,JsonUtils.convertObjectToJSON(smsReports),e);
//        }
//    }

    private void sendWarnMail(String content) throws UmgException {
        MailRequest request = MailRequestBuilder.create().buildMailRequest();
        String[] mails = smsConfig.getWarnMails().split(",");
        List<String> list = Arrays.stream(mails).collect(Collectors.toList());
        request.setTo(list.get(0));
        if(list.size() > 1){
            list.remove(0);
            String cc = StringUtils.join(list,",");
            request.setCc(cc);
        }
        request.setSubject("UMG-WARN Policy");
        request.setContent(content);
        request.setAppCode(smsConfig.getAppCode());
        mailService.acceptMail(JsonUtils.convertObjectToJSON(request));
    }

}
