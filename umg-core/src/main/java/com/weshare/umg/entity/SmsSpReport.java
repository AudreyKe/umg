package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 16:25
 * @Description:
 */
@Entity
@Table(name = "sms_sp_report")
public class SmsSpReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "res_id")
    private String resId;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "`status`")
    private Integer status;
    @Column(name = "sp_code")
    private Integer spCode;
    @Column(name = "errmsg")
    private String errmsg;
    @Column(name = "`desc`")
    private String desc;
    @Column(name = "user_accept_date")
    private Date userAcceptDate;
    @Column(name = "create_time",insertable=false)
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getUserAcceptDate() {
        return userAcceptDate;
    }

    public void setUserAcceptDate(Date userAcceptDate) {
        this.userAcceptDate = userAcceptDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
