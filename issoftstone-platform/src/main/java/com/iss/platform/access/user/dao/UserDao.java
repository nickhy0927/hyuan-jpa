package com.iss.platform.access.user.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.user.entity.User;

public interface UserDao extends CustomRepostiory<User, String> {

	@Query("select u from User u where u.loginName = ?1")
	User findUserByLoginName(String loginName);
	
	@Modifying
	@Query("delete from User u where u.loginName = ?1")
	void deleteByLoginName(String loginName);

}
