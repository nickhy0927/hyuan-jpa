package com.iss.platform.access.menu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.menu.entity.Menu;

/**
 * Created by Curtain on 2015/9/15.
 */
public interface MenuDao extends CustomRepostiory<Menu,String> {

	@Query("select m from Menu m where m.menu.id is null and m.status = 0")
	List<Menu> queryTopMenuList();
	
	@Query("select m from Menu m where m.menu.id = ?1 and m.status = 0")
	List<Menu> queryMenuListByParentId(String parentId);
}
