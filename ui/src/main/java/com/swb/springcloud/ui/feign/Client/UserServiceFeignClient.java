package com.swb.springcloud.ui.feign.Client;

import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.feign.Hystrix.UserServiceFeignHystrix;
import com.swb.springcloud.ui.pojo.UserReturn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service",fallback = UserServiceFeignHystrix.class)
public interface UserServiceFeignClient {

    @GetMapping("/getAuth")
    RestResponse<UserReturn> getAuth(@RequestParam(value ="token" ) String token);

    @GetMapping("/getUserByUsername")
    RestResponse<UserReturn> getUser(@RequestParam(value ="username" ) String username);

    @GetMapping("/getAll")
    RestResponse<Map<String,Object>> getAllUserByName(@RequestParam(value ="name" ) String name, @RequestParam(value ="pageIndex" ) int pageIndex);

    @GetMapping("/getById")
    RestResponse<UserReturn> getUserById(@RequestParam(value ="id" ) Long id);

    @GetMapping("/apply/all")
    RestResponse<Map<String,Object>> getApplyAll(@RequestParam(value = "pageIndex") int pageIndex);

    @GetMapping("/getUsersByIds")
    RestResponse<List<UserReturn>> getUserByIds(@RequestBody List<Long> id);

}
