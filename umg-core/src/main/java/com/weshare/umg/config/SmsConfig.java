package com.weshare.umg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/30 13:52
 * @Description:
 */
@ConfigurationProperties(prefix = "sms")
@Component
public class SmsConfig {

    private String	monUserid;
    private String	monPwd;
    private String monApikey;
    private Boolean monEncryptPwd = true;
    private String monPaths;

    private Integer length = 10;
    private Integer txcAppid;
    private String txcAppkey;

    private Integer reportNum;
    private Integer retry = 0;
    private Integer maxretry = 2;

    private String warnMails;
    private String appCode;

    public String getMonUserid() {
        return monUserid;
    }

    public void setMonUserid(String monUserid) {
        this.monUserid = monUserid;
    }

    public String getMonPwd() {
        return monPwd;
    }

    public void setMonPwd(String monPwd) {
        this.monPwd = monPwd;
    }

    public String getMonApikey() {
        return monApikey;
    }

    public void setMonApikey(String monApikey) {
        this.monApikey = monApikey;
    }

    public Boolean getMonEncryptPwd() {
        return monEncryptPwd;
    }

    public void setMonEncryptPwd(Boolean monEncryptPwd) {
        this.monEncryptPwd = monEncryptPwd;
    }

    public String getMonPaths() {
        return monPaths;
    }

    public void setMonPaths(String monPaths) {
        this.monPaths = monPaths;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getTxcAppid() {
        return txcAppid;
    }

    public void setTxcAppid(Integer txcAppid) {
        this.txcAppid = txcAppid;
    }

    public String getTxcAppkey() {
        return txcAppkey;
    }

    public void setTxcAppkey(String txcAppkey) {
        this.txcAppkey = txcAppkey;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getMaxretry() {
        return maxretry;
    }

    public void setMaxretry(Integer maxretry) {
        this.maxretry = maxretry;
    }

    public String getWarnMails() {
        return warnMails;
    }

    public void setWarnMails(String warnMails) {
        this.warnMails = warnMails;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
