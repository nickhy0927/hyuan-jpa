package com.iss.constant;

import org.springframework.stereotype.Controller;

import com.iss.orm.anno.MenuMonitor;

@Controller
public class PlatformManageMenu {

	/**
	 * 顶级菜单
	 */
	public final static String TOP = "top";
	/**
	 * 权限管理
	 */
	public final static String BASE_MANAGE = "baseManage";
	/**
	 * 系统管理
	 */
	public final static String SYSTEM_MANAGE = "systemManage";

	@MenuMonitor(name = "平台管理", orders = 1, level = 1, paraentAlias = TOP)
	public void platformManage() {

	}

	@MenuMonitor(name = "基础设置", orders = 1, level = 2, paraentAlias = "platformManage")
	public void baseManage() {

	}

	@MenuMonitor(name = "系统管理", orders = 2, level = 2, paraentAlias = "platformManage")
	public void systemManage() {

	}

}
