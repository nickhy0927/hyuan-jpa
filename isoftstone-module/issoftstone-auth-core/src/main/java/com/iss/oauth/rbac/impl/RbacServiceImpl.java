package com.iss.oauth.rbac.impl;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.iss.interceptor.StaticResources;
import com.iss.oauth.rbac.RbacService;

@Component(value = "rbacService")
public class RbacServiceImpl implements RbacService {
	
	private AntPathMatcher matcher  = new AntPathMatcher();
	
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		boolean hasPermission = false;
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Map<String, String> map = StaticResources.permissionSet.get(username);
			Set<String> keySet = map.keySet();
			for (String url : keySet) {
				if (matcher.match(url, requestURI)) {
					hasPermission = true;
					break;
				}
			}
		}
		return hasPermission;
	}
}
