package com.iss.interceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.iss.common.config.InitEnvironment;
import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

public class StaticResources {
	
	@Autowired
	private UserService userService;

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
		InitEnvironment.setPermissionSet(permissionSet);
	}

}
