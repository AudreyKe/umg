package com.weshare.umg.remote;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/17 15:29
 * @Description:
 */
public class UmgConfig {
    private Integer clientType;
    private String appCode;
    private String umgurl;
    private String token;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUmgurl() {
        return umgurl;
    }

    public void setUmgurl(String umgurl) {
        this.umgurl = umgurl;
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
