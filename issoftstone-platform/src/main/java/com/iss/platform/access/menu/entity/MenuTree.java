package com.iss.platform.access.menu.entity;

import java.util.List;

public class MenuTree {

	private String id;
	private String name;// 菜单名称
	private String alias;
	private String url;
	private String localCode;
	private List<MenuTree> children;
	
	public MenuTree(Menu menu, List<MenuTree> children) {
		this.children = children;
		this.id = menu.getId();
		this.url = menu.getUrl();
		this.name = menu.getName();
		this.alias = menu.getAlias();
		this.localCode = menu.getLocalCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	
	public String getLocalCode() {
		return localCode;
	}
	
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
