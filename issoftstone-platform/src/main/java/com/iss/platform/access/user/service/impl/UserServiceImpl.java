package com.iss.platform.access.user.service.impl;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.aspect.anno.ServiceMonitor;
import com.iss.common.config.InitEnvironment;
import com.iss.common.encryption.Md5Encryption;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.SaltUtils;
import com.iss.common.utils.SysContants;
import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.user.dao.UserDao;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl extends BaseCustomService<User, String> implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private Md5PasswordEncoder encoder;
	
	@Override
	@Transactional(readOnly = false)
	public User saveEntity(User entity) throws ServiceException {
		String salt = UUID.randomUUID().toString().replaceAll("-", "");
		salt = SaltUtils.getSalt(salt);
		entity.setSalt(salt);
		String password = entity.getPassword();
		entity.setPassword(encoder.encodePassword(password, salt));
		return super.saveEntity(entity);
	}

	@Override
	@Transactional(readOnly = false)
	public User updateUser(User entity) throws ServiceException {
		return super.saveEntity(entity);
	}

	@Override
	@ServiceMonitor(desc = "用户登录")
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
	@Override
	public User findUserByLoginNameAndPassword(String loginName, String password) {
		User root = isRoot(loginName);
		if (root != null) {
			return root;
		} else {
			root = findUserByLoginName(loginName);
			if (root != null) {
				if (root.getPassword().equals(Md5Encryption.MD5(password))) {
					return root;
				}
			}
		}
		return null;
	}

	@Override
	public User get(String id) throws ServiceException {
		return userDao.findOne(id);
	}

	/**
	 * 判断是否为管理员
	 */
	@Override
	public User isRoot(String loginName) {
		if (StringUtils.equals(InitEnvironment.getInitUsername(), loginName)) {
			User user = new User();
			user.setNickName("超级系统管理员");
			user.setLoginName(loginName);
			user.setPassword("3dbb8ead479a355565ce70e0e1fecb35");
			user.setBrithday("2009-11-21");
			user.setSalt("ef44af4b79d04bd6b329e1879c5d9791");
			user.setEmail("h_y_12@163.com");
			user.setEnable(String.valueOf(1));
			user.setLocked(String.valueOf(0));
			user.setStatus(SysContants.IsDelete.NO);
			return user;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = ServiceException.class)
	public void deleteByLoginName(String loginName) {
		userDao.deleteByLoginName(loginName);
	}

	@Override
	public Set<String> queryMenuAlias(String id) {
		return userDao.queryMenuAlias(id);
	}
	
	@Override
	public List<Menu> queryMenuList(String username) {
		return userDao.queryMenuList(username);
	}

	@Override
	public Set<String> queryAllMenuAlias() {
		return userDao.queryAllMenuAlias();
	}
}
