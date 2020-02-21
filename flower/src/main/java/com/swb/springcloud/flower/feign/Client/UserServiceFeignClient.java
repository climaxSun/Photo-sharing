package com.swb.springcloud.flower.feign.Client;

import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.feign.Hystrix.UserServiceFeignHystrix;
import com.swb.springcloud.flower.pojo.User;
import com.swb.springcloud.flower.pojo.UserReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",fallback = UserServiceFeignHystrix.class)
public interface UserServiceFeignClient {

    @GetMapping("/getAuth")
    RestResponse<UserReturn> getAuth(@RequestParam(value = "token") String token);


    @GetMapping("/getAuthUser")
    RestResponse<User> getAuths(@RequestParam(value = "token") String token);

    @GetMapping("/getUserByUsername")
    RestResponse<UserReturn> getUser(@RequestParam(value = "username") String username);
}
