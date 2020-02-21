package com.swb.springcloud.ui.common;


import com.google.common.collect.ImmutableMap;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.exception.UserException;
import com.swb.springcloud.ui.exception.WithTypeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;

public class Exception2CodeRepo {
	
	private static final ImmutableMap<Object, RestCode> MAP = ImmutableMap.<Object, RestCode>builder()
			.put(IllegalParamsException.Type.WRONG_PAGE_NUM,RestCode.WRONG_PAGE)
            .put(IllegalStateException.class,RestCode.UNKNOWN_ERROR)
			.put(IllegalParamsException.Type.NETWORK_ERROR,RestCode.NETWORK_ERROR)
			.put(IllegalParamsException.Type.XITONG_FANMANG,RestCode.XITONG_FANMANG)
			.put(IllegalParamsException.Type.FLOWER_NOT_FIND,RestCode.FLOWER_NOT_FIND)
			.put(IllegalParamsException.Type.ES_SERVICE_ERROR,RestCode.ES_SERVICE_ERROR)
	        .put(UserException.Type.USER_NOT_LOGIN,RestCode.TOKEN_INVALID)
	        .put(UserException.Type.USER_NOT_FOUND,RestCode.USER_NOT_EXIST)
	        .put(UserException.Type.USER_AUTH_FAIL,RestCode.USER_NOT_EXIST)
			.put(UserException.Type.USER_EMAIL_HAVE,RestCode.USER_EMAIL_HAVE)
			.put(UserException.Type.USER_HAVE,RestCode.USER_USERNAME_HAVE)
			.put(UserException.Type.USER_USERNAMEORPASSWORD_NULL,RestCode.USER_USERNAME_OR_PASSWORD_NULL)
			.put(UserException.Type.USER_USERNAME_NOT_HAVA,RestCode.USER_USERNAME_NOT_HAVA)
			.put(UserException.Type.USER_PASSWORD_ERROR,RestCode.USER_PASSWORD_ERROR)
			.put(UserException.Type.USER_EMAIL_NOT_REGISTER,RestCode.USER_EMAIL_NOT_REGISTER)
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
