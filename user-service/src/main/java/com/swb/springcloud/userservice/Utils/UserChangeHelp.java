package com.swb.springcloud.userservice.Utils;

import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.pojo.UserReturn;

import java.util.ArrayList;
import java.util.List;

public class UserChangeHelp {

    public static UserReturn userChange(User user){
        UserReturn userReturn = new UserReturn(user.getName(), user.getAvatar(), user.getLv(), user.getToken(), user.getIntroduction(),
                user.getEmail(), user.getUsername(), user.getSharesNumber());
        userReturn.setId(user.getId());
        return userReturn;
    }

    public static List<UserReturn> userChange(List<User> users){
        List<UserReturn> userReturns=new ArrayList<>();
        for(User user: users){
            UserReturn userReturn = new UserReturn(user.getName(), user.getAvatar(), user.getLv(), user.getToken(), user.getIntroduction(),
                    user.getEmail(), user.getUsername(), user.getSharesNumber());
            userReturn.setId(user.getId());
            userReturns.add(userReturn);
        }
        return userReturns;
    }
}
