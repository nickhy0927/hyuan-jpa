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
import com.iss.aspect.anno.OperateLog;
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

	private final IconService iconService;

	private final MenuService menuService;

	@Autowired
	public MenuController(IconService iconService, MenuService menuService) {
		this.iconService = iconService;
		this.menuService = menuService;
	}

	@RequestMapping(name = "菜单新增页面", value = "/platform/access/menu/menuCreate.do", method = RequestMethod.GET)
	public String menuCreate() {
		return "platform/access/menu/menuCreate";
	}

	@ResponseBody
	@RequestMapping(name = "获取菜单信息", value = "/platform/access/menu/menuCreateJson.json", method = RequestMethod.GET)
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
	@OperateLog(message = "保存菜单信息", optType = DataType.OptType.INSERT, service = MenuService.class)
	@RequestMapping(name = "保存菜单信息", value = "/platform/access/menu/menuCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuCreateSave(Menu menu) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
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
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "删除菜单信息", optType = DataType.OptType.DELETE, service = MenuService.class)
	@RequestMapping(name = "删除菜单信息", value = "/platform/access/menu/menuDelete.json", method = RequestMethod.POST)
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

	@RequestMapping(name = "菜单修改页面", value = "/platform/access/menu/menuEdit.do", method = RequestMethod.GET)
	public String menuEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/menu/menuEdit";
	}

	@ResponseBody
	@RequestMapping(name = "获取菜单信息", value = "/platform/access/menu/menuEditJson.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuEditJson(String id) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> params = Maps.newConcurrentMap();
			List<Icon> icons = iconService.queryByMap(params);
			List<MenuTree> menuTrees = menuService.queryMenuTree();
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
	@OperateLog(message = "修改菜单信息", optType = DataType.OptType.UPDATE, service = MenuService.class)
	@RequestMapping(name = "修改菜单信息", value = "/platform/access/menu/menuEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuEditUpdate(Menu menu) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			menu.setStatus(IsDelete.NO);
			if (StringUtils.isNotEmpty(menu.getParentId())) {
				menu.setMenu(menuService.get(menu.getParentId()));
			}
			if (StringUtils.isNotEmpty(menu.getIconId())) {
				menu.setIcon(iconService.get(menu.getIconId()));
			}
			menu.setStatus(IsDelete.NO);
			menuService.saveEntity(menu);
			messageObject.openTip("修改菜单成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@RequestMapping(name = "菜单列表页面", value = "/platform/access/menu/menuList.do", method = RequestMethod.GET)
	public String menuList() {
		return "platform/access/menu/menuList";
	}

	@ResponseBody
	@AccessAuthority(alias = "MENULIST", name = "获取菜单列表分页")
	@RequestMapping(name = "获取菜单列表分页", value = "/platform/access/menu/menuList.json", method = { RequestMethod.POST })
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
	@OperateLog(message = "更新菜单状态", optType = DataType.OptType.UPDATE, service = MenuService.class)
	@RequestMapping(name = "更新菜单状态", value = "/platform/access/menu/menuStatusUpdate.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuStatusUpdate(Menu menu) {
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
			if (StringUtils.isNoneEmpty(menu.getEnable())) {
				messageObject.openTip("菜单" + AccessConstant.Enable.getName(menu.getEnable()) + "失败");
			} else {
				messageObject.openTip("菜单" + AccessConstant.Locked.getName(menu.getLocked()) + "失败");
			}
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(name = "获取菜单树", value = "/platform/access/menu/menuTreeListToJson.json", method = { RequestMethod.GET })
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

}
