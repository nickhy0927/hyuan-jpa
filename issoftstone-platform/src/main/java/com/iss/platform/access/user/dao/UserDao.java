package com.iss.platform.access.user.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.user.entity.User;

/**
 * @author Administrator
 */
public interface UserDao extends CustomRepostiory<User, String> {

	/**
	 * 根据用户账号查询用户信息
	 * @param loginName
	 * @return
	 */
	@Query("select u from User u where u.loginName = ?1")
	User findUserByLoginName(String loginName);

    /**
     * 根据用户账号删除用户
     * @param loginName
     */
	@Modifying
	@Query("delete from User u where u.loginName = ?1")
	void deleteByLoginName(String loginName);

}
