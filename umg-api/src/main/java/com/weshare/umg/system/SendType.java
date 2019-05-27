package com.weshare.umg.system;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/5 18:21
 * @Description:
 */
public enum SendType {
    /**
     * 短信
     */
    SMS(100),

    /**
     * 邮件
     */
    MIAL(200),
    ;

    private int type;

    SendType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
