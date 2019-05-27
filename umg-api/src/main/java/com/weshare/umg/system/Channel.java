package com.weshare.umg.system;


public enum Channel {


    /**
     * 短信发送
     */
    SMS_SEND(100),

    /**
     * 邮件发送
     */
    MIAL_SEND(200),
    ;

    private int channelCode;

    Channel(int channelCode) {
        this.channelCode = channelCode;
    }

    public int getChannelCode() {
        return channelCode;
    }
}
