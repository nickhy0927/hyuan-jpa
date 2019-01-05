package com.iss.platform.access.menu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
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

	@ResponseBody
	@AccessAuthority(alias = "menu-create-json")
	@RequestMapping(value = "/platform/access/menu/create.json", method = RequestMethod.GET)
	public MessageObject<Menu> menuCreate() {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> params = Maps.newConcurrentMap();
			List<Menu> list = menuService.findAll();
			List<MenuTree> menuTrees = new ArrayList<MenuTree>();
			for (Menu menu : list) {
				menuTrees.add(new MenuTree(menu));
			}
			params.put("menuTrees", menuTrees);
			messageObject.ok("新增菜单", params);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("进入新增菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-save-json")
	@RequestMapping(value = "/platform/access/menu/save.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuSave(Menu menu) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(menu.getParentId())) {
				menu.setMenu(menuService.get(menu.getParentId()));
			}
			menu.setStatus(IsDelete.NO);
			menuService.save(menu);
			messageObject.ok("新增菜单成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-edit-json")
	@RequestMapping(value = "/platform/access/menu/edit.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuEdit(String id) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("修改查询菜单成功", menuService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询菜单异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "menu-list-json")
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
	@AccessAuthority(alias = "menu-delete-json")
	@RequestMapping(value = "/platform/access/menu/delete.json", method = RequestMethod.POST)
	public MessageObject<Menu> menuDelete(@RequestBody String[] ids) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			if (ids.length > 0) {
				menuService.deleteBatch(ids);
				messageObject.ok("删除菜单成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除菜单异常");
		}
		return messageObject;
	}

}
