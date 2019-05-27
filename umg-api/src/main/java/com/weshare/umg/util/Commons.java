package com.weshare.umg.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weshare.umg.exception.UmgApiException;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.response.send.SmsSendApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class Commons {

    private static final Logger LOGGER = LoggerFactory.getLogger(Commons.class);


    public static String doRestPost(String url, String body, RestTemplate restTemplate) throws UmgApiException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        String retbody;
        try{
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, httpEntity, String.class);
            retbody = responseEntity.getBody();
        }catch (Exception e){
            LOGGER.error("do rest post error!",e);
            throw new UmgApiException("网络错误");
        }
        ValidationUtils.validationResult(retbody);
        return retbody;
    }

}
