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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.JsonMapper;
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
	@RequestMapping(name = "添加权限", value = "/platform/access/user/role.json", method = RequestMethod.POST)
	@OperateLog(message = "添加权限", optType = DataType.OptType.INSERT, service = UserService.class)
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
					message.openTip("添加角色成功", null);
				} else {
					message.error("添加角色失败");
				}
			} else {
				message.error("添加角色失败");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			message.error("保存角色失败");
		}
		return message;
	}

	@RequestMapping(name = "用户新增页面", value = "/platform/access/user/userCreate.do")
	public String userCreate() {
		return "platform/access/user/userCreate";
	}

	@ResponseBody
	@OperateLog(message = "保存用户", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(name = "保存用户", value = "/platform/access/user/userCreateSave.json", method = RequestMethod.POST)
	public MessageObject<User> userCreateSave(User user) {
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
	@OperateLog(message = "删除用户", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(name = "删除用户", value = "/platform/access/user/userDelete.json", method = RequestMethod.POST)
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

	@RequestMapping(name = "用户修改页面", value = "/platform/access/user/userEdit.do")
	public String userEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/user/userEdit";
	}

	@ResponseBody
	@OperateLog(message = "获取用户", optType = DataType.OptType.UPDATE, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userEditJson.json", method = RequestMethod.POST)
	public MessageObject<User> userEditJson(String id) {
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
	@OperateLog(message = "保存用户", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(name = "保存用户", value = "/platform/access/user/userEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<User> userEditUpdate(User user) {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			user.setStatus(IsDelete.NO);
			userService.saveEntity(user);
			messageObject.openTip("保存用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("保存用户异常");
		}
		return messageObject;
	}

	@RequestMapping(name = "用户列表页面", value = "/platform/access/user/userList.do", method = RequestMethod.GET)
	public String userList() {
		return "platform/access/user/userList";
	}

	@ResponseBody
	@RequestMapping(name = "用户列表分页", value = "/platform/access/user/userList.json", method = RequestMethod.POST)
	public MessageObject<User> userList(HttpServletRequest request, PageSupport support) {
		logger.debug("查询用户");
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

	@RequestMapping(name = "获取用户权限信息", value = "/platform/access/user/userRoleList.do", method = RequestMethod.GET)
	public String userRoleList(String userId, Model model) {
		model.addAttribute("userId", userId);
		User user = userService.get(userId);
		if (user != null)
			model.addAttribute("roles", new JsonMapper().toJson(user.getRoles()));
		return "platform/access/user/roleList";
	}

	@ResponseBody
	@OperateLog(message = "保存用户权限", optType = DataType.OptType.INSERT, service = UserService.class)
	@RequestMapping(name = "保存用户权限", value = "/platform/access/user/userRoleSave.json", method = RequestMethod.POST)
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
	@OperateLog(message = "更新用户状态", optType = DataType.OptType.UPDATE, service = UserService.class)
	@RequestMapping(value = "/platform/access/user/userStatusUpdate.json", method = RequestMethod.POST)
	public MessageObject<User> userStatusUpdate(User user) {
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

}
