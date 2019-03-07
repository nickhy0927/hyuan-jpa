package com.iss.platform.access.menu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

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
@Table(name = "t_p_a_menu", uniqueConstraints = { @UniqueConstraint(columnNames = "alias") })
public class Menu extends IdEntity {

	private String name;
	private String url;// 访问地址
	private String alias;// 别名
	private Boolean enable;// 是否显示 true 显示 false 隐藏

	private Integer level;
	/**
	 * 国际化编码
	 */
	private String localCode;

	/**
	 * 上级菜单别名
	 */
	private String parentAlias;
	/**
	 * 菜单图标
	 */
	private Icon icon;

	private Integer orders;

	private String requestType;

	// 附加字段
	private String parentId;
	private String parentName;
	private String enableName;// 是否显示 true 显示 false 隐藏
	private String iconId;

	private Boolean checked;

	@ManyToOne
	@JoinColumn(name = "icon_id", columnDefinition = "varchar(64) comment '菜单图标'")
	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(columnDefinition = "varchar(64) comment '菜单名称'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(columnDefinition = "varchar(64) comment '菜单别名'")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Column(columnDefinition = "varchar(256) comment '菜单访问地址'")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(length = 1, columnDefinition = "int comment '是否启用true 是 false 否'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "varchar(256) comment '国际化编码'")
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
		enableName = AccessConstant.Enable.getName(enable == true ? "1" : "0");
		return enableName;
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
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(columnDefinition = "int comment '菜单排序'")
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

	@Column(columnDefinition = "varchar(64) comment '上级菜单别名'")
	public String getParentAlias() {
		return parentAlias;
	}

	public void setParentAlias(String parentAlias) {
		this.parentAlias = parentAlias;
	}

	@Column(columnDefinition = "varchar(32) comment '请求方式'")
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
