package com.swb.springcloud.flower.feign.Hystrix;

import com.swb.springcloud.flower.common.RestCode;
import com.swb.springcloud.flower.common.RestResponse;
import com.swb.springcloud.flower.pojo.User;
import com.swb.springcloud.flower.pojo.UserReturn;
import org.springframework.stereotype.Component;
import com.swb.springcloud.flower.feign.Client.UserServiceFeignClient;
@Component
public class UserServiceFeignHystrix implements UserServiceFeignClient {

    @Override
    public RestResponse<UserReturn> getAuth(String token) {
        return new RestResponse(RestCode.TOKEN_INVALID.code,RestCode.TOKEN_INVALID.msg);
    }

    @Override
    public RestResponse<User> getAuths(String token) {
        return new RestResponse(RestCode.TOKEN_INVALID.code,RestCode.TOKEN_INVALID.msg);
    }

    @Override
    public RestResponse<UserReturn> getUser(String username) {
        return null;
    }

}
