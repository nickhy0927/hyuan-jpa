package com.iss.oauth.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;

@SuppressWarnings("serial")
public class UrlConfigAttribute implements ConfigAttribute {

	private final HttpServletRequest httpServletRequest;

	public UrlConfigAttribute(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public String getAttribute() {
		return null;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
}
