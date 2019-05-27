package com.weshare.umg.service.sms.txc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.entity.TemplateDict;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.util.SmsSp;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 11:15
 * @Description:
 */
public class TxcRequestUtils {

    public static String buildBalanceRequestBody(SmsConfig config,Map<String, String> querys, Integer offset){
        long random = Long.parseLong(querys.get("random"));
        long now = SmsTxcUtils.getCurrentTime();
        JSONObject body = new JSONObject();
        body.put("offset", offset * config.getLength());
        body.put("length", config.getLength());
        body.put("sig", SmsTxcUtils.calculateSignature(config.getTxcAppkey(), random, now));
        body.put("time", now);
        return body.toJSONString();
    }


    public static String buildReportRequestBody(SmsConfig config,Map<String, String> querys){
        long random = Long.parseLong(querys.get("random"));
        long now = SmsTxcUtils.getCurrentTime();
        JSONObject body = new JSONObject();
        body.put("max", config.getReportNum());
        body.put("type", 0);
        body.put("sig", SmsTxcUtils.calculateSignature(config.getTxcAppkey(), random, now));
        body.put("time", now);
        return body.toJSONString();
    }


    public static String buildSingleRequestBody(SmsRequestBo request, SmsConfig config , Map<String, String> querys){
        long random = Long.parseLong(querys.get("random"));
        long now = SmsTxcUtils.getCurrentTime();
        String spTemplateId = request.getDictMap().get(SmsSp.TXC.getCode()).getSpTemplateId();
        JSONObject body = new JSONObject();
        body.put("tel", getSingleTel(request.getNation().getCode(),request.getMobiles().get(0)));
        body.put("tpl_id", spTemplateId);
        body.put("time", now);
        body.put("sign", request.getTemplateEntity().getSign());
        body.put("sig", SmsTxcUtils.calculateSignature(config.getTxcAppkey(), random, now, request.getMobiles().get(0)));
        body.put("params", request.getParams());
        body.put("extend", "");
        body.put("ext", request.getUniqueId());
        return body.toJSONString();
    }


    public static String buildBatchRequestBody(SmsRequestBo request, SmsConfig config , Map<String, String> querys){
        long random = Long.parseLong(querys.get("random"));
        long now = SmsTxcUtils.getCurrentTime();
        String spTemplateId = request.getDictMap().get(SmsSp.TXC.getCode()).getSpTemplateId();
        JSONObject body = new JSONObject();
        body.put("tel", getBatchTel(request.getNation().getCode(),request.getMobiles()));
        body.put("tpl_id", spTemplateId);
        body.put("time", now);
        body.put("sign", request.getTemplateEntity().getSign());
        body.put("sig", SmsTxcUtils.calculateSignature(config.getTxcAppkey(), random, now, request.getMobiles()));
        body.put("params", request.getParams());
        body.put("extend", "");
        body.put("ext", request.getUniqueId());
        return body.toJSONString();
    }

    private static ArrayList<JSONObject> getBatchTel(String nationCode, List<String> mobiles) {
        ArrayList<JSONObject> phones = new ArrayList<JSONObject>();
        for (String mobile: mobiles) {
            JSONObject phone = getSingleTel(nationCode,mobile);
            phones.add(phone);
        }
        return phones;
    }

    private static JSONObject getSingleTel(String nationCode, String mobile) {
        JSONObject phone = new JSONObject();
        phone.put("nationcode", nationCode);
        phone.put("mobile", mobile);
        return phone;
    }











}
