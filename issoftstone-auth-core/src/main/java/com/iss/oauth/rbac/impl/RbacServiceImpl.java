package com.iss.oauth.rbac.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.iss.oauth.rbac.RbacService;
import com.iss.platform.access.menu.entity.Menu;
import com.iss.platform.access.user.service.UserService;

@Component(value = "rbacService")
public class RbacServiceImpl implements RbacService {
	
	private AntPathMatcher matcher  = new AntPathMatcher();
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		boolean hasPermission = false;
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			List<Menu> menus = userService.queryMenuList(username);
			for (Menu menu : menus) {
				String url = menu.getUrl();
				System.out.println("url====" + url);
				if (StringUtils.equals(url, "/platform/access/role/roleMenuSave.json")) {
					System.out.println("-------------------");
				}
				if (matcher.match(url, requestURI)) {
					hasPermission = true;
					break;
				}
			}
		}
		return hasPermission;
	}
}
