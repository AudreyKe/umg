package com.weshare.umg.util;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/17
 * @Description:
 */
public enum MqOperation {
    SMS_SEND_DETAILS("SMS发送清单"),
    SMS_REPORT("SMS回执报告"),
    ;

    private String name;

    MqOperation(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
