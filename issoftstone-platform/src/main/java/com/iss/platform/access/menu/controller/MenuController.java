package com.iss.platform.access.menu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.access.icon.entity.Icon;
import com.iss.platform.access.icon.service.IconService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.service.MenuService;

/**
 * Created by Curtain on 2015/9/15.
 */
@Controller
public class MenuController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private IconService iconService;

	@ResponseBody
	@AccessAuthority(alias = "menu-save", name = "新增菜单")
	@RequestMapping(value = "/platform/access/menu/create.json", method = RequestMethod.GET)
	public MessageObject<Menu> menuCreate() {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> params = Maps.newConcurrentMap();
			List<MenuTree> menuTrees = menuService.queryMenuTree();
			List<Icon> icons = iconService.queryByMap(params);
			params.put("menuTrees", menuTrees);
			params.put("icons", icons);
			messageObject.ok("新增菜单", params);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("进入新增菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-save", name = "保存菜单")
	@OperateLog(message = "新增菜单信息", method = "menuSave", optType = DataType.OptType.INSERT, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/menuSave.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuSave(Menu menu) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = menu.getId();
			menu.setStatus(IsDelete.NO);
			if (StringUtils.isNotEmpty(menu.getParentId())) {
				menu.setMenu(menuService.get(menu.getParentId()));
			}
			if (StringUtils.isNotEmpty(menu.getIconId())) {
				menu.setIcon(iconService.get(menu.getIconId()));
			}
			menu.setStatus(IsDelete.NO);
			menuService.saveEntity(menu);
			messageObject.openTip("新增菜单成功");
			if (StringUtils.isNotEmpty(id)) {
				messageObject.openTip("修改菜单成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-edit", name = "修改菜单")
	@OperateLog(message = "修改菜单信息", method = "edit", optType = DataType.OptType.UPDATE, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/edit.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuEdit(String id) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> params = Maps.newConcurrentMap();
			List<MenuTree> menuTrees = menuService.queryMenuTree();
			List<Icon> icons = iconService.queryByMap(params);
			params.put("menuTrees", menuTrees);
			params.put("icons", icons);
			Menu menu = menuService.get(id);
			params.put("menu", menu);
			messageObject.ok("修改查询菜单成功", params);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-list", name = "菜单列表")
	@RequestMapping(value = "/platform/access/menu/list.json", method = { RequestMethod.POST })
	public MessageObject<Menu> menuList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Menu> tools = menuService.queryPageByMap(map, support);
			messageObject.ok("查询菜单成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-delete", name = "删除菜单")
	@OperateLog(message = "删除菜单信息", method = "delete", optType = DataType.OptType.DELETE, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/delete.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuDelete(String id) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				menuService.deleteBatch(ids);
				messageObject.openTip("删除菜单成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除菜单异常");
		}
		return messageObject;
	}

}
