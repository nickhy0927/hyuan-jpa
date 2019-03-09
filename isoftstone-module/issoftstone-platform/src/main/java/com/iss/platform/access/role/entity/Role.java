package com.iss.platform.access.role.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;
import com.iss.platform.access.menu.entity.Menu;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_p_a_role")
public class Role extends IdEntity {
	private String code;
	private String name;
	private String remark;
	
	private Role role;

	private List<Menu> menus = new ArrayList<Menu>();

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "t_p_a_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@ManyToOne
	@JoinColumn(name = "p_id", columnDefinition = "varchar(64) comment '上级角色'")
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
