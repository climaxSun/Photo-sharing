package com.swb.springcloud.userservice.controller;


import com.swb.springcloud.userservice.Utils.ToolKit;
import com.swb.springcloud.userservice.common.RestResponse;
import com.swb.springcloud.userservice.exception.UserException;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.pojo.UserReturn;
import com.swb.springcloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userspance")
public class UserSpanceController {

    @Autowired
    public UserService userService;

    @PostMapping("/profile")
    public RestResponse<UserReturn> profile(@CookieValue(value = "token",required = false)String token, User user){

        User u=userService.getLoginedUserByToken(token);
        if(!u.getEmail().equals(user.getEmail())){
            User user2=userService.emailHave(user.getEmail());
            if(user2!=null)
                throw new UserException(UserException.Type.USER_EMAIL_HAVE,"邮箱"+user2.getEmail()+"已存在");
        }
        u.setName(user.getName());
        u.setIntroduction(ToolKit.toHtml(user.getIntroduction()));
        u.setIntroduction(user.getIntroduction());
        u.setEmail(user.getEmail());
        userService.saveUser(u);
        return new RestResponse().success();
    }

    @PostMapping("/avatar")
    public RestResponse<UserReturn> avatar(@CookieValue(value = "token",required = false)String token, String avatarUrl){
        User u=userService.getLoginedUserByToken(token);
        u.setAvatar(avatarUrl);
        userService.saveUser(u);
        return new RestResponse().success();
    }

    @PostMapping("/pwd")
    public RestResponse<UserReturn> pwd(@CookieValue(value = "token",required = false)String token,String pwd){
        User u=userService.getLoginedUserByToken(token);
        u.setPassword(pwd);
        userService.saveUser(u);
        return new RestResponse().success();
    }
}
