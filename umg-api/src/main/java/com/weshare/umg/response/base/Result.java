package com.weshare.umg.response.base;

/**
 *  响应消息报文封装类
 *  @author xiaomudong
 */
public class Result<T> {

    public static final Integer SUCCESS = 200;

    /**   error_code 状态值：200 极为成功，其他数值代表失败*/
    private Integer code;

    /**    error_msg 错误信息，若status为200时，为success*/
    private String msg;

    /**   content 返回体报文的出参，使用泛型兼容不同的类型*/
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Result setResultData(T data) {
        this.data = data;
        return this;
    }

    public Boolean IsSuccess(){
        return code.equals(SUCCESS);
    }



}