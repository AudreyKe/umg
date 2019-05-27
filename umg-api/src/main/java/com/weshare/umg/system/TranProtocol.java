package com.weshare.umg.system;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/4/18
 * @Description:
 */
public enum TranProtocol {

    /**
     *同步
     */
    SYN(1),
    /**
     *异步
     */
    ASYN(2),
    ;

    private int type;

    TranProtocol(int type) {
        this.type = type;
    }

    public static TranProtocol getByValue(Integer type) {
        TranProtocol[] tps = TranProtocol.values();
        for (TranProtocol tp : tps) {
            if (tp.type == type) {
                return tp;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
