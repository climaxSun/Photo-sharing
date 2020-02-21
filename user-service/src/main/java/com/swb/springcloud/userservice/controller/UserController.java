package com.swb.springcloud.userservice.controller;

import com.swb.springcloud.userservice.Utils.UserChangeHelp;
import com.swb.springcloud.userservice.common.RestResponse;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.pojo.UserReturn;
import com.swb.springcloud.userservice.service.EmailServiceImpl;
import com.swb.springcloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public EmailServiceImpl emailService;

    @RequestMapping("/getById")
    public RestResponse<UserReturn> getUserById(Long id){
        User user = userService.getUserById(id);
        return RestResponse.success(UserChangeHelp.userChange(user));
    }

    @RequestMapping("/getUserByUsername")
    public RestResponse<UserReturn> getUserByUsername(String username){
        User user = userService.getUserByUsername(username);
        return RestResponse.success(UserChangeHelp.userChange(user));
    }

    //----------------------注册----------------------------------
    @RequestMapping("/register")
    public RestResponse<UserReturn> register(@RequestBody User user){
        user.setLv(1);
        userService.registerUser(user);
        return RestResponse.success();
    }

    //------------------------登录/鉴权--------------------------
    @PostMapping("/login")
    public RestResponse<UserReturn> login(@RequestBody User user){
        User finalUser = userService.login(user);
        return RestResponse.success(UserChangeHelp.userChange(finalUser));
    }

    @RequestMapping("/getAuth")
    public RestResponse<UserReturn> getUserReturn(String token){
        User finalUser = userService.getLoginedUserByToken(token);
        return RestResponse.success(UserChangeHelp.userChange(finalUser));
    }

    @RequestMapping("/getAuthUser")
    public RestResponse<User> getUser(String token){
        User finalUser = userService.getLoginedUserByToken(token);
        return RestResponse.success(finalUser);
    }

    @GetMapping("/logout")
    public RestResponse<UserReturn> logout(@CookieValue(value = "token",required = false)String token){
        userService.invalidate(token);
        return RestResponse.success();
    }

    @PostMapping("/retrievePWD")
    public RestResponse<UserReturn> editPWD(String email,String yzm,String newPassword){
        User user=userService.emailHave(email);
        emailService.yzEmailYZM(email,yzm,2);
        user.setPassword(newPassword);
        userService.saveUser(user);
        return RestResponse.success();
    }

    @RequestMapping("/getUsersByIds")
    public RestResponse<List<UserReturn>> getUsersByIds(@RequestBody List<Long> ids){
        List<UserReturn> userReturnList=userService.findByIdIn(ids);
        return RestResponse.success(userReturnList);
    }

    @RequestMapping("/getAll")
    public RestResponse<Map<String,Object>> getUsers(@RequestParam(value = "pageIndex",defaultValue = "0",required = false) int pageIndex,
                                                   @RequestParam(value = "name",defaultValue = "",required = false)String name){
        Page<User> userPage=userService.findUserByName(pageIndex,name);
        Map<String,Object> map=new HashMap<>();
        map.put("users",UserChangeHelp.userChange(userPage.getContent()));
        map.put("totalElements",userPage.getTotalElements());
        map.put("totalPages",userPage.getTotalPages());
        map.put("isFirst",userPage.isFirst());
        map.put("number",userPage.getNumber());
        map.put("isLast",userPage.isLast());
        return RestResponse.success(map);
    }
}
