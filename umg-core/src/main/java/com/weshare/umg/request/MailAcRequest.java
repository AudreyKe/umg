package com.weshare.umg.request;

import com.weshare.umg.request.mail.MailRequest;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/10 14:40
 * @Description:
 */
public class MailAcRequest extends MailRequest {
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public MailAcRequest setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }
}
