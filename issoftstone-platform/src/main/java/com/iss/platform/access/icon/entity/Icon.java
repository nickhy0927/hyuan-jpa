package com.iss.platform.access.icon.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iss.common.utils.IdEntity;

/**
 * 图标
 */
@Entity
@Table(name = "t_p_a_icon")
public class Icon extends IdEntity {

	private String name;// 图标名字
	
	private String className;

	private String iconClass;// 图标样式
	
	private String icon;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Transient
	public String getIcon() {
		icon = iconClass;
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
