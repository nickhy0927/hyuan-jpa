package com.iss.common.config;

public class InitEnvironment {

	private static String errorPage;// 前台错误页面
	private static String initPassword;
	private static String initUsername;

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
}
