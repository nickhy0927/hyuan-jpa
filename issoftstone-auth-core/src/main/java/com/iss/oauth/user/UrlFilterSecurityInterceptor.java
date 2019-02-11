package com.iss.oauth.user;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class UrlFilterSecurityInterceptor extends FilterSecurityInterceptor {

	public UrlFilterSecurityInterceptor() {
		super();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);
	}

	@Override
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return super.getSecurityMetadataSource();
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return super.obtainSecurityMetadataSource();
	}

	@Override
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		super.setSecurityMetadataSource(newSource);
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return super.getSecureObjectClass();
	}

	@Override
	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		super.invoke(fi);
	}

	@Override
	public boolean isObserveOncePerRequest() {
		return super.isObserveOncePerRequest();
	}

	@Override
	public void setObserveOncePerRequest(boolean observeOncePerRequest) {
		super.setObserveOncePerRequest(observeOncePerRequest);
	}
}
