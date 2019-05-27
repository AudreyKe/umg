package com.weshare.umg.util;

/**
 * @Author: mudong.xiao
 * @Date: 2019/5/10 14:05
 * @Description:
 */
public enum  CordonEnum {
    ENABLE(1,"可用"),
    UNENABLE(0,"不可用"),
    WARM(2,"警告"),

    ;

    private int co;
    private String name;

    CordonEnum(int co, String name) {
        this.co = co;
        this.name = name;
    }

    public int getCo() {
        return co;
    }

    public String getName() {
        return name;
    }

    public static CordonEnum getByCo(Integer co) {
        CordonEnum[] cos = CordonEnum.values();
        for (CordonEnum e : cos) {
            if (e.co == co) {
                return e;
            }
        }
        return null;
    }


    public static Boolean isEnable(Integer co) {
        CordonEnum e = getByCo(co);
        if(e==null || e.equals(UNENABLE)){
            return false;
        }
        return true;
    }



}
