package com.iss.constants;

import org.springframework.stereotype.Controller;

import com.iss.orm.anno.MenuMonitor;

@Controller
public class SaleManageMenu {

	/**
	 * 顶级菜单
	 */
	public final static String TOP = "top";
	/**
	 * 基础设置
	 */
	public final static String PRODUCT_LEVELS_MANAGE = "productLevelsManage";
	/**
	 * 汽车销售系统
	 */
	public final static String CUSTOMER_LEVELS_MANAGE = "customerLevelsManage";

	@MenuMonitor(name = "销售系统管理", orders = 2, level = 1, paraentAlias = TOP)
	public void saleSystemManage() {

	}

	@MenuMonitor(name = "客户管理", orders = 1, level = 2, paraentAlias = "saleSystemManage")
	public void customerLevelsManage() {

	}
	
	@MenuMonitor(name = "产品管理", orders = 2, level = 2, paraentAlias = "saleSystemManage")
	public void productLevelsManage() {

	}


}
