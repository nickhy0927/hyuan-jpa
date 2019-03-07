package com.iss.constant.scan;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.Underline2Camel;
import com.iss.orm.anno.MenuMonitor;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.service.MenuService;

public class InitMenu {

	private String controllerPackage;
	
	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}
	
	@Autowired
	private MenuService menuService;
	
	public void initMenu() {
		Assert.assertNotNull(controllerPackage, "需要扫描的controller包不能为空");
		try {
			Set<Class<?>> pkgs = ClassPathCandidateComponentScanner.getClzFromPkg(controllerPackage);
			List<Menu> menus = Lists.newArrayList();
			for (Class<?> clazz : pkgs) {
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					MenuMonitor monitor = method.getDeclaredAnnotation(MenuMonitor.class);
					if (monitor != null) {
						String alias = Underline2Camel.camel2Underline(method.getName());
						Menu menu = menuService.queryMenuByAlias(alias);
						menu = menu == null ? new Menu() : menu;
						getMenu(menus, clazz, method, monitor, method.getDeclaredAnnotation(RequestMapping.class), menu);
					}
				}
			}
			menuService.saveBatch(menus);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
 
	private void getMenu(List<Menu> menus, Class<?> clazz, Method method, MenuMonitor monitor,
			RequestMapping mapping, Menu menu) {
		menu.setAlias(Underline2Camel.camel2Underline(method.getName()));
		menu.setParentAlias(StringUtils.isEmpty(monitor.paraentAlias()) ? "" : Underline2Camel.camel2Underline(monitor.paraentAlias()));
		menu.setEnable(monitor.enable());
		menu.setStatus(Boolean.FALSE);
		menu.setOrders(monitor.orders());
		menu.setName(monitor.name());
		menu.setShows(monitor.shows());
		menu.setLevel(monitor.level());
		menu.setUrl(StringUtils.isNotEmpty(monitor.url()) ? monitor.url() : (mapping == null ? "#" : StringUtils.join(mapping.value())));
		menu.setLocalCode(clazz.getName().toLowerCase() + "." + method.getName().toLowerCase());
		if (StringUtils.isNotEmpty(monitor.url())) {
			menu.setRequestType(RequestMethod.GET.name());
		} else
			menu.setRequestType(mapping != null ? StringUtils.join(mapping.method()) : "");
		menus.add(menu);
	}
}
