package com.weshare.umg.util;


import com.weshare.umg.exception.UmgApiException;
import com.weshare.umg.remote.UmgConfig;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.system.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ValidationUtils {
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

    public static void validateUmConfig(UmgConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("UmgConfig Cannot Be null");
        }

        String appCode = config.getAppCode();
        if (StringUtils.isBlank(appCode)) {
            throw new IllegalArgumentException("app code Cannot Be null");
        }

        String token = config.getToken();
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token code Cannot Be null");
        }

        Integer clientType =config.getClientType();
        if(clientType == null){
            throw new IllegalArgumentException("client type Cannot Be null");
        }
        ClientType type = ClientType.getByValue(clientType);
        if(type == null){
            throw new IllegalArgumentException("client type error");
        }

        String urls = config.getUmgurl();
        if (type.equals(ClientType.HTTP) && StringUtils.isBlank(urls)) {
            throw new IllegalArgumentException("umg urls Cannot Be null");
        }
    }

    public static void validationResult(String body) throws UmgApiException {
        if (!JsonUtil.validate(body)) {
            logger.error("result error!body=", body);
            throw new UmgApiException("返回码错误");
        }
    }

    public static void validationResult(Result result) throws UmgApiException {
        Integer code = result.getCode();
        String msg = result.getMsg();
        if (!code.equals(Result.SUCCESS)) {
            logger.error("result error!code={} msg={}",code,msg);
            throw new UmgApiException(msg);
        }
    }



}
