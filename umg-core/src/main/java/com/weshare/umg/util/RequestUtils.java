package com.weshare.umg.util;

import com.weshare.json.JsonUtils;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.entity.*;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.MailAcRequest;
import com.weshare.umg.request.SmsAcRequest;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.request.mail.MailRequest;
import com.weshare.umg.request.sms.SmsRequest;
import com.weshare.umg.system.Nation;
import com.weshare.umg.system.Operation;
import com.weshare.umg.system.ServicePro;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 11:15
 * @Description:
 */
public class RequestUtils {

    public static EMailEntity buildEMailEntity(MailAcRequest request){
        String req = JsonUtils.convertObjectToJSON(request);
        EMailEntity entity =  JsonUtils.toBean(req,EMailEntity.class);
        entity.setOperCode(request.getOperation().getOperCode());
        entity.setChannelCode(request.getChannel().getChannelCode());
        return entity;
    }




    public static SmsEntity initSmsEntity(SmsAcRequest request,SmsConfig smsConfig)  {
        SmsEntity entity = new SmsEntity();
        entity.setCustId(request.getCustId());
        entity.setUniqueId(request.getUniqueId());
        entity.setAppCode(request.getAppCode());
        entity.setBody(JsonUtils.convertObjectToJSON(request));
        entity.setStatus(SendStatus.READY.getStatus());
        entity.setRetry(smsConfig.getRetry());
        entity.setMaxRetry(smsConfig.getMaxretry());
        return entity;
    }


    public static void addSmsEntity(SmsAcRequest request, TemplateEntity templateEntity, SmsEntity entity) throws UmgException {
        String content = TemplateUtils.fitContent(request.getParams(),templateEntity.getMsg(),templateEntity.getSign());
        entity.setContent(content);
        entity.setAvailSpCode(request.getAvailSpCode());
        entity.setTplCode(request.getTplCode());
        entity.setExno(request.getExno());
        entity.setMobiles(JsonUtils.convertObjectToJSON(request.getMobiles()));
        entity.setParams(JsonUtils.convertObjectToJSON(request.getParams()));
    }


    public static SmsRequestBo handleSmsRequest(SmsAcRequest request, TemplateEntity templateEntity,List<SmsSpEntity> smsSplist) throws UmgException {
        String req = JsonUtils.convertObjectToJSON(request);
        SmsRequestBo smsRequestBo = JsonUtils.toBean(req,SmsRequestBo.class);
        smsRequestBo.setBody(req);
        //mobiles 去重
        List<String> unmobiles = smsRequestBo.getMobiles().stream().distinct().collect(Collectors.toList());
        smsRequestBo.setMobiles(unmobiles);
        Integer defaultCode = templateEntity.getAvailSpCode();
        ServicePro servicePro = request.getServicePro();
        switch (servicePro){
            case SMS_DEFAULT_SP:
                smsRequestBo.setAvailSpCode(defaultCode);
                break;
            case SMS_CUSTOM_SP:
                if(request.getAvailSpCode() == null){
                    smsRequestBo.setAvailSpCode(defaultCode);
                }
        }
        Operation operation = request.getOperation();
        if(operation == Operation.SYSTEM_LOGIC){
            List<String> mobiles = smsRequestBo.getMobiles();
            Operation op = mobiles.size() == 1 ? Operation.SINGLE_SEND : Operation.BATCH_SEND;
            smsRequestBo.setOperation(op);
        }
        operation = smsRequestBo.getOperation();
        if(operation == Operation.SINGLE_SEND){
            String mobile = smsRequestBo.getMobiles().get(0);
            smsRequestBo.getMobiles().clear();
            smsRequestBo.getMobiles().add(mobile);
        }

        Nation nation = request.getNation();
        if(nation == null){
            smsRequestBo.setNation(Nation.CHINA);
        }
        String content = TemplateUtils.fitContent(request.getParams(),templateEntity.getMsg(),templateEntity.getSign());
        smsRequestBo.setContent(content);

        List<Integer> list = ChannelUtils.findSmsAvailChannel(smsRequestBo.getAvailSpCode(),templateEntity.getAvailSpCode());
        Map<Integer, Boolean> apMap = smsSplist.stream().collect(Collectors.toMap(x -> x.getCode(), x -> CordonEnum.isEnable(x.getCordon())));
        List<Integer> splist = list.stream().filter(code-> (apMap.containsKey(code) && apMap.get(code))).collect(Collectors.toList());

        smsRequestBo.setSPList(splist);
        smsRequestBo.setTemplateEntity(templateEntity);
        Map<Integer, TemplateDict> dictMap = templateEntity.getDicts().stream().collect(Collectors.toMap(a->(a.getSp()), a -> a,(k1, k2)->k1));
        smsRequestBo.setDictMap(dictMap);

        return smsRequestBo;
    }









}
