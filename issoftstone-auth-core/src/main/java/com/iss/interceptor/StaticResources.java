package com.iss.interceptor;

import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.iss.aspect.quartz.QuartzManager;
import com.iss.common.config.InitEnvironment;
import com.iss.common.exception.ServiceException;
import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.SysContants.IsStart;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.menu.service.MenuService;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;
import com.iss.platform.system.autotask.entity.AutoTask;
import com.iss.platform.system.autotask.service.AutoTaskService;

public class StaticResources {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private AutoTaskService autoTaskService;
	
	public static Map<String, Map<String, String>> permissionSet = Maps.newConcurrentMap();

	private static List<String> urls;
	private static String loginUrl;
	private static String successUrl;

	private static String unauthUrl;

	public static String getUnauthUrl() {
		return unauthUrl;
	}

	public void setUnauthUrl(String unauthUrl) {
		StaticResources.unauthUrl = unauthUrl;
	}

	public static String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		StaticResources.successUrl = successUrl;
	}

	public static String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		StaticResources.loginUrl = loginUrl;
	}

	private StaticResources() {
	}

	public static List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		StaticResources.urls = urls;
	}

	public static StaticResources getStaticResourcesInstance() {
		return SpringContextHolder.getBean(StaticResources.class);
	}
	
	public void init() {
		Map<String, Object> paramMap = Maps.newConcurrentMap();
		paramMap.put("status_eq", IsDelete.NO);
		List<User> users = userService.queryByMap(paramMap);
		for (User user : users) {
			List<Menu> menus = userService.queryMenuList(user.getLoginName());
			Map<String, String> map = Maps.newConcurrentMap();
			for (Menu menu : menus) {
				map.put(menu.getUrl(), menu.getAlias());
			}
			permissionSet.put(user.getLoginName(), map);
		}
		List<Menu> menuList = menuService.queryByMap(paramMap);
		Map<String, String> map = Maps.newConcurrentMap();
		for (Menu menu : menuList) {
			map.put(menu.getUrl(), menu.getAlias());
		}
		permissionSet.put(InitEnvironment.getInitUsername(), map);
		InitEnvironment.setPermissionSet(permissionSet);
		paramMap.put("startStatus_eq", IsStart.YES);
		try {
			List<AutoTask> autoTasks = autoTaskService.queryByMap(paramMap);
			for (AutoTask autoTask : autoTasks) {
				Class<? extends Job> clazz = Class.forName(autoTask.getClassName()).asSubclass(Job.class);
				String job_name = autoTask.getTaskName();
				QuartzManager.addJob(job_name, clazz, autoTask.getScheduler());
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
