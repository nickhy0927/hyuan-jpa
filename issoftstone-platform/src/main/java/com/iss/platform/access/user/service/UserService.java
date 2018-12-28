package com.iss.platform.access.user.service;

import com.iss.orm.service.CustomService;
import com.iss.platform.access.user.entity.User;

public interface UserService extends CustomService<User, String> {

	public User findUserByLoginName(String loginName);

	public User findUserByLoginNameAndPassword(String loginName, String password);

	public User isRoot(String loginName);
	
	void deleteByLoginName(String loginName);
}
