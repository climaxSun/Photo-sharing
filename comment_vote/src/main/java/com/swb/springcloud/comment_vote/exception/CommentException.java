package com.swb.springcloud.comment_vote.exception;

public class CommentException extends RuntimeException implements WithTypeException {

  private static final long serialVersionUID = 1L;

  private Type type;

  public CommentException(String message) {
    super(message);
    this.type = Type.LACK_PARAMTER;
  }

  public CommentException(Type type, String message) {
    super(message);
    this.type = type;
  }
  
  public  Type type(){
    return type;
  }

  
  public enum Type{
    LACK_PARAMTER,Comment_NOT_FOUND
  }

}
