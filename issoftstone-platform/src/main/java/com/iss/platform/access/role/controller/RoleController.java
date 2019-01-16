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
	@AccessAuthority(alias = "role-create-json", name = "新增角色")
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
	@AccessAuthority(alias = "role-save-json", name = "保存角色")
	@OperateLog(message = "新增角色信息", method = "save", optType = DataType.OptType.INSERT, service = RoleService.class)
	@RequestMapping(value = "/platform/access/role/save.json", method = RequestMethod.POST)
	public MessageObject<Role> roleSave(Role role) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(role.getParentId())) {
				role.setRole(roleService.get(role.getParentId()));
			}
			roleService.saveEntity(role);
			messageObject.ok("新增角色成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-edit-json", name = "修改角色")
	@OperateLog(message = "修改角色信息", method = "edit", optType = DataType.OptType.UPDATE, service = RoleService.class)
	@RequestMapping(value = "/platform/access/role/edit.json", method = RequestMethod.POST)
	public MessageObject<Role> roleEdit(@RequestBody String id) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("修改角色成功", roleService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改角色异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "role-list-json", name = "角色列表")
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
	@AccessAuthority(alias = "role-delete-json", name = "删除角色")
	@OperateLog(message = "删除角色信息", method = "delete", optType = DataType.OptType.DELETE, service = RoleService.class)
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
