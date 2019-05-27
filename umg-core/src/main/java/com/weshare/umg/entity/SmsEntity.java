package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.Date;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 10:12
 * @Description:
 */



@Entity
@Table(name = "sms")
public class SmsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unique_id")
    private String uniqueId;
    @Column(name = "cust_id")
    private String custId;
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "avail_sp_code")
    private Integer availSpCode;
    /**
     * 手机号码 格式 [xxxx,xxxxx]
     */
    @Column(name = "mobiles")
    private String mobiles;
    @Column(name = "tpl_code")
    private String tplCode;
    /**
     * 短信模板内容 格式 [xxxx,xxxxx]
     */
    @Column(name = "params")
    private String params;
    @Column(name = "exno")
    private String exno;
    @Column(name = "retry")
    private Integer retry;
    @Column(name = "max_retry")
    private Integer maxRetry;
    @Column(name = "error_msg")
    private String errorMsg;
    @Column(name = "create_time",insertable = false)
    private Date createTime;
    @Column(name = "modify_time",insertable = false)
    private Date modifyTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "sp_code")
    private Integer spCode;
    @Column(name = "content")
    private String content;
    @Column(name = "body")
    private String body;

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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Integer getAvailSpCode() {
        return availSpCode;
    }

    public void setAvailSpCode(Integer availSpCode) {
        this.availSpCode = availSpCode;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getExno() {
        return exno;
    }

    public void setExno(String exno) {
        this.exno = exno;
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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSpCode() {
        return spCode;
    }

    public void setSpCode(Integer spCode) {
        this.spCode = spCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
