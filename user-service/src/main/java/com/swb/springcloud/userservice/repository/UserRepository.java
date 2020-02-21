package com.swb.springcloud.userservice.repository;

import com.swb.springcloud.userservice.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

	Page<User> findByNameLike(String name, Pageable pageable);
	
	User findByUsername(String username);

	User findByEmail(String email);

    List<User> findByUsernameIn(Collection<String> usernames);

	List<User> findByIdIn(Collection<Long> id);

	Page<User> findAllByNameLikeOrUsernameLikeOrderByIdAsc(String name,String username,Pageable pageable);
}
