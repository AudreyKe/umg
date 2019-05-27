package com.weshare.umg.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weshare.umg.exception.UmgApiException;
import com.weshare.umg.request.mail.MailRequest;
import com.weshare.umg.request.sms.QuerySmsSendRequest;
import com.weshare.umg.request.sms.SmsRequest;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.response.send.SmsSendApiResponse;
import com.weshare.umg.system.UrlConstant;
import com.weshare.umg.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class UmgRestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmgRestClient.class);
    private String appCode;
    private String token;
    private String umgurl;
    private RestTemplate restTemplate;

    UmgRestClient(UmgRestClientBuilder builder) {
        this.umgurl = builder.getUmgConfig().getUmgurl();
        this.token = builder.getUmgConfig().getToken();
        this.appCode = builder.getUmgConfig().getAppCode();
        this.restTemplate = builder.getRestTemplate();
    }


    public Result sendMail(MailRequest request) throws UmgApiException {
        String url = this.umgurl + UrlConstant.UMG_MAIL_URL;
        request.setAppCode(appCode);
        String body = Commons.doRestPost(url,JsonUtil.convertObjectToJSON(request), restTemplate);
        Result<String> result = JSON.parseObject(body, new TypeReference<Result<String>>(){});
        return result;
    }


    public Result sendSms(SmsRequest request) throws UmgApiException {
        String url =  this.umgurl + UrlConstant.UMG_SMS_URL;
        request.setAppCode(appCode);
        Long timestamp = System.currentTimeMillis();
        request.setTimestamp(timestamp);
        request.setSign(EncryptUtils.sha256(timestamp+token));
        String body = Commons.doRestPost(url,JsonUtil.convertObjectToJSON(request), restTemplate);
        Result<String> result = JSON.parseObject(body, new TypeReference<Result<String>>(){});
        return result;
    }

    public Result<List<SmsSendApiResponse>> querySendDetails(String custId) throws UmgApiException {
        String url =  this.umgurl + UrlConstant.UMG_SMS_QUERY;
        QuerySmsSendRequest request = new QuerySmsSendRequest();
        request.setAppCode(appCode);
        Long timestamp = System.currentTimeMillis();
        request.setTimestamp(timestamp);
        request.setSign(EncryptUtils.sha256(timestamp+token));
        request.setCustId(custId);
        String body = Commons.doRestPost(url,JsonUtil.convertObjectToJSON(request), restTemplate);
        Result<List<SmsSendApiResponse>> result = JSON.parseObject(body, new TypeReference<Result<List<SmsSendApiResponse>>>(){});
        return result;
    }




}
