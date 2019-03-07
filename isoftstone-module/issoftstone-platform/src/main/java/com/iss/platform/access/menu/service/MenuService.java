package com.iss.platform.access.menu.service;

import java.util.List;

import com.iss.orm.service.CustomService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.entity.Tree;
import com.iss.platform.access.menu.entity.Ztree;

/**
 * Created by Curtain on 2015/9/15.
 */
public interface MenuService extends CustomService<Menu, String> {

	List<MenuTree> queryMenuTree();
	
	List<MenuTree> queryIndexMenuList();

	List<Tree> queryLayerMenuTree(List<Menu> menus);

	List<Ztree> queryZtreeMenuTree(List<Menu> menus);
	
	Menu queryMenuByAlias(String alias);
	
	List<Ztree> queryZtree(List<Menu> menus);

}
