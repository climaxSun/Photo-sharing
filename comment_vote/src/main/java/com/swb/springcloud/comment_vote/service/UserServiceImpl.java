package com.swb.springcloud.comment_vote.service;

import com.swb.springcloud.comment_vote.comment.RestResponse;
import com.swb.springcloud.comment_vote.exception.IllegalParamsException;
import com.swb.springcloud.comment_vote.exception.UserException;
import com.swb.springcloud.comment_vote.feign.Client.UserServiceFeignClient;
import com.swb.springcloud.comment_vote.pojo.UserReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    public UserReturn getUserByToen(String token) {
        if (token == null) {
            throw new UserException(UserException.Type.USER_NOT_LOGIN, "token不存在");
        }
        RestResponse<UserReturn> userReturnRestResponse = userServiceFeignClient.getAuth(token);
        if (userReturnRestResponse.getCode() != 0) {
            throw new UserException(UserException.Type.USER_NOT_LOGIN, "token失效");
        }
        return userReturnRestResponse.getResult();
    }

    public List<UserReturn> getUsersByUserIds(List<Long> ids){
        RestResponse<List<UserReturn>> usersRestResponse= userServiceFeignClient.getUsersByIds(ids);
        if(usersRestResponse==null || usersRestResponse.getCode()!=0){
            throw new IllegalParamsException(IllegalParamsException.Type.XITONG_FANMANG, "系统繁忙");
        }
        return usersRestResponse.getResult();
    }
}
