package com.iss.platform.access.permission.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.iss.common.utils.IdEntity;
import com.iss.platform.access.menu.entity.Menu;

@Entity
@Table(name = "t_p_a_perm")
@SuppressWarnings("serial")
public class Permission extends IdEntity {

	private String name;

	private String permission;

	private String pId;

	private List<Menu> menus = Lists.newArrayList();

	/**
	 * 权限名称
	 * 
	 * @return
	 */
	@Column(columnDefinition = "varchar(64) comment '权限名称'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(columnDefinition = "varchar(64) comment '权限别名'")
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "t_p_a_perm_menu", joinColumns = @JoinColumn(name = "perm_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Column(name = "parent_id", columnDefinition = "varchar(64) comment '上级权限ID'")
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}
}
