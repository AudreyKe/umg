package com.weshare.umg.request.sms;

import com.weshare.umg.system.*;
import com.weshare.umg.util.UniqueIdUtils;


public class SmsRequestBuilder {

    public static SmsRequestBuilder create() {
        return new SmsRequestBuilder();
    }

    private SmsRequestBuilder() {}


    public SmsRequest buildSmsRequest() {
        SmsRequest smsRequest = new SmsRequest();
        smsRequest.setNation(Nation.CHINA)
                .setServicePro(ServicePro.SMS_DEFAULT_SP)
                .setChannel(Channel.SMS_SEND)
                .setOperation(Operation.SYSTEM_LOGIC)
                .setCustId(UniqueIdUtils.nextId())
                .setVersion(Version.V1.getV());
        return smsRequest;
    }

}
