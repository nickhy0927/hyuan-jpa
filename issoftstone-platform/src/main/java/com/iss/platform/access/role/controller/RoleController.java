package com.iss.platform.access.role.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.Ztree;
import com.iss.platform.access.menu.service.MenuService;
import com.iss.platform.access.role.entity.Role;
import com.iss.platform.access.role.service.RoleService;

/**
 * Created by Curtain on 2015/9/21.
 */
@Controller
public class RoleController {
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

	private final MenuService menuService;

	private final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService, MenuService menuService) {
		this.roleService = roleService;
		this.menuService = menuService;
	}
	
	@RequestMapping(name = "角色新增页面", value = "/platform/access/role/roleCreate.do", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "platform/access/role/roleCreate";
	}
	@ResponseBody
	@RequestMapping(name = "获取菜单树", value = "/platform/access/menu/menuTreeList.json", method = RequestMethod.GET)
	public List<Ztree> menuTreeList(String id) {
		Role role = roleService.get(id);
		List<Menu> menus = Lists.newArrayList();
		if (role != null) menus = role.getMenus(); 
		return menuService.queryZtreeMenuTree(menus);
	}

	@ResponseBody
	@RequestMapping(name = "新增角色", value = "/platform/access/role/roleCreate.json", method = RequestMethod.GET)
	public MessageObject<Role> roleCreateJson() {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			List<Role> list = roleService.findAll();
			messageObject.ok("查询新增角色", list);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("新增菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "保存角色", optType = DataType.OptType.INSERT, service = RoleService.class)
	@RequestMapping(name = "保存角色", value = "/platform/access/role/roleCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Role> roleCreateSave(Role role) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = role.getId();
			role.setStatus(IsDelete.NO);
			roleService.saveEntity(role);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增角色成功");
			} else
				messageObject.openTip("修改角色成功");

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "删除角色", optType = DataType.OptType.DELETE, service = RoleService.class)
	@RequestMapping(name = "删除角色", value = "/platform/access/role/roleDetete.json", method = { RequestMethod.POST })
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

	@RequestMapping(name = "角色修改页面", value = "/platform/access/role/roleEdit.do", method = RequestMethod.GET)
	public String roleEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/role/roleEdit";
	}

	@ResponseBody
	@RequestMapping(name = "获取角色", value = "/platform/access/role/roleEditJson.json", method = RequestMethod.POST)
	public MessageObject<Role> roleEditJson(String id) {
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
	@OperateLog(message = "修改角色", optType = DataType.OptType.UPDATE, service = RoleService.class)
	@RequestMapping(name = "修改保存角色",value = "/platform/access/role/roleEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Role> roleEditUpdate(Role role) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			role.setStatus(IsDelete.NO);
			roleService.saveEntity(role);
			messageObject.openTip("修改角色成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@RequestMapping(name = "角色列表页面", value = "/platform/access/role/roleList.do")
	public String roleList() {
		return "platform/access/role/roleList";
	}

	@ResponseBody
	@RequestMapping(name = "获取角色列表分页", value = "/platform/access/role/roleList.json", method = { RequestMethod.POST })
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
	@OperateLog(message = "保存角色菜单", optType = DataType.OptType.INSERT, service = RoleService.class)
	@RequestMapping(name = "保存角色菜单", value = "/platform/access/role/roleMenuSave.json", method = RequestMethod.POST)
	public MessageObject<Role> roleMenuSave(String roleId, String menuIds) {
		MessageObject<Role> messageObject = MessageObject.getDefaultInstance();
		try {
			Role role = roleService.get(roleId);
			List<Menu> menus = Lists.newArrayList();
			String[] split = menuIds.split(",");
			for (String menuId : split)
				menus.add(menuService.get(menuId));
			role.setMenus(menus);
			roleService.saveEntity(role);
			messageObject.openTip("保存权限成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}
}
