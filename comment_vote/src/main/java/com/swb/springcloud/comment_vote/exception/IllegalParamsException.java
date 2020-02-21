package com.swb.springcloud.comment_vote.exception;

public class IllegalParamsException extends RuntimeException implements WithTypeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Type type;
	
	public IllegalParamsException(){
		
	}
	
	public IllegalParamsException(Type type, String msg){
		super(msg);
		this.type = type;
	}
	
	public Type type(){
		return type;
	}
	
	public enum Type{
		WRONG_PAGE_NUM,WRONG_TYPE,XITONG_FANMANG,REPORT_NOTHAVE,REPORT_PROCESSED,ES_SERVICE_ERROR
    }
}
