package com.swb.springcloud.userservice.exception;

public class RedisException extends RuntimeException implements WithTypeException{

    private static final long serialVersionUID = 1L;

    private RedisException.Type type;

    public RedisException(String message) {
        super(message);
        this.type = RedisException.Type.LACK_PARAMTER;
    }

    public RedisException(RedisException.Type type, String message) {
        super(message);
        this.type = type;
    }

    public RedisException.Type type(){
        return type;
    }


    public enum Type{
        Redis_Error,LACK_PARAMTER
    }
}