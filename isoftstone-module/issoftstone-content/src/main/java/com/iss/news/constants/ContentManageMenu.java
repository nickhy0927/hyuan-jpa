package com.iss.news.constants;

import org.springframework.stereotype.Controller;

import com.iss.orm.anno.MenuMonitor;

@Controller
public class ContentManageMenu {

	/**
	 * 顶级菜单
	 */
	public final static String TOP = "top";
	/**
	 * 基础设置
	 */
	public final static String BASE_MANAGE = "newsBaseManage";
	/**
	 * 博客管理
	 */
	public final static String NEWS_CONTENT_MANAGE = "newsContentManage";

	@MenuMonitor(name = "新闻管理", orders = 2, level = 1, paraentAlias = TOP)
	public void contentManage() {

	}

	@MenuMonitor(name = "基础设置", orders = 1, level = 2, paraentAlias = "contentManage")
	public void newsBaseManage() {

	}

	@MenuMonitor(name = "新闻管理", orders = 2, level = 2, paraentAlias = "contentManage")
	public void newsContentManage() {

	}

}
