package com.iss.platform.access.menu.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.iss.common.utils.IdEntity;
import com.iss.constant.AccessConstant;
import com.iss.platform.access.icon.entity.Icon;

/**
 * 菜单
 */
@Entity
@SuppressWarnings("serial")
@DynamicInsert
@DynamicUpdate
@Table(name = "t_p_a_menu")
public class Menu extends IdEntity {

	private String name;
	private String url;// 访问地址
	private String alias;// 别名
	private String enable;// 是否显示 true 显示 false 隐藏
	private String locked; // 是否锁定 true 是 false 否
	/**
	 * 国际化编码
	 */
	private String localCode;
	/**
	 * 上级菜单
	 */
	private Menu menu;
	/**
	 * 菜单图标
	 */
	private Icon icon; 
	
	private Integer orders;

	// 附加字段
	private String parentId;
	private String parentName;
	private String enableName;// 是否显示 true 显示 false 隐藏
	private String lockedName; // 是否锁定 true 是 false 否
	private String iconId;
	
	private Boolean checked;

	@ManyToOne
	@JoinColumn(name = "icon_id")
	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "p_id")
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(length = 1)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(length = 1)
	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	/**
	 * 不需要进行数据库映射
	 * 
	 * @return
	 */
	@Transient
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Transient
	public String getEnableName() {
		enableName = AccessConstant.Enable.getName(enable);
		return enableName;
	}

	@Transient
	public String getLockedName() {
		lockedName = AccessConstant.Locked.getName(locked);
		return lockedName;
	}

	@Transient
	public String getIconId() {
		return iconId;
	}

	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	@Transient
	public String getParentName() {
		if (menu != null) {
			parentName = menu.getName();
		}
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public Integer getOrders() {
		return orders;
	}
	
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	@Transient
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
