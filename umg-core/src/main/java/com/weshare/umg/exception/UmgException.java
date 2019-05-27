package com.weshare.umg.exception;

import com.weshare.umg.util.CodeEnum;


public class UmgException extends Exception {
    private Integer code;
    private String message;

    public UmgException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public UmgException(CodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }
    public UmgException(CodeEnum codeEnum, Throwable cause){
        super(codeEnum.getMessage(),cause);
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
