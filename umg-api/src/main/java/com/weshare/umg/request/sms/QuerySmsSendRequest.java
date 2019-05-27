package com.weshare.umg.request.sms;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/15 15:56
 * @Description:
 */
public class QuerySmsSendRequest {
    private String appCode;
    private String sign;
    private Long timestamp;
    private String custId;


    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
