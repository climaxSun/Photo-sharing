package com.swb.springcloud.ui.controller;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Client.FlowerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private FlowerFeignClient flowerFeignClient;

    @GetMapping("/{articleId}")
    public String getArticle(@PathVariable(value = "articleId")Long articleId, Model model){
        RestResponse<Object> restResponse= flowerFeignClient.getArticleById(articleId);
        if(restResponse.getCode()!=0){
            model.addAttribute("error",restResponse.getMsg());
            return "fragments/error";
        }
        model.addAttribute("article",restResponse.getResult());
        return "article/article";
    }

    @GetMapping("/all")
    public String getArticle(@RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                             @RequestParam(value = "keyword",required = false,defaultValue = "")String keyword,Model model,
                             @RequestParam(value="async",required=false) boolean async){
        RestResponse<Map<String,Object>> restResponse= flowerFeignClient.getArticleAll(pageIndex,pageSize,keyword);
        if(restResponse.getCode()!=0){
            model.addAttribute("error",restResponse.getMsg());
            return "fragments/error";
        }
        Map<String,Object> map=restResponse.getResult();
        model.addAttribute("articles",map.get("articles"));
        model.addAttribute("totalElements",map.get("totalElements"));
        model.addAttribute("first",map.get("isFirst"));
        model.addAttribute("number",map.get("number"));
        model.addAttribute("keyword",keyword);
        model.addAttribute("last",map.get("isLast"));
        model.addAttribute("totalPages",map.get("totalPages"));
        model.addAttribute("pageSize",map.get("pageSize"));
        return (async==true?"article/articleList :: #ArticleMainContainerRepleace":"article/articleList");
    }
}
