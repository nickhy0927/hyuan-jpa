package com.iss.platform.access.menu.entity;

public class MenuTree {

	private String name;// 菜单名称
	private String id;
	private String alias;
	private boolean open;
	private String pId;
	private Menu menu;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MenuTree(Menu menu) {
		this.menu = menu.getMenu();
		this.id = menu.getId();
		this.name = menu.getName();
		this.alias = menu.getAlias();
		this.url = menu.getUrl();
		this.pId = menu.getMenu() != null ? menu.getMenu().getId() : "";
	}


	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

}
