package com.iss.platform.access.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.constant.PlatformManageMenu;
import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.access.menu.dao.MenuDao;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.entity.Tree;
import com.iss.platform.access.menu.entity.Ztree;
import com.iss.platform.access.menu.service.MenuService;

/**
 *
 * @author Hyuan
 * @date 2019/03/12
 */
@Service
public class MenuServiceImpl extends BaseCustomService<Menu, String> implements MenuService {

	@Autowired
	private MenuDao menuDao;
	
	@Override
	public List<MenuTree> queryIndexMenuList() {
		List<Menu> menuList = menuDao.queryTopMenuList(PlatformManageMenu.TOP);
		return queryIndexMenuTree(menuList);
	}
	
	private List<MenuTree> queryIndexMenuTree(List<Menu> menuList) {
		List<MenuTree> menuTrees = Lists.newArrayList();
		for (Menu menu : menuList) {
			if (menu == null || !menu.getShows()) {
				continue;
			}
			List<Menu> children = menuDao.queryMenuListByParentAlias(menu.getAlias());
			menuTrees.add(new MenuTree(menu, queryIndexMenuTree(children)));
		}
		return menuTrees;
	}
	
	@Override
	public List<MenuTree> queryMenuTree() {
		List<Menu> menuList = menuDao.queryTopMenuList(PlatformManageMenu.TOP);
		return queryMenuTree(menuList);
	}
	
	private List<MenuTree> queryMenuTree(List<Menu> menuList) {
		List<MenuTree> menuTrees = Lists.newArrayList();
		for (Menu menu : menuList) {
			List<Menu> children = menuDao.queryMenuListByParentAlias(menu.getAlias());
			menuTrees.add(new MenuTree(menu, queryMenuTree(children)));
		}
		return menuTrees;
	}
	
	@Override
	public List<Tree> queryLayerMenuTree(List<Menu> src) {
		List<Menu> menuList = menuDao.queryTopMenuList(PlatformManageMenu.TOP);
		return layerMenuTree(menuList, src);
	}
	
	private List<Tree> layerMenuTree(List<Menu> menuList, List<Menu> src) {
		List<Tree> trees = Lists.newArrayList();
		for (Menu menu : menuList) {
			List<Menu> children = menuDao.queryMenuListByParentAlias(menu.getAlias());
			for (Menu tree : src) {
				if (StringUtils.equals(tree.getId(), menu.getId())) {
					menu.setChecked(true);
					break;
				}
			}
			Tree treeMenu = new Tree(menu, layerMenuTree(children, src));
			trees.add(treeMenu);
		}
		return trees;
	}
	@Override
	public List<Ztree> queryZtreeMenuTree(List<Menu> src) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("status_eq", IsDelete.NO);
		List<Menu> menuList = menuDao.queryByMap(paramMap);
		return zTreeMenuTree(menuList, src);
	}
	
	private List<Ztree> zTreeMenuTree(List<Menu> menuList, List<Menu> src) {
		List<Ztree> trees = Lists.newArrayList();
		for (Menu menu : menuList) {
			for (Menu m : src) {
				if (StringUtils.equals(m.getId(), menu.getId())) {
					menu.setChecked(true);
					break;
				}
			}
			Ztree ztree = new Ztree(menu);
			Menu alias = queryMenuByAlias(menu.getParentAlias());
			ztree.setpId(alias != null ? alias.getId() : "");
			trees.add(ztree);
		}
		return trees;
	}

	@Override
	public Menu queryMenuByAlias(String alias) {
		return menuDao.queryMenuByAlias(alias);
	}

	@Override
	public List<Ztree> queryZtree(List<Menu> menus) {
		List<Menu> menuList = menuDao.queryTopMenuList(PlatformManageMenu.TOP);
		return queryChildren(menuList, menus);
	}
	
	private List<Ztree> queryChildren(List<Menu> menuList, List<Menu> menus) {
		List<Ztree> ztrees = Lists.newArrayList();
		for (Menu menu : menuList) {
			List<Menu> list = menuDao.queryMenuListByParentAlias(menu.getAlias());
			for (Menu m : menus) {
				if (StringUtils.equals(m.getId(), menu.getId())) {
					menu.setChecked(true);
				}
			}
			ztrees.add(new Ztree(menu, queryChildren(list, menus)));
		}
		return ztrees;
	}
}
