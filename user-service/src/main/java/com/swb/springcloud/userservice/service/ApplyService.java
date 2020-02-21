package com.swb.springcloud.userservice.service;

import com.swb.springcloud.userservice.pojo.Apply;
import com.swb.springcloud.userservice.pojo.User;
import org.springframework.data.domain.Page;

public interface ApplyService {

    Page<Apply> getAllApply(int pageIndex);

    Apply save(User user);

    Apply findApplyById(Long id);

    Apply findApplyByUser(User user);

    Apply updateApply(Apply apply,boolean result);
}
