package com.weshare.umg.service.sms.montnets;

import com.alibaba.fastjson.JSONObject;
import com.weshare.date.DateTimeUtils;
import com.weshare.umg.config.SmsConfig;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.request.SmsRequestBo;
import com.weshare.umg.util.CodeEnum;
import com.weshare.umg.util.TemplateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Date;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 11:15
 * @Description:
 */
public class MonRequestUtils {
    private static final Logger logger = LoggerFactory.getLogger(MonRequestUtils.class);

    public static String buildMonRequestBody(SmsRequestBo request, SmsConfig config)throws UmgException {
        try{
            JSONObject body = new JSONObject();
            body.put("userid", config.getMonUserid().toUpperCase());
            body.put("mobile", StringUtils.join(request.getMobiles(),","));
            body.put("content", URLEncoder.encode(TemplateUtils.fitContent(request), "GBK"));
            body.put("exno", request.getExno());
            body.put("svrtype", request.getSvrtype());
            body.put("exdata", request.getExdata());
            body.put("pwd", config.getMonPwd());
            body.put("custid", request.getUniqueId());
            body.put("apikey", config.getMonApikey());
            boolean isEncryptPwd = config.getMonEncryptPwd();
            if(isEncryptPwd) {
                String timestamp = DateTimeUtils.formatDate(new Date(),"MMddHHmmss");
                String encryptPwd = SmsMonUtils.encryptPwd(config.getMonUserid(),config.getMonPwd(),timestamp);
                body.put("pwd", encryptPwd);
                body.put("timestamp", timestamp);
            }
            return body.toJSONString();
        }catch (Exception e){
            logger.error("build Mon Request error!id={}",request.getUniqueId(),e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    public static String buildGetBalanceRequestBody( SmsConfig config)throws UmgException {
        try{
            JSONObject body = new JSONObject();
            body.put("userid",config.getMonUserid().toUpperCase());
            body.put("pwd", config.getMonPwd());
            body.put("apikey", config.getMonApikey());
            boolean isEncryptPwd = config.getMonEncryptPwd();
            if(isEncryptPwd) {
                String timestamp = DateTimeUtils.formatDate(new Date(),"MMddHHmmss");
                String encryptPwd = SmsMonUtils.encryptPwd(config.getMonUserid(),config.getMonPwd(),timestamp);
                body.put("pwd", encryptPwd);
                body.put("timestamp", timestamp);
            }
            return body.toJSONString();
        }catch (Exception e){
            logger.error("build Mon GetBalance Request error!id={}",config.getMonUserid(),e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }

    public static String buildReportRequestBody(SmsConfig config)throws UmgException {
        try{
            JSONObject body = new JSONObject();
            body.put("userid",config.getMonUserid().toUpperCase());
            body.put("pwd", config.getMonPwd());
            body.put("apikey", config.getMonApikey());
            body.put("retsize", config.getReportNum());
            boolean isEncryptPwd = config.getMonEncryptPwd();
            if(isEncryptPwd) {
                String timestamp = DateTimeUtils.formatDate(new Date(),"MMddHHmmss");
                String encryptPwd = SmsMonUtils.encryptPwd(config.getMonUserid(),config.getMonPwd(),timestamp);
                body.put("pwd", encryptPwd);
                body.put("timestamp", timestamp);
            }
            return body.toJSONString();
        }catch (Exception e){
            logger.error("build Mon GetBalance Request error!id={}",config.getMonUserid(),e);
            throw new UmgException(CodeEnum.SYSTEM_ERROR);
        }
    }


}
