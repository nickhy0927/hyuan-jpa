package com.iss.platform.access.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.AccessConstant;
import com.iss.constant.DataType;
import com.iss.platform.access.role.entity.Role;
import com.iss.platform.access.role.service.RoleService;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	private final RoleService roleService;

	private final UserService userService;

	@Autowired
	public UserController(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}

	@ResponseBody
	@AccessAuthority(alias = "user-save|user-edit", name = "新增用户信息")
	@OperateLog(message = "新增用户信息", method = "userSave", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userSave.json", method = RequestMethod.POST)
	public MessageObject<User> userSave(User user) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = user.getId();
			user.setStatus(IsDelete.NO);
			if (StringUtils.isEmpty(id)) {
				userService.saveEntity(user);
				messageObject.openTip("保存用户成功");
			} else {
				userService.updateUser(user);
				messageObject.openTip("修改用户成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@AccessAuthority(alias = "user-save|user-edit", name = "更新用户状态信息")
	@OperateLog(message = "更新用户状态信息", method = "userStatusEdit", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userStatusEdit.json", method = RequestMethod.POST)
	public MessageObject<User> userStatusEdit(User user) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			userService.updateUser(user);
			if (StringUtils.isNoneEmpty(user.getEnable())) {
				messageObject.openTip("用户" + AccessConstant.Enable.getName(user.getEnable()) + "成功");
			} else {
				messageObject.openTip("用户" + AccessConstant.Locked.getName(user.getLocked()) + "成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			if (StringUtils.isNoneEmpty(user.getEnable())) {
				messageObject.openTip("用户" + AccessConstant.Enable.getName(user.getEnable()) + "失败");
			} else {
				messageObject.openTip("用户" + AccessConstant.Locked.getName(user.getLocked()) + "失败");
			}
		}
		return messageObject;
	}
	
	
	@ResponseBody
	@AccessAuthority(alias = "user-role-list", name = "新增用户权限信息")
	@OperateLog(message = "新增用户权限信息", method = "userRoleSave", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userRoleSave.json", method = RequestMethod.POST)
	public MessageObject<User> userRoleSave(String userId, String roleIds) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			User user = userService.get(userId);
			String[] split = roleIds.split(",");
			List<Role> roles = Lists.newArrayList();
			for (String string : split) {
				roles.add(roleService.get(string));
			}
			user.setRoles(roles);
			userService.updateUser(user);
			messageObject.openTip("保存用户权限成功");
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户权限异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@AccessAuthority(alias = "user-delete", name = "删除用户信息")
	@OperateLog(message = "删除用户信息", method = "userDelete", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userDelete.json", method = RequestMethod.POST)
	public MessageObject<User> userDelete(String id) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			List<User> users = Lists.newArrayList();
			String[] ids = id.split(",");
			for (String str : ids) {
				User user = userService.get(str);
				user.setStatus(IsDelete.YES);
				users.add(user);
			}
			userService.saveBatch(users);
			messageObject.openTip("刪除用户成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("刪除用户异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "queryUserInfo", name = "获取用户信息")
	@OperateLog(message = "获取用户信息", method = "edit", optType = DataType.OptType.UPDATE, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/queryUserInfo.json", method = RequestMethod.POST)
	public MessageObject<User> queryUserInfo(String id) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			User user = userService.get(id);
			user.setPassword(null);
			messageObject.ok("修改查询用户成功", user);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询用户异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@AccessAuthority(alias = "user-list", name = "获取用户信息列表")
	@RequestMapping(value = "/platform/access/user/list.json", method = RequestMethod.POST)
	public MessageObject<User> userList(HttpServletRequest request, PageSupport support) {
		logger.debug("查询用户信息");
		MessageObject<User> message = MessageObject.getDefaultInstance();
		Map<String, Object> paramMap = WebUtils.getRequestToMap(request);
		try {
			paramMap.put("status_eq", IsDelete.NO);
			PagerInfo<User> users = userService.queryPageByMap(paramMap, support);
			message.ok("查询用户成功", users);
		} catch (ServiceException e) {
			e.printStackTrace();
			message.error("查询用户异常");
		}
		return message;
	}

	@ResponseBody
	@AccessAuthority(alias = "user-role-save", name = "新增用户权限信息")
	@RequestMapping(value = "/platform/access/user/role.json", method = RequestMethod.POST)
	@OperateLog(message = "用户添加权限信息", method = "saveRole", optType = DataType.OptType.UPDATE, service = UserService.class)
	public MessageObject<User> addRole(String id, HttpServletRequest request) {
		MessageObject<User> message = MessageObject.getDefaultInstance();
		try {
			String roleIds = request.getParameter("roleIds");
			List<Role> roles = new ArrayList<>();
			if (StringUtils.isNotEmpty(roleIds)) {
				String[] ids = roleIds.split(",");
				for (String id1 : ids) {
					Role role = roleService.get(id1);
					if (role != null) {
						roles.add(role);
					}
				}
			}
			if (StringUtils.isNotEmpty(id)) {
				User user = userService.get(id);
				if (user != null) {
					user.setRoles(roles);
					userService.saveEntity(user);
					message.openTip("保存角色信息成功", null);
				} else {
					message.error("保存角色信息失败");
				}
			} else {
				message.error("保存角色信息失败");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			message.error("保存角色信息失败");
		}
		return message;
	}
}
