package com.swb.springcloud.ui.feign.Client;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Hystrix.FlowerFeignHystrix;
import com.swb.springcloud.ui.pojo.Flower;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "flower",fallback = FlowerFeignHystrix.class)
public interface FlowerFeignClient {

    @GetMapping("/getFlower")
    RestResponse<Object> getFlowers(@RequestParam("id") Long id, @RequestParam("userId") Long userId,
                                   @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                                   @RequestParam("keyword")String keyword);

    @GetMapping("/getFlowerOne")
    RestResponse<Flower> getFlower(@RequestParam("flowerId") Long flowerId);

    @GetMapping("/article/{articleId}")
    RestResponse<Object> getArticleById(@PathVariable("articleId") Long articleId);

    @GetMapping("/articles")
    RestResponse<Map<String,Object>> getArticleAll(@RequestParam(value = "pageIndex")int pageIndex,
                                                   @RequestParam(value = "pageSize")int pageSize,
                                                   @RequestParam(value = "keyword")String keyword);
}
