package com.swb.springcloud.ui.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.feign.Client.FlowerFeignClient;
import com.swb.springcloud.ui.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.ui.pojo.Flower;
import com.swb.springcloud.ui.pojo.UserReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private FlowerFeignClient flowerFeignClient;

    @GetMapping
    public String admins(@CookieValue(value = "adminToken",required = false)String adminToken, Model model){
        if(adminToken==null){
            return "admin/admin-login";
        }
        RestResponse<UserReturn> returnRestResponse = userServiceFeignClient.getAuth(adminToken);
        if(returnRestResponse.getCode()!=0){
            return "admin/admin-login";
        }
        UserReturn userReturn=returnRestResponse.getResult();
        if(userReturn.getLv()!=0 &&userReturn.getLv()!=3){
            return "admin/admin-login";
        }
        model.addAttribute("admin",userReturn);
        return "admin/manager";
    }

    @GetMapping("/login")
    public String adminLong(){
        return "admin/admin-login";
    }

    @GetMapping("/users")
    public String users(@RequestParam(value = "pageIndex",defaultValue = "0",required = false)int pageIndex,
                       @RequestParam(value="name",required=false,defaultValue="") String name,Model model){
        RestResponse<Map<String,Object>> mapRestResponse=userServiceFeignClient.getAllUserByName(name,pageIndex);
        if(mapRestResponse.getCode()!=0){
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"UserService返回不成功");
        }
        Map<String,Object> map=mapRestResponse.getResult();
        List<UserReturn> userReturnList= (List<UserReturn>) map.get("users");
        model.addAttribute("users",userReturnList);
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("last",map.get("isLast"));
        model.addAttribute("name",name);
        return "admin/userList";
    }

    @GetMapping("/applys")
    public String applys(@RequestParam(value = "pageIndex",defaultValue = "0",required = false)int pageIndex,Model model){
        RestResponse<Map<String,Object>> mapRestResponse=userServiceFeignClient.getApplyAll(pageIndex);
        if(mapRestResponse.getCode()!=0){
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"UserService返回不成功");
        }
        Map<String,Object> map=mapRestResponse.getResult();
        model.addAttribute("applys",map.get("applys"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("last",map.get("isLast"));
        model.addAttribute("number",map.get("number"));
        return "admin/apply";
    }

    @GetMapping("/user")
    public String user(@RequestParam(value = "userId")Long userId,Model model) {
        RestResponse<UserReturn> restResponse=userServiceFeignClient.getUserById(userId);
        if(restResponse.getCode()!=0){
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"UserService返回不成功");
        }
        model.addAttribute("user",restResponse.getResult());
        return "admin/user/edit";
    }

    @GetMapping("/flower")
    public String flower(@RequestParam(value = "flowerId")Long flowerId,Model model) {
        RestResponse<Flower> restResponse=flowerFeignClient.getFlower(flowerId);
        if(restResponse.getCode()==15){
            throw new IllegalParamsException(IllegalParamsException.Type.FLOWER_NOT_FIND,"花图没找到");
        }
        if(restResponse.getCode()!=0){
            System.out.println(restResponse);
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"Flower返回异常");
        }
        model.addAttribute("flower",restResponse.getResult());
        return "admin/model/flower";
    }

    @GetMapping("/article/{articleId}")
    public String article(@PathVariable(value = "articleId")Long articleId, Model model) {
        RestResponse<Object> restResponse=flowerFeignClient.getArticleById(articleId);
        if(restResponse.getCode()!=0){
            System.out.println(restResponse);
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"Flower返回异常");
        }
        model.addAttribute("article",restResponse.getResult());
        return "admin/model/article";
    }

    @GetMapping("/articles")
    public String articles(@RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                           @RequestParam(value = "keyword",required = false,defaultValue = "")String keyword, Model model) {
        RestResponse<Map<String,Object>> restResponse=flowerFeignClient.getArticleAll(pageIndex,10,keyword);
        if(restResponse.getCode()!=0){
            System.out.println(restResponse);
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"Flower返回异常");
        }
        Map<String,Object> map=restResponse.getResult();
        model.addAttribute("articles",map.get("articles"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("last",map.get("isLast"));
        model.addAttribute("keyword",keyword);
        return "admin/articleList";
    }
}
