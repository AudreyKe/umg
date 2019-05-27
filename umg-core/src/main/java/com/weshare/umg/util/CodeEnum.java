package com.weshare.umg.util;


public enum CodeEnum {

    /**
     * umg系统状态码
     */
    SUCCESS_CODE(200,"操作成功"),
    SYSTEM_ERROR(5005001,"系统异常"),
    APPCODE_ISEMPTY(5005002,"系统编号为空"),
    APPCODE_NOT_EXISTS(5005003,"系统编号不存在"),
    VERSION_ERROR(5005004,"版本号错误"),
    TIMESTAMP_ISEMPTY(5005005,"时间戳为空"),
    SIGN_ISEMPTY(5005006,"签名为空"),
    SIGN_ERROR(5005007,"签名错误"),
    CUSTID_ISEMPTY(5005008,"用户主键为空"),
    OPERATION_ERROR(5005009,"操作码错误"),
    UNKNOWN_ERROR(5005010,"未知错误"),
    CHANNEL_ERROR(5005011,"通道类型错误"),
    DB_ERROR(5005012,"数据库操作错误"),
    UNIQUEID_EXISTS(5005013,"信息唯一标识已存在"),
    NETWORK_ERROR(5005014,"网络请求错误"),
    GATEWAY_EXCEPTION(5005015,"网关异常"),

    SEND_SMS_FAIL(5005100,"发短信失败"),
    MOBILE_ISEMPTY(5005101,"手机号为空"),
    MOBILE_FORMAT_ERROR(5005102,"手机号格式错误"),
    GET_BALANCE_EXCEPTION(5005103,"获取余额异常"),
    SMS_TPL_PARAMS_NOT_MATCHER(5005104,"短信模板参数不匹配"),
    SMS_TPL_CODE_ISEMPTY(5005105,"短信模板CODE为空"),
    SMS_TPL_CODE_ERROR(5005106,"短信模板CODE错误"),
    SMS_TPL_PARAMS_ISEMPTY(5005107,"短信模板参数为空"),
    SMS_SP_ERROR(5005108,"短信服务商错误"),
    SMS_SP_UNENABLE(5005109,"短信服务商不可用"),
    SMS_REQUEST_ERROR(5005110,"短信请求体结构异常"),
    NATION_ERROR(5005111,"国家码错误"),
    RMQ_PUSH_ERROR(5005112,"推送数据至RMQ异常"),
    RMQ_PULL_ERROR(5005113,"拉取数据RMQ异常"),

    RMQ_OPER_ERROR(5005114,"RMQ操作码异常"),

    MAIL_TO_ISEMPTY(5005200,"接收邮箱为空"),
    EMAIL_FORMAT_ERROR(5005201,"邮箱格式错误"),
    SEND_MAIL_FAIL(5005202,"发送邮件失败"),
    ;
    private int code;
    private String message;

    CodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static CodeEnum getCodeEnum(int code){
        CodeEnum[] codeEnums = CodeEnum.values();
        for(CodeEnum codeEnum : codeEnums ){
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
