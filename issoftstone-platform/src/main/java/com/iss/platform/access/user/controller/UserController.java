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
import com.google.common.collect.Maps;
import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.access.role.entity.Role;
import com.iss.platform.access.role.service.RoleService;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

@Controller
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@ResponseBody
	@AccessAuthority(alias = "user-save", name = "新增用户信息")
	@OperateLog(message = "新增用户信息", method = "userSave", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/save.json", method = RequestMethod.POST)
	public MessageObject<User> userSave(User user) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			user.setStatus(Boolean.TRUE);
			userService.saveEntity(user);
			messageObject.openTip("保存用户成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@AccessAuthority(alias = "user-delete", name = "删除用户信息")
	@OperateLog(message = "新增用户信息", method = "userSave", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/delete.json", method = RequestMethod.POST)
	public MessageObject<User> userDelete(String id) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			List<User> users = Lists.newArrayList();
			String[] ids = id.split(",");
			for (String str : ids) {
				User user = userService.get(str);
				user.setStatus(Boolean.FALSE);
				users.add(user);
			}
			userService.saveBatch(users);
			messageObject.openTip("刪除用户成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "user-edit", name = "修改用户")
	@OperateLog(message = "修改用户信息", method = "edit", optType = DataType.OptType.UPDATE, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/edit.json", method = RequestMethod.POST)
	public MessageObject<User> userEdit(String id) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> params = Maps.newConcurrentMap();
			User user = userService.get(id);
			params.put("user", user);
			messageObject.ok("修改查询用户成功", params);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询用户异常");
		}
		return messageObject;
	}
	@ResponseBody
	@AccessAuthority(alias = "query-user-role", name = "获取用户角色信息")
	@RequestMapping(value = "/platform/access/user/userroles/queryuserrole.json", method = RequestMethod.GET)
	public MessageObject<Role> roleList(String userId, HttpServletRequest request, PageSupport support) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			map.put("user.id_eq", userId);
			PagerInfo<Role> pagerInfo = roleService.queryPageByMap(map, support);
			messageObject.ok("查询用户权限成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询用户权限失败");
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
			List<Role> roles = new ArrayList<Role>();
			if (StringUtils.isNotEmpty(roleIds)) {
				String[] ids = roleIds.split(",");
				for (int i = 0; i < ids.length; i++) {
					Role role = roleService.get(ids[i]);
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
