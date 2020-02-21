package com.swb.springcloud.userservice.controller;

import com.swb.springcloud.userservice.Utils.UserChangeHelp;
import com.swb.springcloud.userservice.common.RestResponse;
import com.swb.springcloud.userservice.exception.ApplyException;
import com.swb.springcloud.userservice.pojo.Apply;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.service.ApplyService;
import com.swb.springcloud.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;


    @Autowired
    private UserService userService;

    @PostMapping
    public RestResponse<Object> addApply(@CookieValue(value = "token",required = false)String token){
        User user=userService.getLoginedUserByToken(token);
        if(!user.getLv().equals(1)){
            throw new ApplyException(ApplyException.Type.APPLY_CAN_NOT,user.getUsername()+"不是普通用户");
        }
        if(user.getSharesNumber()<2){
            throw new ApplyException(ApplyException.Type.SHARE_NUMVER_LESS,user.getUsername()+"分享属性不足");
        }
        applyService.save(user);
        return RestResponse.success();
    }

    @GetMapping
    public RestResponse<Object> getApply(@CookieValue(value = "token",required = false)String token){
        User user=userService.getLoginedUserByToken(token);
        Apply apply=applyService.findApplyByUser(user);
        return RestResponse.success(apply);
    }

    @GetMapping("/all")
    public RestResponse<Map<String,Object>> getAllApply(@RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex) {
        Page<Apply> allApply=applyService.getAllApply(pageIndex);
        Map<String,Object> map=new HashMap<>();
        map.put("applys", allApply.getContent());
        map.put("totalElements",allApply.getTotalElements());
        map.put("totalPages",allApply.getTotalPages());
        map.put("number",allApply.getNumber());
        map.put("isFirst",allApply.isFirst());
        map.put("isLast",allApply.isLast());
        return RestResponse.success(map);
    }
}
