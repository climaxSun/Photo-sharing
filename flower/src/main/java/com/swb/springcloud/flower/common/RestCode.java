package com.swb.springcloud.flower.common;

public enum RestCode {
	OK(0,"ok"),
	UNKNOWN_ERROR(1,"未知异常"),
	TOKEN_INVALID(2,"用户已过期，请重新登录"),//token失效
	USER_NOT_EXIST(3,"用户不存在"),
	USER_EMAIL_HAVE(4,"邮箱已被注册，你可以尝试找回密码"),
	USER_USERNAME_HAVE(5,"登录名已存在"),
	YZM_Invalid(6,"验证码失效"),
	EMAIL_SEND_FAILED(7,"邮件发送失败"),
	EMAIL_REPEAT_SENT(8,"邮件已经发送,请勿重复发送"),
	YZM_ERROR(9,"验证码错误"),
	XITONG_FANMANG(10,"系统繁忙，请稍后再试!"),
	USER_USERNAME_OR_PASSWORD_NULL(11,"用户名或密码为空"),
	USER_USERNAME_NOT_HAVA(12,"用户不存在，请重新输入"),
	USER_PASSWORD_ERROR(13,"密码错误,请重新输入"),
	USER_EMAIL_NOT_REGISTER(14,"该邮箱未注册，请先注册"),
	FLOWER_NOT_FIND(15,"找不到花图"),
	ES_SERVICE_ERROR(16,"es服务异常"),
	USER_Insufficient_permissions(17,"权限不足"),
	ARICLE_NOT_FIND(18,"文章不存在"),
	WRONG_PAGE(10100,"页码不合法"),
    LACK_PARAMS(10101,"缺少参数");

	public final int code;
	public final String msg;
	
	private RestCode(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

}
