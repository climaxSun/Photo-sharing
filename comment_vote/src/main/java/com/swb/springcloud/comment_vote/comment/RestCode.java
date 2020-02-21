package com.swb.springcloud.comment_vote.comment;

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
	COMMENT_NOTHAVE(9,"评论已经不存在，请刷新页面试试"),
	XITONG_FANMANG(10,"系统繁忙，请稍后再试!"),
	USER_USERNAME_OR_PASSWORD_NULL(11,"用户名或密码为空"),
	USER_USERNAME_NOT_HAVA(12,"用户不存在，请重新输入"),
	USER_PASSWORD_ERROR(13,"密码错误,请重新输入"),
	USER_EMAIL_NOT_REGISTER(14,"该邮箱未注册，请先注册"),
	REPORT_ERROR(15,"该举报异常，刷新页面重试"),
	REPORT_PROCESSED(16,"该举报已处理"),
	AUTH_NOT_HAVA(17,"权限不足"),
	ES_SERVICE_ERROR(18,"ES服务错误"),
	WRONG_PAGE(10100,"页码不合法"),
    LACK_PARAMS(10101,"缺少参数");

	public final int code;
	public final String msg;
	
	private RestCode(int code, String msg){
		this.code = code;
		this.msg = msg;
	}

}
