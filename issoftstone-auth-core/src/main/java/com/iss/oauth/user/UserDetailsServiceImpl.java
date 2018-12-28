package com.iss.oauth.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

public class UserDetailsServiceImpl implements UserDetailsService, CredentialsContainer {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByLoginName(username);
		Collection<GrantedAuthority> authorities = getAuthorities();
		UserPrincipal userdetails = new UserPrincipal(username, 
				user.getPassword(), user.getEnable(), true,
				true, !user.getLocked(), authorities);
		userdetails.setSalt(user.getSalt());
		return userdetails;
	}

	/** * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤 * @param * @return */
	private Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authList;
	}

	@Override
	public void eraseCredentials() {

	}

}
