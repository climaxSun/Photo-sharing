package com.swb.springcloud.userservice.common;

public enum RestCode {
	OK(0,"ok"),
	UNKNOWN_ERROR(1,"未知异常"),
	TOKEN_INVALID(2,"用户已过期，请重新登录"),//token失效
	USER_NOT_EXIST(3,"用户不存在"),
	USER_EMAIL_HAVE(4,"邮箱已存在"),
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
	USER_Insufficient_permissions(15,"权限不足"),
	APPLY_NOT_HAVE(16,"不存在的申请"),
	APPLY_handle(17,"该申请已处理"),
	REPEAT_APPLY(18,"重复申请"),
	APPLY_Unprocessed(19,"申请还未处理"),
	APPLY_USER_NOT_HAVE(20,"用户没有申请"),
	APPLY_CAN_NOT(21,"只有普通用户才能申请"),
	SHARE_NUMVER_LESS(22,"分享数篇数不足,不能申请"),
	WRONG_PAGE(10100,"页码不合法"),
    LACK_PARAMS(10101,"缺少参数");

	public final int code;
	public final String msg;
	
	private RestCode(int code,String msg){
		this.code = code;
		this.msg = msg;
	}

}
