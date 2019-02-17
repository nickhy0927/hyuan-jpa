package com.iss.common.utils;

/**
 * 下拉树
 * @author Mr's Huang
 */
public class SelectTree {

	private boolean chkDisabled = false;

	private String id;

	private String name;

	private Object obj;

	private boolean open = false;

	private String pId;
	
	public SelectTree(String id, String name, Object obj, String pId) {
		super();
		this.id = id;
		this.name = name;
		this.obj = obj;
		this.pId = pId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Object getObj() {
		return obj;
	}

	public String getpId() {
		return pId;
	}

	public boolean isChkDisabled() {
		return chkDisabled;
	}

	public boolean isOpen() {
		return open;
	}

	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
}
