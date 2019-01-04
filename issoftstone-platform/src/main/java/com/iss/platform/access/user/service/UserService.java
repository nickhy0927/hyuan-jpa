package com.iss.platform.access.user.service;

import com.iss.orm.service.CustomService;
import com.iss.platform.access.user.entity.User;

/**
 * @author Administrator
 */
public interface UserService extends CustomService<User, String> {

	/**
	 * 根据用户账号查询用户信息
	 * @param loginName
	 * @return
	 */
	User findUserByLoginName(String loginName);

	/**
	 * 根据用户名和密码查询用户信息
	 * @param loginName
	 * @param password
	 * @return
	 */
	User findUserByLoginNameAndPassword(String loginName, String password);

	/**
	 * 判断是否是超级管理员
	 * @param loginName
	 * @return
	 */
	User isRoot(String loginName);

	/**
	 * 删除用户信息
	 * @param loginName
	 */
	void deleteByLoginName(String loginName);
}
