package com.iss.platform.access.role.controller;

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
import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.access.role.entity.Role;
import com.iss.platform.access.role.service.RoleService;

/**
 * Created by Curtain on 2015/9/21.
 */
@Controller
public class RoleController {
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;

	@ResponseBody
	@AccessAuthority(alias = "role-create", name = "新增角色")
	@RequestMapping(value = "/platform/access/role/create.json", method = RequestMethod.GET)
	public MessageObject<Role> roleCreate() {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			List<Role> list = roleService.findAll();
			messageObject.ok("查询新增角色信息", list);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("进入新增菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-save", name = "保存角色")
	@OperateLog(message = "新增角色信息", method = "save", optType = DataType.OptType.INSERT, service = RoleService.class)
	@RequestMapping(value = "/platform/access/role/save.json", method = RequestMethod.POST)
	public MessageObject<Role> roleSave(Role role) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = role.getId();
			role.setStatus(IsDelete.NO);
			roleService.saveEntity(role);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增角色成功");
			} else messageObject.openTip("修改角色成功");
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-edit", name = "获取角色信息")
	@OperateLog(message = "获取角色信息", method = "edit", optType = DataType.OptType.UPDATE, service = RoleService.class)
	@RequestMapping(value = "/platform/access/role/roleEdit.json", method = RequestMethod.POST)
	public MessageObject<Role> roleEdit(String id) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取角色成功", roleService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("获取角色异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-list", name = "角色列表")
	@RequestMapping(value = "/platform/access/role/list.json", method = { RequestMethod.POST })
	public MessageObject<Role> roleList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Role> pagerInfo = roleService.queryPageByMap(map, support);
			messageObject.ok("查询角色成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询角色异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-delete", name = "删除角色")
	@OperateLog(message = "删除角色信息", method = "delete", optType = DataType.OptType.DELETE, service = RoleService.class)
	@RequestMapping(value = "/platform/access/role/roleDetete.json", method = { RequestMethod.POST })
	public MessageObject<Role> roleDetete(String id) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Role> roles = Lists.newArrayList();
				for (String string : ids) {
					Role role = roleService.get(string);
					role.setStatus(IsDelete.YES);
					roles.add(role);
				}
				roleService.saveBatch(roles);
				messageObject.openTip("删除角色成功");
			} else {
				messageObject.error("删除角色异常");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除角色异常");
		}
		return messageObject;
	}
}
