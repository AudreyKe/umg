package com.weshare.umg.exception;


public class UmgApiException extends Exception {
    private String message;

    public UmgApiException(String message){
        super(message);
        this.message = message;
    }


    public UmgApiException(String message, Throwable cause){
        super(message,cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
