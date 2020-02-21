package com.swb.springcloud.userservice.service;

import com.swb.springcloud.userservice.exception.ApplyException;
import com.swb.springcloud.userservice.pojo.Apply;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.repository.ApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyServiceImpl implements ApplyService{

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Page<Apply> getAllApply(int pageIndex) {
        Pageable pageable= PageRequest.of(pageIndex,10);
        return applyRepository.findAllByHandleIs(false,pageable);
    }

    @Override
    public Apply save(User user) {
        Pageable pageable=PageRequest.of(0,1);
        List<Apply> applyList=applyRepository.findApplyByHandleIsAndUserOrderByIdDesc(false,user,pageable);
        if(applyList.size()==1){
            throw new ApplyException(ApplyException.Type.REPEAT_APPLY,"重复申请");
        }
        return applyRepository.save(new Apply(user));
    }

    @Override
    public Apply findApplyById(Long id) {
        Apply apply=applyRepository.findById(id).orElse(null);
        if(apply==null){
            throw new ApplyException(ApplyException.Type.APPLY_NOT_HAVE,"不存在的申请");
        }
        return apply;
    }

    @Override
    public Apply findApplyByUser(User user) {
        Pageable pageable=PageRequest.of(0,1);
        List<Apply> applyList1=applyRepository.findApplyByHandleIsAndUserOrderByIdDesc(false,user,pageable);
        List<Apply> applyList2=applyRepository.findApplyByHandleIsAndUserOrderByIdDesc(true,user,pageable);
        if(applyList2.size()==0 ){
            if(applyList1.size()==0){
                throw new ApplyException(ApplyException.Type.APPLY_USER_NOT_HAVE,"没有申请");
            }
            throw new ApplyException(ApplyException.Type.APPLY_Unprocessed,"申请未处理");
        }
        return applyList2.get(0);
    }

    @Override
    public Apply updateApply(Apply apply,boolean result) {
        if(result){
            User user=apply.getUser();
            user.setLv(2);
            apply.setUser(user);
        }
        apply.setHandle(true);
        apply.setResult(result);
        return applyRepository.save(apply);
    }
}
