package com.iss.platform.system.performance.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 性能监控
 * @author Hyuan
 *
 */
@Entity
@Table(name = "t_p_s_performance")
public class Performance extends IdEntity {

	/**
	 * 执行的url
	 */
	private String url;
	
	/**
	 * 执行时间
	 */
	private Long executeTime;
	
	/**
	 * 请求别名
	 */
	private String alias;
	
	private String name;
	
	private String createDate;
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Long executeTime) {
		this.executeTime = executeTime;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
