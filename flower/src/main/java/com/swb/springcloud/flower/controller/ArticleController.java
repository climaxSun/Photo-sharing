package com.swb.springcloud.flower.controller;

import com.swb.springcloud.flower.common.RestCode;
import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.exception.UserException;
import com.swb.springcloud.flower.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.flower.pojo.Article;
import com.swb.springcloud.flower.pojo.User;
import com.swb.springcloud.flower.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ArticleController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/article")
    public RestResponse<Article> saveArticle(@CookieValue(value = "token", required = false) String token, @RequestParam(value = "title") String title,
                                             @RequestParam(value = "content") String content, @RequestParam(value = "tag") String tag) {
        RestResponse<User> restResponse = userServiceFeignClient.getAuths(token);
        if (restResponse.getCode() == RestCode.TOKEN_INVALID.code) {
            throw new UserException(UserException.Type.USER_NOT_LOGIN, "用户已过期，请重新登录");
        }
        User user = restResponse.getResult();
        if (user.getLv() != 2) {
            throw new UserException(UserException.Type.USER_Insufficient_permissions, "用户" + user.getName() + "发布文章的权限不足");
        }
        Article article = new Article(user, content, title);
        article.setTags(tag);
        article = articleService.saveArticle(article);
        return RestResponse.success(article);
    }

    @GetMapping("/article/{articleId}")
    public RestResponse<Article> getArticle(@PathVariable(value = "articleId") Long articleId) {
        Article article = articleService.getArticleById(articleId);
        return RestResponse.success(article);
    }

    @GetMapping("/articles")
    public RestResponse<Map<String,Object>> getArticles(@RequestParam(value = "pageIndex",required = false,defaultValue = "0")int pageIndex,
                                                        @RequestParam(value = "pageSize",required = false,defaultValue = "10")int pageSize,
                                                        @RequestParam(value = "keyword",required = false,defaultValue = "")String keyword) {
        if(keyword.length()!=0){
            keyword=keyword.replaceAll("花","");
        }
        Page<Article> articles=articleService.getArticlesByKeyWord(keyword, pageIndex,pageSize);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("articles",articles.getContent());
        map.put("totalElements",articles.getTotalElements());
        map.put("number",articles.getNumber());
        map.put("totalPages",articles.getTotalPages());
        map.put("pageSize",articles.getSize());
        map.put("isLast",articles.isLast());
        map.put("isFirst",articles.isFirst());
        return RestResponse.success(map);
    }

    @DeleteMapping("/article/{articleId}")
    public RestResponse<Article> deleteArticle(@PathVariable(value = "articleId") Long articleId) {
        articleService.removeArticle(articleId);
        return RestResponse.success();
    }

}
