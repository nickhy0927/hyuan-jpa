package com.iss.platform.access.menu.service.impl;

import com.google.common.collect.Lists;
import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.access.menu.dao.MenuDao;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.service.MenuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Curtain on 2015/9/15.
 */
@Service
public class MenuServiceImpl extends BaseCustomService<Menu, String> implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<MenuTree> queryMenuTree() {
		List<Menu> menuList = menuDao.queryTopMenuList();
		return queryMenuTree(menuList);
	}
	
	private List<MenuTree> queryMenuTree(List<Menu> menuList) {
		List<MenuTree> menuTrees = Lists.newArrayList();
		for (Menu menu : menuList) {
			List<Menu> children = menuDao.queryMenuListByParentId(menu.getId());
			menuTrees.add(new MenuTree(menu, queryMenuTree(children)));
		}
		return menuTrees;
	}
}
