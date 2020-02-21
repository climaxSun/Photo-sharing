package com.swb.springcloud.userservice.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.ImmutableMap;
import com.swb.springcloud.userservice.Utils.UserChangeHelp;
import com.swb.springcloud.userservice.exception.EmailException;
import com.swb.springcloud.userservice.pojo.User;
import com.swb.springcloud.userservice.Utils.JwtHelper;
import com.swb.springcloud.userservice.pojo.UserReturn;
import com.swb.springcloud.userservice.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.swb.springcloud.userservice.exception.UserException;
import com.swb.springcloud.userservice.exception.UserException.Type;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository ur;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	@Transactional
	public User registerUser(User user) {
		String email=user.getEmail();
		String redisTZM=null;
		if(redisTemplate.getExpire("email"+email)>0){
			redisTZM=redisTemplate.opsForValue().get("email"+user.getEmail());
		}else {
			throw  new EmailException(EmailException.Type.YZM_INVALID,email+"的验证码失效");
		}
		if(!redisTZM.equals(user.getYzm())){
			throw  new EmailException(EmailException.Type.YZM_ERROR,email+"的验证码错误");
		}
		if(ur.findByUsername(user.getUsername())!=null){
			throw new UserException(Type.USER_HAVE,"登录名"+user.getUsername()+"已存在");
		}if(ur.findByEmail(user.getEmail())!=null){
			throw new UserException(Type.USER_EMAIL_HAVE,"邮箱"+user.getEmail()+"已经注册");
		}
		redisTemplate.delete("email"+email);
		return ur.save(user);
	}

	@Override
	public User saveUser(User user) {
		return ur.save(user);
	}

	@Override
	public void removeUser(Long id) {
		ur.deleteById(id);
	}

	@Override
	public User updateUser(User user) {
		return null;
	}

	@Override
	public User getUserById(Long id) {
		User user=ur.findById(id).orElse(null);
		if(user==null)
			throw new UserException(Type.USER_NOT_FOUND,"user not found for "+id);
		return user;
	}

	@Override
	public List<User> listUsers() {
		return ur.findAll();
	}

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
		name="%"+name+"%";
		return ur.findByNameLike(name, pageable);
	}

	@Override
	public List<User> listUsersByUsernames(Collection<String> usernames) {
		return ur.findByUsernameIn(usernames);
	}

	@Override
	public User login(User user) {
		if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
			throw new UserException(Type.USER_USERNAMEORPASSWORD_NULL,"登录名或密码为空");
		}
		User findUser=ur.findByUsername(user.getUsername());
		if(findUser==null){
			throw new UserException(Type.USER_USERNAME_NOT_HAVA,"用户不存在，请重新输入");
		}
		if(findUser.getPassword().equals(user.getPassword())){
			onLongin(findUser);
			return findUser;
		}else {
			throw new UserException(Type.USER_PASSWORD_ERROR,"密码错误,请重新输入");
		}
	}

	private void onLongin(User user) {
		String token =  JwtHelper.genToken(ImmutableMap.of("id", user.getId().toString(), "name", user.getName(),"ts", Instant.now().getEpochSecond()+""));
		renewToken(token,user.getId());
		user.setToken(token);
	}

	private String renewToken(String token, long id) {
		redisTemplate.opsForValue().set("token"+id, token);
		redisTemplate.expire("token"+id, 30, TimeUnit.MINUTES);
		return token;
	}

	@Override
	public User getLoginedUserByToken(String token) {
		Map<String, String> map = null;
		try {
			map = JwtHelper.verifyToken(token);
		} catch (Exception e) {
			throw new UserException(Type.USER_NOT_LOGIN,"User not login");
		}
		int id= Integer.valueOf(map.get("id"));
		String tokenid =  "token"+map.get("id");
		Long expired = redisTemplate.getExpire(tokenid);
		if (expired > 0L) {
			renewToken(token, id);
			User user = getUserById((long) id);
			user.setToken(token);
			return user;
		}
		throw new UserException(Type.USER_NOT_LOGIN,"user not login");
	}

	@Override
	public void invalidate( String token) {
		Map<String, String> map = JwtHelper.verifyToken(token);
		redisTemplate.delete("token"+map.get("id"));
	}

	@Override
	public User emailHave(String email) {
		return ur.findByEmail(email);
	}

	@Override
	public User findByUsername(String username) {
		return ur.findByUsername(username);
	}

	@Override
	public List<UserReturn> findByIdIn(List<Long> id) {
		return UserChangeHelp.userChange(ur.findByIdIn(id));
	}

	@Override
	public User getUserByUsername(String username) {
		return ur.findByUsername(username);
	}

	@Override
	@Transactional
	public void deleteUser(Long userId) {
		ur.deleteById(userId);
	}

	@Override
	public Page<User> findUserByName(int pageIndex,String name) {
		name="%"+name+"%";
		Pageable pageable= PageRequest.of(pageIndex,5);
		return ur.findAllByNameLikeOrUsernameLikeOrderByIdAsc(name,name,pageable);
	}

}
