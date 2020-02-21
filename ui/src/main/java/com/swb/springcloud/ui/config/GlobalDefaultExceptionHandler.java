package com.swb.springcloud.ui.config;

import com.swb.springcloud.ui.common.Exception2CodeRepo;
import com.swb.springcloud.ui.common.RestCode;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Throwable.class)
    public String defaultExceptionHandler(HttpServletRequest request, Throwable throwable, Model model){
        RestCode restCode = Exception2CodeRepo.getCode(throwable);
        model.addAttribute("error",restCode.msg);
        return "error";
    }

}
