package com.iss.common.config;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

public class InitEnvironment {

	private static String errorPage;// 前台错误页面
	private static String initPassword;
	private static String initUsername;
	private static String currentLoginName;

	private static Map<String, Set<String>> permissionSet;
	
	public String getErrorPage() {
		return errorPage;
	}

	public static String getInitPassword() {
		return initPassword;
	}

	public static String getInitUsername() {
		return initUsername;
	}

	public void setErrorPage(String errorPage) {
		InitEnvironment.errorPage = errorPage;
	}

	public void setInitPassword(String initPassword) {
		InitEnvironment.initPassword = initPassword;
	}

	public void setInitUsername(String initUsername) {
		InitEnvironment.initUsername = initUsername;
	}
	
	public static void setPermissionSet(Map<String, Set<String>> permissionSet) {
		InitEnvironment.permissionSet = permissionSet;
	}
	
	public static Set<String> getPermissionSet(String loginName) {
		if (StringUtils.isEmpty(loginName)) {
			return Sets.newConcurrentHashSet();
		}
		return permissionSet.get(loginName);
	}
	
	public static String getCurrentLoginName() {
		return currentLoginName;
	}
	
	public static void setCurrentLoginName(String currentLoginName) {
		InitEnvironment.currentLoginName = currentLoginName;
	}
}
