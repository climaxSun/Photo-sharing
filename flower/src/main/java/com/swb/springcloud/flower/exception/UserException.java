package com.swb.springcloud.flower.exception;

public class UserException extends RuntimeException implements WithTypeException {
  
  private static final long serialVersionUID = 1L;

  private Type type;
  
  public UserException(String message) {
    super(message);
    this.type = Type.LACK_PARAMTER;
  }

  public UserException(Type type, String message) {
    super(message);
    this.type = type;
  }
  
  public  Type type(){
    return type;
  }

  
  public enum Type{
    WRONG_PAGE_NUM,LACK_PARAMTER,USER_NOT_LOGIN,USER_NOT_FOUND,USER_AUTH_FAIL,USER_EMAIL_HAVE,USER_HAVE,
    USER_USERNAMEORPASSWORD_NULL,USER_USERNAME_NOT_HAVA,USER_PASSWORD_ERROR,USER_EMAIL_NOT_REGISTER,USER_Insufficient_permissions;
  }

}
