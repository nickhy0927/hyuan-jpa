package com.iss.platform.access.menu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.menu.entity.Menu;

/**
 * Created by Curtain on 2015/9/15.
 */
public interface MenuDao extends CustomRepostiory<Menu,String> {

	@Query("select m from Menu m where m.parentAlias = ?1 and m.status = 0 and m.enable = 1 order by m.orders")
	List<Menu> queryTopMenuList(String parentAlias);
	
	@Query("select m from Menu m where m.parentAlias = ?1 and m.status = 0 and m.enable = 1 order by m.orders")
	List<Menu> queryIndexMenuList(String parentAlias);
	
	@Query("select m from Menu m where m.parentAlias = ?1 and m.status = 0 and m.enable = 1 order by m.orders")
	List<Menu> queryMenuListByParentAlias(String parentAlias);
	
	@Query("select m from Menu m where m.alias = ?1 and m.status = 0")
	Menu queryMenuByAlias(String alias);
}
