package com.iss.platform.access.icon.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 图标
 */
@Entity
@Table(name = "t_p_a_icon")
public class Icon extends IdEntity {

	private String name;// 图标名字

	private String iconClass;// 图标样式

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

}
