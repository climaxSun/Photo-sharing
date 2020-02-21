package com.swb.springcloud.userservice.common;


import com.google.common.collect.ImmutableMap;
import com.swb.springcloud.userservice.exception.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;

public class Exception2CodeRepo {
	
	private static final ImmutableMap<Object, RestCode> MAP = ImmutableMap.<Object, RestCode>builder()
			.put(IllegalParamsException.Type.WRONG_PAGE_NUM,RestCode.WRONG_PAGE)
            .put(IllegalStateException.class,RestCode.UNKNOWN_ERROR)
	        .put(UserException.Type.USER_NOT_LOGIN,RestCode.TOKEN_INVALID)
	        .put(UserException.Type.USER_NOT_FOUND,RestCode.USER_NOT_EXIST)
	        .put(UserException.Type.USER_AUTH_FAIL,RestCode.USER_NOT_EXIST)
			.put(UserException.Type.USER_EMAIL_HAVE,RestCode.USER_EMAIL_HAVE)
			.put(UserException.Type.USER_HAVE,RestCode.USER_USERNAME_HAVE)
			.put(UserException.Type.USER_USERNAMEORPASSWORD_NULL,RestCode.USER_USERNAME_OR_PASSWORD_NULL)
			.put(UserException.Type.USER_Insufficient_permissions,RestCode.USER_Insufficient_permissions)
			.put(UserException.Type.USER_USERNAME_NOT_HAVA,RestCode.USER_USERNAME_NOT_HAVA)
			.put(UserException.Type.USER_PASSWORD_ERROR,RestCode.USER_PASSWORD_ERROR)
			.put(UserException.Type.USER_EMAIL_NOT_REGISTER,RestCode.USER_EMAIL_NOT_REGISTER)
			.put(RedisException.Type.Redis_Error,RestCode.XITONG_FANMANG)
			.put(EmailException.Type.EMAIL_REPEAT_SENT,RestCode.EMAIL_REPEAT_SENT)
			.put(EmailException.Type.EMAIL_SEND_FAILED,RestCode.EMAIL_SEND_FAILED)
			.put(EmailException.Type.YZM_ERROR,RestCode.YZM_ERROR)
			.put(EmailException.Type.YZM_INVALID,RestCode.YZM_Invalid)
			.put(ApplyException.Type.APPLY_NOT_HAVE,RestCode.APPLY_NOT_HAVE)
			.put(ApplyException.Type.APPLY_handle,RestCode.APPLY_handle)
			.put(ApplyException.Type.REPEAT_APPLY,RestCode.REPEAT_APPLY)
			.put(ApplyException.Type.APPLY_USER_NOT_HAVE,RestCode.APPLY_USER_NOT_HAVE)
			.put(ApplyException.Type.APPLY_CAN_NOT,RestCode.APPLY_CAN_NOT)
			.put(ApplyException.Type.SHARE_NUMVER_LESS,RestCode.SHARE_NUMVER_LESS)
			.put(ApplyException.Type.APPLY_Unprocessed,RestCode.APPLY_Unprocessed)
			.build();
	
	private static Object getType(Throwable throwable){
	   try {
		  return FieldUtils.readDeclaredField(throwable, "type", true);
	   } catch (Exception e) {
		  return null;
	   }	
	}
	
	
	public static RestCode getCode(Throwable throwable) {
		if (throwable == null) {
			return RestCode.UNKNOWN_ERROR;
		}
		Object target = throwable;
		if (throwable instanceof WithTypeException) {
			Object type = getType(throwable);
			if (type != null ) {
				target = type;
			}
		}
		RestCode restCode =  MAP.get(target);
		if (restCode != null) {
			return restCode;
		}
		Throwable rootCause = ExceptionUtils.getRootCause(throwable);
		if (rootCause != null) {
			return getCode(rootCause);
		}
		return restCode.UNKNOWN_ERROR;
	}

}
