package com.swb.springcloud.userservice.exception;

public class EmailException extends RuntimeException implements WithTypeException{

    private static final long serialVersionUID = 1L;

    private EmailException.Type type;

    public EmailException(String message) {
        super(message);
        this.type = EmailException.Type.LACK_PARAMTER;
    }

    public EmailException(EmailException.Type type, String message) {
        super(message);
        this.type = type;
    }

    public EmailException.Type type(){
        return type;
    }


    public enum Type{
        YZM_INVALID,LACK_PARAMTER,EMAIL_SEND_FAILED,EMAIL_REPEAT_SENT,YZM_ERROR
    }
}
