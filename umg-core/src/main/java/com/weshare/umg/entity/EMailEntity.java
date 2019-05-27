package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 10:12
 * @Description:
 */

@Entity
@Table(name = "email")
public class EMailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "cust_id")
    private String custId;
    @Column(name = "reply_to")
    private String replyTo;
    @Column(name = "`to`")
    private String to;
    @Column(name = "cc")
    private String cc;
    @Column(name = "bcc")
    private String bcc;
    @Column(name = "sent_date")
    private Long sentDate;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "channel_code")
    private Integer channelCode;
    @Column(name = "oper_code")
    private Integer operCode;
    @Column(name = "tran_protocol_type")
    private Integer tranProtocolType;
    @Column(name = "status")
    private Integer status;
    @Column(name = "retry")
    private Integer retry = 0;
    @Column(name = "max_retry")
    private Integer maxRetry = 3;
    @Column(name = "warm_msg")
    private String warmMsg;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public Long getSentDate() {
        return sentDate;
    }

    public void setSentDate(Long sentDate) {
        this.sentDate = sentDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(Integer channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getOperCode() {
        return operCode;
    }

    public void setOperCode(Integer operCode) {
        this.operCode = operCode;
    }


    public Integer getTranProtocolType() {
        return tranProtocolType;
    }

    public void setTranProtocolType(Integer tranProtocolType) {
        this.tranProtocolType = tranProtocolType;
    }

    public String getWarmMsg() {
        return warmMsg;
    }

    public void setWarmMsg(String warmMsg) {
        this.warmMsg = warmMsg;
    }
}
