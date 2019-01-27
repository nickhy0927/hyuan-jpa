package com.iss.platform.access.user.dao;

import java.util.Set;

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
	@Query("select u from User u where u.loginName = ?1 and u.status = 0")
	User findUserByLoginName(String loginName);
	
	/**
	 * 查询所有的菜单别名
	 * @param userId
	 * @return
	 */
	@Query("select m.alias from User u join u.roles r join r.menus m where u.id = ?1 and u.status = 0")
	Set<String> queryMenuAlias(String id);

	@Query("select m.alias from User u join u.roles r join r.menus m where u.status = 0")
	Set<String> queryAllMenuAlias();

    /**
     * 根据用户账号删除用户
     * @param loginName
     */
	@Modifying
	@Query("delete from User u where u.loginName = ?1")
	void deleteByLoginName(String loginName);

}
