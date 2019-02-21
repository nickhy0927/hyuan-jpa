package com.iss.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
		if (user == null) {
			throw new UsernameNotFoundException("账户不存在", new Throwable());
		}
		Collection<GrantedAuthority> authorities = getAuthorities(user);
		Boolean locked = StringUtils.equals(user.getLocked(), "1");
		Boolean enable = StringUtils.equals(user.getEnable(), "1");
		UserPrincipal userdetails = new UserPrincipal(username, 
				user.getPassword(), enable, true,
				true, !locked, authorities);
		userdetails.setSalt(user.getSalt());
		return userdetails;
	}

	/** * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤 * @param * @return */
	private Collection<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		Set<String> aliasSet = userService.queryMenuAlias(user.getId());
		for (String alias : aliasSet) {
			System.out.println(alias);
			authList.add(new SimpleGrantedAuthority(alias));
		}
		if (authList.size() == 0) {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authList;
	}

	@Override
	public void eraseCredentials() {

	}

}
