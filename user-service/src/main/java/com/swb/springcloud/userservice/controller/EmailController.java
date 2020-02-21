package com.swb.springcloud.userservice.controller;

import com.swb.springcloud.userservice.common.RestResponse;
import com.swb.springcloud.userservice.exception.UserException;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.service.EmailServiceImpl;
import com.swb.springcloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    public EmailServiceImpl emailService;

    @Autowired
    public UserService userService;

    @GetMapping("/getYZM")
    public RestResponse<Object> getRegisterYZM(String email,int zt){
        User user=userService.emailHave(email);
        if (user!=null && zt==1){
            throw new UserException(UserException.Type.USER_EMAIL_HAVE,"邮箱"+email+"已经注册");
        }
        else if(user==null && zt==2){
            throw new UserException(UserException.Type.USER_EMAIL_NOT_REGISTER,"邮箱"+email+"未注册");
        }
        emailService.sendRegisterYZM(email);
        return RestResponse.success();
    }

    @GetMapping("/yzYZM")
    public RestResponse<Object> yzYZM(String email,String yzm){
        emailService.yzEmailYZM(email,yzm,1);
        return RestResponse.success();
    }
}
