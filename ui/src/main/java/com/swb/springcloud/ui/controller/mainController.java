package com.swb.springcloud.ui.controller;

import com.swb.springcloud.ui.common.RestCode;
import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Client.ESFeignClient;
import com.swb.springcloud.ui.feign.Client.FlowerFeignClient;
import com.swb.springcloud.ui.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.ui.pojo.TagVO;
import com.swb.springcloud.ui.pojo.UserReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class mainController {

    @Autowired
    public UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public ESFeignClient esFeignClient;

    @Autowired
    public FlowerFeignClient flowerFeignClient;

    @GetMapping("/login")
    public String login(@CookieValue(value = "token",required = false)String token){
        RestResponse restResponse=userServiceFeignClient.getAuth(token);
        if(restResponse.getCode()== RestCode.TOKEN_INVALID.code){
            return "login";
        }
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value="order",required=false,defaultValue="new") String order,
                        @RequestParam(value="keyword",required=false,defaultValue="" ) String keyword,
                        @RequestParam(value="pageIndex",required=false,defaultValue="0") int pageIndex,
                        @RequestParam(value="pageSize",required=false,defaultValue="10") int pageSize,
                        @RequestParam(value="async",required=false) boolean async, Model model) {
        if(keyword.length()>0){
            keyword=keyword.replaceAll("花","");
        }
        RestResponse<Map<String, Object>> restResponse=esFeignClient.getFlowers(order, keyword, pageIndex, pageSize);
        Map<String, Object> map=restResponse.getResult();
        model.addAttribute("flowers", map.get("flower"));
        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("last",map.get("isLast"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("pageSize",map.get("pageSize"));
        if(!async){
            model.addAttribute("articles",flowerFeignClient.getArticleAll(0,5,"").getResult().get("articles"));
            List<TagVO> tagVOList=esFeignClient.getHotTag().getResult();
            model.addAttribute("tags",tagVOList);
            List<Long> hotUserIds=esFeignClient.getHotUser().getResult();
            List<UserReturn> userReturnList=userServiceFeignClient.getUserByIds(hotUserIds).getResult();
            model.addAttribute("hotUsers",userReturnList);
        }

        return (async==true? "/index :: #mainContainerRepleace":"/index");
    }

    @GetMapping("/")
    public String index1() {
        return "redirect:/index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/header")
    public String header(){
        return "header";
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "findPwd";
    }

    @GetMapping("/identification")
    public String identification(){
        return "identification";
    }

}
