package com.iss.oauth.user;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.iss.common.spring.SpringContextHolder;
import com.iss.platform.access.user.service.UserService;

public class UserPrincipal extends User {

	private String salt;
	
	private static final long serialVersionUID = -7439502203471471526L;
	
	private static HttpServletRequest request;

	public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public static HttpServletRequest getRequest() {
		return request;
	}
	
	public static void setRequest(HttpServletRequest request) {
		UserPrincipal.request = request;
	}
	
	public static com.iss.platform.access.user.entity.User getContextUser() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object principal = authentication.getPrincipal();
			UserDetails userDetails = (UserDetails) principal;
			UserService userService = SpringContextHolder.getBean(UserService.class);
			com.iss.platform.access.user.entity.User user = userService.findUserByLoginName(userDetails.getUsername());
			return user;
		} catch (Exception e) {
		}
		return new com.iss.platform.access.user.entity.User();
	}
	
}