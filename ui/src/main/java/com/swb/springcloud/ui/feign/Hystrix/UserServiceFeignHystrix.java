package com.swb.springcloud.ui.feign.Hystrix;

import com.swb.springcloud.ui.common.RestCode;
import com.swb.springcloud.ui.common.RestResponse;
import com.swb.springcloud.ui.exception.IllegalParamsException;
import com.swb.springcloud.ui.exception.UserException;
import com.swb.springcloud.ui.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.ui.pojo.UserReturn;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


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
    public RestResponse<Map<String,Object>> getAllUserByName(String name, int pageIndex) {
       throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG,"UserService服务调用异常");
    }

    @Override
    public RestResponse<UserReturn> getUserById(Long id) {
        throw new UserException(UserException.Type.USER_NOT_FOUND,"UserServiceFeign异常");
    }

    @Override
    public RestResponse<Map<String, Object>> getApplyAll(int pageIndex) {
        throw new UserException(UserException.Type.USER_NOT_FOUND,"UserServiceFeign异常");
    }

    @Override
    public RestResponse<List<UserReturn>> getUserByIds(List<Long> id) {
        throw new UserException(UserException.Type.USER_NOT_FOUND,"UserServiceFeign异常");
    }

}
