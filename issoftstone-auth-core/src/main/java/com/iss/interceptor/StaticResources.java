package com.iss.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.iss.aspect.quartz.QuartzManager;
import com.iss.common.config.InitEnvironment;
import com.iss.common.exception.ServiceException;
import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.SysContants.IsStart;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;
import com.iss.platform.system.autotask.entity.AutoTask;
import com.iss.platform.system.autotask.service.AutoTaskService;

public class StaticResources {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AutoTaskService autoTaskService;

	private List<String> urls;
	private String loginUrl;
	private String successUrl;

	private String unauthUrl;

	public String getUnauthUrl() {
		return unauthUrl;
	}

	public void setUnauthUrl(String unauthUrl) {
		this.unauthUrl = unauthUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	private StaticResources() {
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public static StaticResources getStaticResourcesInstance() {
		return SpringContextHolder.getBean(StaticResources.class);
	}
	
	public void init() {
		Map<String, Object> paramMap = Maps.newConcurrentMap();
		paramMap.put("status_eq", IsDelete.NO);
		List<User> users = userService.queryByMap(paramMap);
		Map<String, Set<String>> permissionSet = Maps.newConcurrentMap();
		for (User user : users) {
			Set<String> alias = userService.queryMenuAlias(user.getId());
			permissionSet.put(user.getLoginName(), alias);
		}
		permissionSet.put("root", userService.queryAllMenuAlias());
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
