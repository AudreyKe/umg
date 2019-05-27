package com.weshare.umg.entity;

import javax.persistence.*;
import java.util.Date;


/**
 * @Author: mudong.xiao
 * @Date: 2019/4/18 10:12
 * @Description:
 */



@Entity
@Table(name = "sms_sp")
public class SmsSpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private Integer status;
    @Column(name = "code")
    private Integer code;
    @Column(name = "cordon")
    private Integer cordon;
    @Column(name = "type")
    private Integer type;
    @Column(name = "money")
    private Float money;
    @Column(name = "cordon_money")
    private Float cordonMoney;
    @Column(name = "number")
    private Integer number;
    @Column(name = "cordon_number")
    private Integer cordonNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCordon() {
        return cordon;
    }

    public void setCordon(Integer cordon) {
        this.cordon = cordon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getCordonMoney() {
        return cordonMoney;
    }

    public void setCordonMoney(Float cordonMoney) {
        this.cordonMoney = cordonMoney;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCordonNumber() {
        return cordonNumber;
    }

    public void setCordonNumber(Integer cordonNumber) {
        this.cordonNumber = cordonNumber;
    }
}
