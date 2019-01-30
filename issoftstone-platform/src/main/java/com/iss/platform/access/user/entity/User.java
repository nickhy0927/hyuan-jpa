package com.iss.platform.access.user.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iss.common.utils.IdEntity;
import com.iss.constant.AccessConstant;
import com.iss.platform.access.role.entity.Role;

/**
 * 用户
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_p_a_user")
public class User extends IdEntity {

	private String nickName;// 用户昵称
	private String loginName;// 登录名称
	private String locked; // 锁定 true 是 false 否
	private String enable; // 启用 true 是 false 否
	private String password;// 登录密码
	private String brithday;// 出生日期
	private String email;// 电子邮箱
	private String userTag;// 用户手机端的标识
	private List<Role> roles = new ArrayList<Role>();
	private String remark; // 用户信息备注
	private String salt; // 加密盐
	private byte[] image; // 用户图像
	private Date lastLoginTime;

	// 附加字段
	private String enableName;// 是否显示 true 是 false 否
	private String lockedName; // 是否锁定 true 是 false 否

	@Column(columnDefinition = "varchar(255) comment '手机端标识'")
	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public String getBrithday() {
		return brithday;
	}

	public String getEmail() {
		return email;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPassword() {
		return password;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "t_p_a_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public List<Role> getRoles() {
		return roles;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Column(length = 1)
	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Column(length = 1)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "image", columnDefinition = "BLOB", nullable = true)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
