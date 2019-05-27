package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 16:25
 * @Description:
 */

@Entity
@Table(name = "sms_send_details")
public class SmsSendDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "content")
    private String content;
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "cust_id")
    private String custId;
    @Column(name = "sent_date")
    private Date sentDate;
    @Column(name = "accept_date")
    private Date acceptDate;
    @Column(name = "res_id")
    private String resId;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "sp_code")
    private Integer spCode;
    @Column(name = "create_time",insertable = false)
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
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

    public Integer getSpCode() {
        return spCode;
    }

    public void setSpCode(Integer spCode) {
        this.spCode = spCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
