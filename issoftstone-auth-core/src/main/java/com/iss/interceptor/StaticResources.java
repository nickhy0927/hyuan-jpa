package com.iss.interceptor;

import java.util.List;

import com.iss.common.spring.SpringContextHolder;

public class StaticResources {

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

}
