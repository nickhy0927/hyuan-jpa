package com.iss.platform.access.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.common.config.InitEnvironment;
import com.iss.common.encryption.MD5Encryption;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.SysContants;
import com.iss.orm.service.impl.DefaultCustomService;
import com.iss.platform.access.user.dao.UserDao;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

@Service
public class UserServiceImpl extends DefaultCustomService<User, String> implements UserService {

	@Autowired
	private UserDao userDao;

	public User findUserByLoginName(String loginName) {
		User root = isRoot(loginName);
		if (root == null) {
			root = userDao.findUserByLoginName(loginName);
		} 
		return root;
	}

	/**
	 * 根据用户名和密码登录
	 */
	public User findUserByLoginNameAndPassword(String loginName, String password) {
		User root = isRoot(loginName);
		if (root != null) {
			return root;
		} else {
			root = findUserByLoginName(loginName);
			if (root != null) {
				if (root.getPassword().equals(MD5Encryption.MD5(password))) {
					return root;
				}
			}
		}
		return null;
	}

	public User get(String id) throws ServiceException {
		return userDao.findOne(id);
	}

	/**
	 * 判断是否为管理员
	 */
	public User isRoot(String loginName) {
		if (StringUtils.equals(InitEnvironment.getInitUsername(), loginName)) {
			User user = new User();
			user.setNickName("超级系统管理员");
			user.setLoginName(loginName);
			user.setPassword("3dbb8ead479a355565ce70e0e1fecb35");
			user.setBrithday("2009-11-21");
			user.setSalt("ef44af4b79d04bd6b329e1879c5d9791");
			user.setEmail("h_y_12@163.com");
			user.setEnable(true);
			user.setLocked(false);
			user.setStatus(SysContants.IsDelete._NO);
			return user;
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void deleteByLoginName(String loginName) {
		userDao.deleteByLoginName(loginName);
	}

}
