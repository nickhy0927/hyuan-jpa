package com.iss.platform.access.user.service;

import java.util.List;
import java.util.Set;

import com.iss.common.exception.ServiceException;
import com.iss.orm.service.CustomService;
import com.iss.platform.access.menu.entity.Menu;
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
	
	/**
	 * 查询所有的菜单别名
	 * @param userId
	 * @return
	 */
	Set<String> queryMenuAlias(String id);
	
	/**
	 * 查询所有的菜单
	 * @param id
	 * @return
	 */
	List<Menu> queryMenuList(String id);
	
	/**
	 * 查询所有的菜单
	 * @return
	 */
	Set<String> queryAllMenuAlias();
	
	User updateUser(User entity) throws ServiceException;
}
