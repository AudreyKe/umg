package com.weshare.umg.request;

import com.weshare.umg.request.sms.SmsRequest;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/10 09:38
 * @Description:
 */
public class SmsAcRequest extends SmsRequest {
    private String uniqueId;

    public String getUniqueId() {
        return uniqueId;
    }

    public SmsAcRequest setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

}
