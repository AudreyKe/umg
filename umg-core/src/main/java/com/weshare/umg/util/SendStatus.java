package com.weshare.umg.util;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/4/18
 * @Description:
 */
public enum SendStatus {
    READY(0),
    SUEECSS(1),
    FAIL(-1),
    EXCE(2),

    ;

    private int status;

    SendStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
