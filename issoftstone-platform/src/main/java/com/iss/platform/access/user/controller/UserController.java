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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.encryption.Md5Encryption;
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
	@AccessAuthority(alias = "user-create", name = "新增用户信息")
	@RequestMapping(value = "/platform/access/user/create.do")
	public MessageObject<User> userCreate() {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		messageObject.ok("获取新增用户信息成功");
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "user-create", name = "新增用户信息")
	@OperateLog(message = "新增用户信息", method = "userSave", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/save.json", method = RequestMethod.POST)
	public MessageObject<User> userSave(@RequestBody User user) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			user.setPassword(Md5Encryption.MD5(user.getPassword()));
			user = userService.saveEntity(user);
			messageObject.ok("保存用户成功", user);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "user-edit", name = "修改用户信息")
	@RequestMapping(value = "/platform/access/user/edit.do")
	@OperateLog(message = "修改用户信息", method = "edit", optType = DataType.OptType.UPDATE, service = UserService.class)
	public MessageObject<User> userEdit(@RequestBody String id) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("查询用户成功", userService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询用户异常");
		}
		return messageObject;

	}

	@ResponseBody
	@AccessAuthority(alias = "query-user-role", name = "获取用户角色信息")
	@RequestMapping(value = "/platform/access/user/userroles/queryuserrole.json", method = RequestMethod.GET)
	public MessageObject<User> roleList(String userId, HttpServletRequest request, PageSupport support) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			if (StringUtils.isNotEmpty(userId)) {
				User user = userService.get(userId);
				map.put("user", user);
			}
			PagerInfo<Role> pagerInfo = roleService.queryPageByMap(map, support);
			System.out.println(pagerInfo);
			map.put("pagerInfo", pagerInfo);
			messageObject.ok("查询用户权限成功", map);
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
					message.ok("保存角色信息成功", null);
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
