package com.iss.platform.access.menu.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.iss.constant.AccessConstant;
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
	private IconService iconService;

	@Autowired
	private MenuService menuService;

	@AccessAuthority(alias = "menu-save", name = "菜单新增页面")
	@RequestMapping(value = "/platform/access/menu/menuCreate.do", method = RequestMethod.GET)
	public String menuCreate() {
		return "platform/access/menu/menuCreate";
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-save", name = "获取新增菜单信息")
	@RequestMapping(value = "/platform/access/menu/menuCreate.json", method = RequestMethod.GET)
	public MessageObject<Menu> menuCreateJson() {
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
	@AccessAuthority(alias = "menu-delete", name = "删除菜单")
	@OperateLog(message = "删除菜单信息", method = "delete", optType = DataType.OptType.DELETE, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/menuDekete.json", method = RequestMethod.POST)
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

	@ResponseBody
	@AccessAuthority(alias = "menu-edit", name = "修改菜单")
	@OperateLog(message = "修改菜单信息", method = "edit", optType = DataType.OptType.UPDATE, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/menuEdit.json", method = RequestMethod.POST)
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

	@AccessAuthority(alias = "menu-edit", name = "菜单修改页面")
	@RequestMapping(value = "/platform/access/menu/menuEdit.do", method = RequestMethod.GET)
	public String menuEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/menu/menuEdit";
	}

	@AccessAuthority(alias = "menu-list", name = "进入菜单列表页面")
	@RequestMapping(value = "/platform/access/menu/menuList.do", method = RequestMethod.GET)
	public String menuList() {
		return "platform/access/menu/menuList";
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-list", name = "获取菜单列表分页")
	@RequestMapping(value = "/platform/access/menu/menuList.json", method = { RequestMethod.POST })
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
	@AccessAuthority(alias = "menu-tree-list", name = "获取菜单列表")
	@RequestMapping(value = "/platform/access/menu/menuTreeListToJson.json", method = { RequestMethod.GET })
	public Map<String, Object> menuTreeList(HttpServletRequest request) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		try {
			map.put("status_eq", IsDelete.NO);
			List<Menu> menus = menuService.queryByMap(map);
			for (Menu menu : menus) {
				menu.setParentId(menu.getMenu() == null ? "" : menu.getMenu().getId());
			}
			map.put("code", 0);
			map.put("msg", true);
			map.put("data", menus);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return map;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-save|menu-edit", name = "保存菜单信息")
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
	@AccessAuthority(alias = "menu-save|menu-edit", name = "保存菜单信息")
	@OperateLog(message = "新增菜单信息", method = "menuStatusEdit", optType = DataType.OptType.INSERT, service = MenuService.class)
	@RequestMapping(value = "/platform/access/menu/menuStatusEdit.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuStatusEdit(Menu menu) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			menuService.saveEntity(menu);
			if (StringUtils.isNoneEmpty(menu.getEnable())) {
				messageObject.openTip("菜单" + AccessConstant.Enable.getName(menu.getEnable()) + "成功");
			} else {
				messageObject.openTip("菜单" + AccessConstant.Locked.getName(menu.getLocked()) + "成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

}
