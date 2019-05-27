package com.weshare.umg.request.mail;

import com.weshare.umg.request.sms.SmsRequest;
import com.weshare.umg.system.*;
import com.weshare.umg.util.UniqueIdUtils;


public class MailRequestBuilder {

    public static MailRequestBuilder create() {
        return new MailRequestBuilder();
    }

    private MailRequestBuilder() {}

    public MailRequest buildMailRequest() {
        MailRequest request = new MailRequest();
        request.setChannel(Channel.MIAL_SEND)
                .setOperation(Operation.SYSTEM_LOGIC)
                .setCustId(UniqueIdUtils.nextId())
                .setVersion(Version.V1.getV());
        return request;
    }


}
