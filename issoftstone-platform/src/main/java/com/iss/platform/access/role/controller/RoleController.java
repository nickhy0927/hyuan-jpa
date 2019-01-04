package com.iss.platform.access.role.controller;

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

import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.AccessConstant.Locked;
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
	@RequestMapping(value = "/platform/access/role/save.json", method = RequestMethod.POST)
	public MessageObject<Role> roleSave(@RequestBody Role role) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(role.getParentId())) {
				role.setRole(roleService.get(role.getParentId()));
			}
			roleService.save(role);
			messageObject.ok("新增角色成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(value = "/platform/access/role/edit.json", method = RequestMethod.POST)
	public MessageObject<Role> roleEdit(@RequestBody String id) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("修改查询角色成功", roleService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询角色异常");
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(value = "/platform/access/role/list.json", method = { RequestMethod.POST })
	public MessageObject<Role> roleList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			map.put("locked_eq", Locked.NO);
			PagerInfo<Role> tools = roleService.queryPageByMap(map, support);
			messageObject.ok("查询菜单成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询菜单异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(value = "/platform/access/role/delete.json", method = { RequestMethod.POST })
	public MessageObject<Role> roleDelete(@RequestBody String[] ids) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			if (ids.length > 0) {
				roleService.deleteBatch(ids);
				messageObject.ok("删除角色成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除角色异常");
		}
		return messageObject;
	}
}
