package com.iss.oauth.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.user.service.UserService;

public class UrlFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	@Autowired
	private UserService userService;

	private AntPathMatcher matcher = new AntPathMatcher();

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public UrlFilterSecurityMetadataSource(UserService userService) {
		this.loadResourceDefine();
	}

	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<Menu> menuAlias = userService.queryMenuList(UserPrincipal.getContextUser().getId());
		ConfigAttribute ca = null;
		Collection<ConfigAttribute> cca = new ArrayList<ConfigAttribute>();
		for (Menu menu : menuAlias) {
			if (resourceMap.containsKey(menu.getUrl())) {
				ca = new SecurityConfig(menu.getAlias());
				resourceMap.get(menu.getUrl()).add(ca);
			} else {
				ca = new SecurityConfig(menu.getAlias());
				cca.add(ca);
				resourceMap.put(menu.getUrl(), cca);
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (matcher.match(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public UserService getUserService() {
		return userService;
	}
}
