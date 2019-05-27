package com.weshare.umg.controller;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshare.umg.service.sms.SmsService;
import com.weshare.umg.util.SmsSp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/smscallback")
public class SmsCallBackController {

    private static final Logger logger = LoggerFactory.getLogger(SmsCallBackController.class);
    private static final Logger STORE_ERROR_LOG = LoggerFactory.getLogger("store-error");

    @Autowired
    private SmsService smsService;


    @RequestMapping(value = "/txc", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> txc(@RequestBody String body) {
        SmsSp sp = SmsSp.TXC;
        try {
            smsService.acceptHttpReport(body,sp);
        }catch (Exception e){
            logger.error("acceptHttp sms report!sp_code={},body={}",sp.getCode(),body,e);
            STORE_ERROR_LOG.info("http sms report|sp_code={},body={}",sp.getCode(),body);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("result",0);
        map.put("errmsg","OK");
        return map;
    }

    @RequestMapping(value = "/montnets", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> montnets(@RequestBody String body) {
        SmsSp sp = SmsSp.MONTNETS;
        try {
            smsService.acceptHttpReport(body,sp);
        }catch (Exception e){
            logger.error("acceptHttp sms report!sp_code={},body={}",sp.getCode(),body,e);
            STORE_ERROR_LOG.info("http sms report|sp_code={},body={}",sp.getCode(),body);
        }
        JSONObject object = JSON.parseObject(body);
        Map<String,Object> map = new HashMap<>();
        map.put("cmd",object.getString("cmd"));
        map.put("seqid",object.getIntValue("seqid"));
        map.put("result",0);
        return map;
    }



}
