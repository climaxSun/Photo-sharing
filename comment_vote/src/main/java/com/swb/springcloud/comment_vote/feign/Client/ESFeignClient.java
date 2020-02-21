package com.swb.springcloud.comment_vote.feign.Client;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.feign.Hystrix.ESFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "es",fallback = ESFeignHystrix.class)
public interface ESFeignClient {

    @PostMapping("/deleteFlower")
    RestResponse<Object> deleteEsFlowerByIds(@RequestBody List<Long> flowerIds);

    @DeleteMapping("/flower/{flowerId}")
    RestResponse<Object> deleteEsFlowerById(@PathVariable(value = "flowerId") Long flowerId);

    @DeleteMapping("/flowerByUser")
    RestResponse<Object> deleteEsFlowerByUserId(@RequestParam(value = "userId") Long userId);

    @GetMapping("/comment")
    RestResponse<Object> editFlowerCommentSize(@RequestParam(value = "type") String type, @RequestParam(value = "flowerId") Long flowerId);

    @GetMapping("/vote")
    RestResponse<Object> editFlowerVoteSize(@RequestParam(value = "type") String type, @RequestParam(value = "flowerId") Long flowerId);
}
