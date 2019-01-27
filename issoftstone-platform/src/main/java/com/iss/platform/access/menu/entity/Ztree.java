package com.iss.platform.access.menu.entity;

public class Ztree {

	private String id;
	private String url;
	private String name;
	private String alias;
	private String pId;
	private boolean open = true;
	private Boolean checked;

	public Ztree(Menu menu) {
		this.id = menu.getId();
		this.url = menu.getUrl();
		this.alias = menu.getAlias();
		this.name = menu.getName();
		this.pId = menu.getMenu() == null ? "" : menu.getMenu().getId();
		this.checked = menu.getChecked() == null ? false : menu.getChecked();
	}

	public String getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Boolean getChecked() {
		return checked;
	}
	
	public boolean isOpen() {
		return open;
	}
}
