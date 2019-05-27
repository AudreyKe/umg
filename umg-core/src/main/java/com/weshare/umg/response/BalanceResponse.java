package com.weshare.umg.response;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/7 10:07
 * @Description:
 */
public class BalanceResponse {

    private Integer code;
    private Integer number;
    private Float money;
    /**
     * 计费类型：
     * 0：条数计费，单位：“条”，money默认为0
     * 1：金额计费，单位：“元”，number默认为0
     **/
    private Integer type;

    public Integer getNumber() {
        return number;
    }

    public BalanceResponse setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public Float getMoney() {
        return money;
    }

    public BalanceResponse setMoney(Float money) {
        this.money = money;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public BalanceResponse setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
