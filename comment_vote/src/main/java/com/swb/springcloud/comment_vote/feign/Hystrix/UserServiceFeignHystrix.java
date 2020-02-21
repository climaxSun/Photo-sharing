package com.swb.springcloud.comment_vote.feign.Hystrix;

import com.swb.springcloud.comment_vote.comment.RestCode;
import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserServiceFeignHystrix implements UserServiceFeignClient {
    @Override
    public RestResponse<UserReturn> getAuth(String token) {
        System.out.println("UserServiceFeignHystrix.getAuth()");
        return new RestResponse(RestCode.TOKEN_INVALID.code,RestCode.TOKEN_INVALID.msg);
    }

    @Override
    public RestResponse<UserReturn> getUser(String username) {
        return null;
    }

    @Override
    public RestResponse<List<UserReturn>> getUsersByIds(List<Long> ids) {
        return null;
    }

}
