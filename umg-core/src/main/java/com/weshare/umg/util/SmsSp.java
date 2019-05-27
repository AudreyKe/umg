package com.weshare.umg.util;

import com.weshare.umg.system.ClientType;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/8
 * @Description:
 */
public enum SmsSp {

    TXC(1,"腾讯云"),
    MONTNETS(2,"梦网科技"),
    ;

    private Integer code;
    private String name;

    SmsSp(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static SmsSp getByCode(Integer code) {
        SmsSp[] sps = SmsSp.values();
        for (SmsSp sp : sps) {
            if (sp.code == code) {
                return sp;
            }
        }
        return null;
    }
}
