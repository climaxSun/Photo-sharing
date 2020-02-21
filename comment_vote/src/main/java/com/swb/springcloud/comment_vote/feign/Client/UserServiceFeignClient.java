package com.swb.springcloud.comment_vote.feign.Client;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.feign.Hystrix.UserServiceFeignHystrix;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user-service",fallback = UserServiceFeignHystrix.class)
public interface UserServiceFeignClient {

    @GetMapping("/getAuth")
    RestResponse<UserReturn> getAuth(@RequestParam(value = "token") String token);

    @GetMapping("/getUserByUsername")
    RestResponse<UserReturn> getUser(@RequestParam(value = "username") String username);

    @PostMapping("/getUsersByIds")
    RestResponse<List<UserReturn>> getUsersByIds(@RequestBody List<Long> ids);
}
