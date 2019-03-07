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
import com.iss.constant.PlatformManageMenu;
import com.iss.orm.anno.MenuMonitor;
import com.iss.platform.access.permission.entity.Permission;
import com.iss.platform.access.permission.service.PermissionService;

@Controller
@RequestMapping(name = "权限管理")
public class PermissionController {
	private static Logger logger = LoggerFactory.getLogger(PermissionController.class);
	@Autowired
	private PermissionService permissionService;

	public final static String PERMISSIONMANAGE = "permissionManage";

	@MenuMonitor(name = "权限管理", orders = 3, level = 3, url = "/platform/access/permission/permissionList.do", paraentAlias = PlatformManageMenu.BASE_MANAGE)
	public void permissionManage() {
	}

	@MenuMonitor(name = "权限新增", orders = 1, level = 4, paraentAlias = PERMISSIONMANAGE)
	@RequestMapping(value = "/platform/access/permission/permissionCreate.do", method = RequestMethod.GET)
	public String permissionCreate() {
		return "platform/access/permission/permissionCreate";
	}

	@ResponseBody
	@MenuMonitor(name = "权限删除", orders = 1, level = 4, paraentAlias = PERMISSIONMANAGE)
	@RequestMapping(name = "权限删除", value = "/platform/access/permission/permissionDelete.json", method = {
			RequestMethod.POST })
	public MessageObject<Permission> permissionDelete(HttpServletRequest request, PageSupport support) {
		MessageObject<Permission> messageObject = MessageObject.getDefaultInstance();
		messageObject.ok("查询权限成功");
		return messageObject;
	}

	@MenuMonitor(name = "权限修改", orders = 2, level = 4, paraentAlias = PERMISSIONMANAGE)
	@RequestMapping(value = "/platform/access/permission/permissionEdit.do", method = RequestMethod.GET)
	public String permissionEdit() {
		return "platform/access/permission/permissionEdit";
	}

	@MenuMonitor(name = "权限列表", orders = 4, level = 4, paraentAlias = PERMISSIONMANAGE)
	@RequestMapping(value = "/platform/access/permission/permissionList.do", method = RequestMethod.GET)
	public String permissionList() {
		return "platform/access/permission/permissionList";
	}

	@ResponseBody
	@MenuMonitor(name = "权限列表-获取数据分页", orders = 1, level = 5, paraentAlias = "permissionList")
	@RequestMapping(name = "获取角色列表分页", value = "/platform/access/permission/permissionList.json", method = {
			RequestMethod.POST })
	public MessageObject<Permission> permissionPage(HttpServletRequest request, PageSupport support) {
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
	@MenuMonitor(name = "权限列表-获取权限树", orders = 2, level = 5, paraentAlias = "permissionList")
	@RequestMapping(name = "获取权限树", value = "/platform/access/permission/permissionTree.json", method = {
			RequestMethod.POST })
	public MessageObject<Permission> permissionTree(HttpServletRequest request, PageSupport support) {
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
