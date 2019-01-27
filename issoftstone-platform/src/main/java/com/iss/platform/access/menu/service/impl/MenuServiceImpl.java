package com.iss.platform.access.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.access.menu.dao.MenuDao;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.entity.Tree;
import com.iss.platform.access.menu.entity.Ztree;
import com.iss.platform.access.menu.service.MenuService;

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
	
	@Override
	public List<Tree> queryLayerMenuTree(List<Menu> src) {
		List<Menu> menuList = menuDao.queryTopMenuList();
		return layerMenuTree(menuList, src);
	}
	
	private List<Tree> layerMenuTree(List<Menu> menuList, List<Menu> src) {
		List<Tree> trees = Lists.newArrayList();
		for (Menu menu : menuList) {
			List<Menu> children = menuDao.queryMenuListByParentId(menu.getId());
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
			trees.add(ztree);
		}
		return trees;
	}
}
