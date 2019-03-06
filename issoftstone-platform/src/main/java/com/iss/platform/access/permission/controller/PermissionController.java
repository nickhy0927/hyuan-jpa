package com.iss.platform.access.permission.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iss.common.exception.ServiceException;
import com.iss.common.utils.JsonMapper;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.platform.access.permission.entity.Permission;
import com.iss.platform.access.permission.service.PermissionService;

@Controller
@RequestMapping(value = "/platform/access/permission", name = "权限管理")
public class PermissionController {
	private static Logger logger = LoggerFactory.getLogger(PermissionController.class);
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping(value = "/permissionCreate.do", method = RequestMethod.GET)
	public String permissionCreate() {
		return "platform/access/permission/permissionCreate";
	}

	@RequestMapping(value = "/permissionEdit.do", method = RequestMethod.GET)
	public String permissionEdit() {
		return "platform/access/permission/permissionEdit";
	}

	@RequestMapping(value = "/permissionList.do", method = RequestMethod.GET)
	public String permissionList() {
		return "platform/access/permission/permissionList";
	}
	
	@ResponseBody
	@RequestMapping(name = "获取角色列表分页", value = "/platform/access/role/roleList.json", method = { RequestMethod.POST })
	public MessageObject<Permission> roleList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Permission> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			List<Permission> pagerInfo = permissionService.queryByMap(map);
			messageObject.ok("查询权限成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询权限异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
	@ResponseBody
	@RequestMapping(name = "获取权限树", value = "/queryPermissionTree.json", method = { RequestMethod.POST })
	public MessageObject<Permission> queryPermissionTree(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Permission> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			List<Permission> permissions = permissionService.queryByMap(map);
			messageObject.ok("查询权限成功", new JsonMapper().toJson(permissions));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询权限异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
