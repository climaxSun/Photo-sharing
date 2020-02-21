package com.swb.springcloud.userservice.repository;

import com.swb.springcloud.userservice.pojo.Apply;
import com.swb.springcloud.userservice.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

	Page<Apply> findAllByHandleIs(boolean handle,Pageable pageable);

	List<Apply> findApplyByHandleIsAndUserOrderByIdDesc(boolean handle, User user, Pageable pageable);

}
