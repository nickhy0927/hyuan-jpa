package com.iss.platform.access.menu.entity;

import java.util.List;

public class Tree {

	private String title;
	private String value;
	private Boolean checked;
	private List<Tree> data;
	
	public Tree(Menu menu, List<Tree> data) {
		this.data = data;
		this.title = menu.getName();
		this.value = menu.getId();
		this.checked = menu.getChecked() == null ? false : menu.getChecked();
	}

	public String getTitle() {
		return title;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getValue() {
		return value;
	}

	public List<Tree> getData() {
		return data;
	}
}
