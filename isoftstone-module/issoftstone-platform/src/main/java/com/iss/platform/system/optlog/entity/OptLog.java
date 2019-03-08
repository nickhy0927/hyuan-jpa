package com.iss.platform.system.optlog.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 操作日志
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_p_s_optlog")
public class OptLog extends IdEntity {
	private String message; // 操作信息
	private String method; // 操作方法
	private String clazz; // 操作类名称
	private Integer optType; // 操作类型
	private String data; // 操作数据
	private String userId;

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
	@Column(columnDefinition = "mediumtext comment '日志数据'")
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
