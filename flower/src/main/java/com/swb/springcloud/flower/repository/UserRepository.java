package com.swb.springcloud.flower.repository;

import com.swb.springcloud.flower.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{

	Page<User> findByNameLike(String name, Pageable pageable);
	
	User findByUsername(String username);

	User findByEmail(String email);

    List<User> findByUsernameIn(Collection<String> usernames);

	@Modifying
	@Query(value = "update user set shares_number=shares_number+1 where id=?1",nativeQuery = true)
	int updateSharesNumber(Long id);
}
