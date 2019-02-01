package com.iss.oauth.user;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;

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
		if (request == null) {
			return new com.iss.platform.access.user.entity.User();
		}
		HttpSession session = request.getSession();
		Object object = session.getAttribute("SPRING_SECURITY_CONTEXT");
		SecurityContextImpl securityContextImpl = (SecurityContextImpl) object;
		String username = securityContextImpl.getAuthentication().getName();
		UserService userService = SpringContextHolder.getBean(UserService.class);
		com.iss.platform.access.user.entity.User user = userService.findUserByLoginName(username);
		return user;
	}
	
}