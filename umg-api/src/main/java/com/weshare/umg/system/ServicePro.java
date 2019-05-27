package com.weshare.umg.system;

/**
 * @Auther: mudong.xiao
 * @Date: 2019/5/7
 * @Description:
 */
public enum ServicePro {

    SMS_DEFAULT_SP(10),
    /**
     * 自定义 availspCode，联系umg管理员获取可用的服务商通道码
     **/
    SMS_CUSTOM_SP(11),
    ;

    private int spCode;

    ServicePro(int spCode) {
        this.spCode = spCode;
    }

    public int getSpCode() {
        return spCode;
    }

    public void setSpCode(int spCode) {
        this.spCode = spCode;
    }
}

