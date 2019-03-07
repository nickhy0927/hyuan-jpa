package com.iss.platform.access.menu.entity;

import java.util.List;

public class Ztree {

	private String id;
	private String url;
	private String name;
	private String alias;
	private String pId;
	private boolean open = true;
	private Boolean checked;
	private List<Ztree> children;

	public Ztree(Menu menu) {
		this.id = menu.getId();
		this.alias = menu.getAlias();
		this.name = menu.getName();
		this.checked = menu.getChecked() == null ? false : menu.getChecked();
	}
	
	public Ztree(Menu menu, List<Ztree> children) {
		this.id = menu.getId();
		this.alias = menu.getAlias();
		this.name = menu.getName();
		this.checked = menu.getChecked() == null ? false : menu.getChecked();
		this.children = children;
	}
	
	public List<Ztree> getChildren() {
		return children;
	}
	
	public void setChildren(List<Ztree> children) {
		this.children = children;
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
