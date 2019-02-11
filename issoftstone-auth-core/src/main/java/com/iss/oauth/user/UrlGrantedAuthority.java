package com.iss.oauth.user;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class UrlGrantedAuthority implements GrantedAuthority {

	private final String alias;

	private final String url;

	public UrlGrantedAuthority(String alias, String url) {
		this.alias = alias;
		this.url = url;
	}

	@Override
	public String getAuthority() {
		return url;
	}

	public String getAlias() {
		return alias;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UrlGrantedAuthority target = (UrlGrantedAuthority) o;
		if (alias.equals(target.getAlias()) && url.equals(target.getUrl()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		int result = alias != null ? alias.hashCode() : 0;
		result = 31 * result + (url != null ? url.hashCode() : 0);
		return result;
	}

}
