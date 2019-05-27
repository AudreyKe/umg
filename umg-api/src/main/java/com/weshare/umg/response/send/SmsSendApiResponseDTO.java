package com.weshare.umg.response.send;

import java.util.Date;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/15 16:23
 * @Description:
 */
public class SmsSendApiResponseDTO {

    private Date sentDate;
    private Date userAcceptDate;
    private String resId;
    private String mobile;
    private Integer downStatus;
    private String uniqueId;


    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getUserAcceptDate() {
        return userAcceptDate;
    }

    public void setUserAcceptDate(Date userAcceptDate) {
        this.userAcceptDate = userAcceptDate;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(Integer downStatus) {
        this.downStatus = downStatus;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
