package com.swb.springcloud.userservice.exception;

public class ApplyException extends RuntimeException implements WithTypeException{

    private static final long serialVersionUID = 1L;

    private ApplyException.Type type;

    public ApplyException(String message) {
        super(message);
        this.type = ApplyException.Type.LACK_PARAMTER;
    }

    public ApplyException(ApplyException.Type type, String message) {
        super(message);
        this.type = type;
    }

    public ApplyException.Type type(){
        return type;
    }


    public enum Type{
        APPLY_NOT_HAVE,APPLY_handle,LACK_PARAMTER,REPEAT_APPLY,APPLY_Unprocessed,APPLY_USER_NOT_HAVE,APPLY_CAN_NOT,SHARE_NUMVER_LESS
    }
}