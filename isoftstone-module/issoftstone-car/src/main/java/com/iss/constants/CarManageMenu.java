package com.iss.constants;

import org.springframework.stereotype.Controller;

import com.iss.orm.anno.MenuMonitor;

@Controller
public class CarManageMenu {

	/**
	 * 顶级菜单
	 */
	public final static String TOP = "top";
	/**
	 * 基础设置
	 */
	public final static String BASE_MANAGE = "carBaseManage";
	/**
	 * 汽车销售系统
	 */
	public final static String BLOG_CONTENT_MANAGE = "carInfoManage";

	@MenuMonitor(name = "汽车销售管理", orders = 2, level = 1, paraentAlias = TOP)
	public void carManage() {

	}

	@MenuMonitor(name = "基础设置", orders = 1, level = 2, paraentAlias = "carManage")
	public void carBaseManage() {

	}

	@MenuMonitor(name = "车辆管理", orders = 2, level = 2, paraentAlias = "carManage")
	public void carInfoManage() {

	}

}
