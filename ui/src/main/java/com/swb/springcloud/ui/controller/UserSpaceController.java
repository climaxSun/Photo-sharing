package com.swb.springcloud.ui.controller;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.UserException;
import com.swb.springcloud.ui.feign.Client.FlowerFeignClient;
import com.swb.springcloud.ui.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.ui.pojo.UserReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/userspance")
public class UserSpaceController {

    @Autowired
    public UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public FlowerFeignClient flowerFeignClient;

    @GetMapping("/profile")
    public String profile(@CookieValue(value = "token",required = false)String token, Model model){
        System.out.println(token);
        if (token==null) {
            return "redirect:/login";
        }
        RestResponse restResponse=userServiceFeignClient.getAuth(token);
        if(restResponse.getCode()!= 0){
            return "redirect:/login";
        }
        model.addAttribute("user",restResponse.getResult());
        return "userspance/profile";
    }

    @GetMapping("/u")
    public String gotoMyUserspance(@CookieValue(value = "token",required = false)String token){
        RestResponse<UserReturn> restResponse=userServiceFeignClient.getAuth(token);
        if(restResponse.getCode()!=0){
            return "login";
        }
        UserReturn userReturn= restResponse.getResult();
        return "redirect:/userspance/u/"+userReturn.getUsername();
    }


    @GetMapping("/avatar")
    public String avatar(){
        return "userspance/avatar";
    }

    @GetMapping("/shareArticle")
    public String shareArticle(){
        return "userspance/articleShare";
    }

    @GetMapping("/{userId}")
    public String u(@PathVariable(value = "userId")Long userId){
        RestResponse<UserReturn> restResponse= userServiceFeignClient.getUserById(userId);
        if(restResponse.getCode()!=0){
            throw new UserException(UserException.Type.USER_NOT_FOUND,"用户未找到");
        }
        UserReturn userReturn= restResponse.getResult();
        return "redirect:/userspance/u/"+userReturn.getUsername();
    }

    @GetMapping("/u/{readUsername}")
    public String u(@PathVariable(value = "readUsername")String readUsername, Model model,
                    @CookieValue(value = "token",required = false)String token,
                    @RequestParam(value="async",required=false) boolean async,
                    @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                    @RequestParam(value="pageSize",required=false,defaultValue="5") int pageSize,
                    @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword){
        Long user_id=0l;

        if(token!=null){
            RestResponse<UserReturn> userResponse =userServiceFeignClient.getAuth(token);
            if(userResponse.getCode()==0){
                user_id=userResponse.getResult().getId();
            }
        }
        RestResponse<UserReturn> readUserResponse =userServiceFeignClient.getUser(readUsername);
        if(readUserResponse==null){
            model.addAttribute("error",readUsername+"用户不存在!!!");
            return "fragments/error";
        }
        UserReturn readUser=readUserResponse.getResult();
        RestResponse<Object> flowerRestResponse = flowerFeignClient.getFlowers(readUser.getId(),user_id, pageIndex, pageSize, keyword);
        Map<String,Object> flowerMap= (Map<String, Object>) flowerRestResponse.getResult();
        model.addAttribute("readUser",readUser);
        model.addAttribute("loginUser",user_id);
        model.addAttribute("flowers",flowerMap.get("flower"));
        model.addAttribute("totalElements",flowerMap.get("totalElements"));
        model.addAttribute("number",flowerMap.get("number"));
        model.addAttribute("totalPages",flowerMap.get("totalPages"));
        model.addAttribute("first",flowerMap.get("isFirst"));
        model.addAttribute("last",flowerMap.get("isLast"));
        model.addAttribute("pageSize",flowerMap.get("pageSize"));
        model.addAttribute("keyword",keyword);
        return (async==true?"userspance/u :: #mainContainerRepleace":"userspance/u");
    }

    @GetMapping("/share")
    public String share(@CookieValue(value = "token",required = false)String token){
        if(token==null || userServiceFeignClient.getAuth(token).getCode()!=0)
            return "redirect:/login";
        return "userspance/share";
    }

}
