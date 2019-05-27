package com.weshare.umg.response;

import com.weshare.umg.entity.SmsDetails;
import com.weshare.umg.entity.SmsSendDetails;

import java.util.*;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 10:22
 * @Description:
 */
public class SmsSendResponse {

    public SmsSendResponse(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    public SmsSendResponse() {}


    private String uniqueId;
    private Integer code;
    private Boolean status;
    private Date sentDate;
    Map<String,String> errMap = new HashMap<>();
    List<SmsSendDetails> reList = new ArrayList<>();

    public String getUniqueId() {
        return uniqueId;
    }

    public SmsSendResponse setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public Map<String, String> getErrMap() {
        return errMap;
    }

    public void setErrMap(Map<String, String> errMap) {
        this.errMap = errMap;
    }

    public Integer getCode() {
        return code;
    }

    public SmsSendResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public SmsSendResponse setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<SmsSendDetails> getReList() {
        return reList;
    }

    public SmsSendResponse setReList(List<SmsSendDetails> reList) {
        this.reList = reList;
        return this;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public SmsSendResponse setSentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }
}
