package com.iss.constants;

import org.springframework.stereotype.Controller;

import com.iss.orm.anno.MenuMonitor;

@Controller
public class BlogManageMenu {

	/**
	 * 顶级菜单
	 */
	public final static String TOP = "top";
	/**
	 * 基础设置
	 */
	public final static String BASE_MANAGE = "blogBaseManage";
	/**
	 * 博客管理
	 */
	public final static String BLOG_CONTENT_MANAGE = "blogContentManage";

	@MenuMonitor(name = "博客管理", orders = 2, level = 1, paraentAlias = TOP)
	public void blogManage() {

	}

	@MenuMonitor(name = "基础设置", orders = 1, level = 2, paraentAlias = "blogManage")
	public void blogBaseManage() {

	}

	@MenuMonitor(name = "博客内容", orders = 2, level = 2, paraentAlias = "blogManage")
	public void blogContentManage() {

	}

}
