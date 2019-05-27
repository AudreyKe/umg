package com.weshare.umg.system;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/5
 * @Description:
 */
public enum Nation {

    CHINA("86"),
    ;

    private String code;

    Nation(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
