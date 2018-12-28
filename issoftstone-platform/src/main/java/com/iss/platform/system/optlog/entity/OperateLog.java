package com.iss.platform.system.optlog.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_p_s_optlog")
public class OperateLog extends IdEntity {
	private String username; // 操作人名字
	private String userId; // 操作人ID
	private String message; // 操作信息
	private String method; // 操作方法
	private String clazz; // 操作类名称
	private Integer optType; // 操作类型
	private String data; // 操作数据

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public Integer getOptType() {
		return optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
