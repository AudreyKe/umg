package com.weshare.umg.service.sms.montnets;



/**
 * @Author: mudong.xiao
 * @Date: 2019/5/9 10:03
 * @Description:
 */
public enum  MonCodeEnum {

    MON_AUTHENTICATION_ERROR(-100001,"鉴权不通过,请检查账号,密码,时间戳,固定串,以及MD5算法是否按照文档要求进行设置"),
    MON_AUTHENTICATION_MORE_ERROR(-100002,"用户多次鉴权不通过,请检查账号,密码,时间戳,固定串,以及MD5算法是否按照文档要求进行设置"),
    MON_USER_ARREARS(-100003,"用户欠费"),
    MON_CUSTID_ERROR(-100004,"custid或者exdata字段填写不合法"),
    MON_CONTENT_LONG(-100011,"短信内容超长"),
    MON_MOBILE_ERROR(-100012,"手机号码不合法"),
    MON_MOBILE_MUCH(-100014,"手机号码超过最大支持数量（1000）"),
    MON_PORT_EXCEPTION(-100029,"端口绑定失败"),
    MON_CONNECT_EXCEPTION(-100056,"用户账号登录的连接数超限"),
    MON_IP_EXCEPTION(-100057,"用户账号登录的IP错误"),
    MON_SMS_UNENABLE(-100126,"短信有效存活时间无效"),
    MON_SVRTYPE_ERROR(-100252,"业务类型不合法(超长或包含非字母数字字符)"),
    MON_EXDATA_TOO_MUCH(-100253,"自定义参数超长"),
    MON_DB_EXCEPTION(-100999,"平台数据库内部错误"),
    MON_EXCEPTION(-100000,"内部错误"),

    ;

    private int code;
    private String message;

    MonCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static MonCodeEnum getCodeEnum(int code){
        MonCodeEnum[] codeEnums = MonCodeEnum.values();
        for(MonCodeEnum codeEnum : codeEnums ){
            if(codeEnum.code == code){
                return codeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
