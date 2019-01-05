package com.iss.platform.access.menu.service;

import java.util.List;

import com.iss.orm.service.CustomService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.entity.MenuTree;

/**
 * Created by Curtain on 2015/9/15.
 */
public interface MenuService extends CustomService<Menu,String> {
	
	List<MenuTree> queryMenuTree();
	
}
