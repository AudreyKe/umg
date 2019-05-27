package com.weshare.umg.util;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/16
 * @Description:
 */
public enum DownStatus {

    FAIL(0,"发送失败"),
    SUEECSS(1,"发送成功"),
    WAIT(2,"等待回执"),

    ;


    private int status;

    private String msg;

    DownStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
