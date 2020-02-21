package com.swb.springcloud.comment_vote.config;

import com.swb.springcloud.comment_vote.comment.Exception2CodeRepo;
import com.swb.springcloud.comment_vote.comment.RestCode;
import com.swb.springcloud.comment_vote.comment.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public RestResponse<Object> defaultExceptionHandler(HttpServletRequest request, Throwable throwable){
        RestCode restCode = Exception2CodeRepo.getCode(throwable);
        RestResponse<Object> response = new RestResponse<Object>(restCode.code,restCode.msg);
        return response;
    }

}
