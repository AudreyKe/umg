package com.weshare.umg;

import com.weshare.json.JsonUtils;
import com.weshare.umg.exception.UmgApiException;
import com.weshare.umg.exception.UmgException;
import com.weshare.umg.remote.UmgRestClient;
import com.weshare.umg.remote.UmgRestClientBuilder;
import com.weshare.umg.remote.UmgConfig;
import com.weshare.umg.request.mail.MailRequest;
import com.weshare.umg.request.mail.MailRequestBuilder;
import com.weshare.umg.request.sms.SmsRequest;
import com.weshare.umg.request.sms.SmsRequestBuilder;
import com.weshare.umg.response.base.Result;
import com.weshare.umg.response.send.SmsSendApiResponse;
import com.weshare.umg.system.ClientType;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 15:20
 * @Description:
 */
public class UmgClientTest {



    @Test
    public void testUmgMailClient() {
        RestTemplate restTemplate = new RestTemplate();

        UmgConfig umgConfig = new UmgConfig();
        umgConfig.setAppCode("SSKY");
        umgConfig.setUmgurl("https://umg-test.weshareholdings.com");
        umgConfig.setToken("59f571344cd57e094df2c5487de55f0a");
        umgConfig.setClientType(ClientType.HTTP.getType());

        UmgRestClient umgClient = UmgRestClientBuilder.create()
                .setRestTemplate(restTemplate)
                .setUmgConfig(umgConfig)
                .build();


        String to = "mudong.xiao@weshareholdings.com";
        String cc = "874650018@qq.com";
        String subject = "邮件测试";
        String content = "<p>尊敬的张三（先生/女士），您好：</p> 您在weshare公司购买的许可证新分享将在8天后到期，到期后将不能使用许可证中的功能。请联系您的专属商务人员洽谈许可证续费事宜。</p> weshare网址：http://www.weshareholdings.com <div style=\"width:400px;float:right;margin:180px 50px 0px 0px;\"><p><b>新分享科技服务（深圳）有限公司</b></p></div>";


        MailRequest request = MailRequestBuilder.create().buildMailRequest();
        request.setCc(cc);
        request.setTo(to);
        request.setSubject(subject);
        request.setContent(content);


        try {
            System.out.println(umgClient.sendMail(request));
        } catch (UmgApiException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testUmgSmsClient() throws UmgApiException {
        RestTemplate restTemplate = new RestTemplate();

        UmgConfig umgConfig = new UmgConfig();
        umgConfig.setAppCode("SSKY");
        umgConfig.setUmgurl("https://umg-test.weshareholdings.com");
        umgConfig.setToken("59f571344cd57e094df2c5487de55f0a");
        umgConfig.setClientType(ClientType.HTTP.getType());

        UmgRestClient umgClient = UmgRestClientBuilder.create()
                .setRestTemplate(restTemplate)
                .setUmgConfig(umgConfig)
                .build();

        SmsRequest request = SmsRequestBuilder.create().buildSmsRequest();
        request.getParams().add("XMDSHY1");
        request.getMobiles().add("15012458798");
        request.setTplCode("SMS_VERIFI_CODE");
        System.out.println(request.getCustId());
        System.out.println(JsonUtils.convertObjectToJSON(umgClient.sendSms(request)));

    }


    @Test
    public void testQuery() throws UmgApiException {
        RestTemplate restTemplate = new RestTemplate();

        UmgConfig umgConfig = new UmgConfig();
        umgConfig.setAppCode("SSKY");
        umgConfig.setUmgurl("https://umg-test.weshareholdings.com");
        umgConfig.setToken("59f571344cd57e094df2c5487de55f0a");
        umgConfig.setClientType(ClientType.HTTP.getType());

        UmgRestClient umgClient = UmgRestClientBuilder.create()
                .setRestTemplate(restTemplate)
                .setUmgConfig(umgConfig)
                .build();


        String custId = "5cde6c4b0a0a13330f8c0001";
        Result<List<SmsSendApiResponse>> result = umgClient.querySendDetails(custId);
        System.out.println(JsonUtils.convertObjectToJSON(result));


    }

}
