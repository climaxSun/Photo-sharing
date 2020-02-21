package com.swb.springcloud.userservice.service;

import java.util.Collection;
import java.util.List;

import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.pojo.UserReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	User saveUser(User user);
	
	/**
	 * 保存用户
	 * @param user
	 * @return
	 */
	User registerUser(User user);
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	void removeUser(Long id);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	User updateUser(User user);
	
	/**
	 * 根据id获取用户
	 * @param user
	 * @return
	 */
	User getUserById(Long id);
	
	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	List<User> listUsers();
	
	/**
	 * 根据用户名进行分页模糊查询
	 * @param user
	 * @return
	 */
	Page<User> listUsersByNameLike(String name, Pageable pageable);

    List<User> listUsersByUsernames(Collection<String> usernames);

    User login(User user);

    User getLoginedUserByToken(String token);

    void invalidate(String token);

    User emailHave(String email);

	User findByUsername(String username);

    List<UserReturn> findByIdIn(List<Long> id);

	User getUserByUsername(String username);

	void deleteUser(Long  userId);

	Page<User> findUserByName(int pageIndex,String name);
}
