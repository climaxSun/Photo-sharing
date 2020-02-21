package com.swb.springcloud.userservice.controller;

import com.swb.springcloud.userservice.Utils.UserChangeHelp;
import com.swb.springcloud.userservice.common.RestResponse;
import com.swb.springcloud.userservice.exception.ApplyException;
import com.swb.springcloud.userservice.exception.UserException;
import com.swb.springcloud.userservice.pojo.Apply;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.pojo.UserReturn;
import com.swb.springcloud.userservice.service.ApplyService;
import com.swb.springcloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplyService applyService;

    @PostMapping("/user")
    public RestResponse<UserReturn> editUser(@RequestParam(value = "userId",required = false,defaultValue = "0")Long userId,
        @RequestParam(value = "username",required = false)String username,@RequestParam(value = "lv")Integer lv, @RequestParam(value = "name")String name,
        @RequestParam(value = "email")String email, @RequestParam(value = "password",required = false)String password,
        @CookieValue(value = "adminToken",required = false)String adminToken){
        User admin=isAdmin(adminToken);
        int adminLv=admin.getLv();
        User user;
        if(userId==0){
            if(adminLv==3 && (lv.equals(3) || lv.equals(0))){
                throw new UserException(UserException.Type.USER_Insufficient_permissions,"权限不足");
            }
            User user1=userService.emailHave(email);
            if(user1!=null){
                throw new UserException(UserException.Type.USER_EMAIL_HAVE,"邮箱"+email+"已经注册");
            }
            user1=userService.findByUsername(username);
            if(user1!=null){
                throw new UserException(UserException.Type.USER_HAVE,"登录名"+username+"已存在");
            }
            user=new User(name, email, username, password);
            user.setLv(lv);
            user=userService.saveUser(user);
        }else {
            user=userService.getUserById(userId);
            if(!admin.getId().equals(user.getId())){
                int userLv=user.getLv();
                if(adminLv==3 && ((userLv==0)||(userLv==3))){
                    System.out.println("adminLv="+adminLv);
                    System.out.println("userLv="+userLv);
                    throw new UserException(UserException.Type.USER_Insufficient_permissions,"权限不足");
                }
            }
            User user1=userService.emailHave(email);
            if(user1!=null && !user1.getId().equals(userId)){
                throw new UserException(UserException.Type.USER_EMAIL_HAVE,"邮箱"+email+"已经注册");
            }
            user1=userService.findByUsername(username);
            if(user1!=null && !user1.getId().equals(userId)){
                throw new UserException(UserException.Type.USER_HAVE,"登录名"+username+"已存在");
            }
            user.setName(name);
            user.setEmail(email);
            if(!admin.getId().equals(user.getId())){
                user.setLv(lv);
            }
            userService.saveUser(user);
        }
        return RestResponse.success(UserChangeHelp.userChange(user));
    }

    @DeleteMapping("/user")
    public RestResponse<UserReturn> deleteUser(@RequestParam(value = "userId",required = false,defaultValue = "0")Long userId,
                                               @CookieValue(value = "adminToken",required = false)String adminToken){
        if(userId==0){
            throw new UserException(UserException.Type.USER_NOT_FOUND,"不存在的用户Id"+userId);
        }
        int adminLv=isAdmin(adminToken).getLv();
        if(adminLv==0){
            User user=userService.getUserById(userId);
            if(user.getLv().equals(0)){
                throw new UserException(UserException.Type.USER_Insufficient_permissions,"权限不足");
            }
        }else{
            User user=userService.getUserById(userId);
            if(user.getLv().equals(3) || user.getLv().equals(0)){
                throw new UserException(UserException.Type.USER_Insufficient_permissions,"权限不足");
            }
        }
        userService.deleteUser(userId);
        return RestResponse.success();
    }

    private User isAdmin(String token){
        User admin=userService.getLoginedUserByToken(token);
        if(admin.getLv()!=0 &&admin.getLv()!=3){
            throw new UserException(UserException.Type.USER_NOT_LOGIN,"管理员失效");
        }
        return admin;
    }

    @GetMapping("/apply")
    public RestResponse<Apply> edit(@RequestParam(value = "id",required = false,defaultValue = "0")Long id,
                                    @RequestParam(value = "result")boolean result,@CookieValue(value = "adminToken",required = false)String adminToken){
        isAdmin(adminToken);
        Apply apply=applyService.findApplyById(id);
        if(apply.isHandle()){
            throw new ApplyException(ApplyException.Type.APPLY_handle,"已处理");
        }
        applyService.updateApply(apply,result);
        return RestResponse.success();
    }

}
