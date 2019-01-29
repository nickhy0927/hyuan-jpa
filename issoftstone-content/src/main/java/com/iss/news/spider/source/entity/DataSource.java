package com.iss.news.spider.source.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 数据源管理
 * @author Mr'Huang
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_config_ds")
public class DataSource extends IdEntity {
	// 数据源地址
	private String url;
	// 爬取类型
	private String type;
	// 请求方式
	private Integer requestType;
	// 是否需要代理 true 是 false 否
	private Boolean proxy;
	// 是否启用 true 启用 false 停用
	private Boolean enable;
	// 执行类
	private String excuteClassName;
	// 数据来源
	private Integer dataSource;
	// 数据类型
	private Integer dataType;

	@Column(columnDefinition = "varchar(255) comment '采集地址'")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(columnDefinition = "varchar(64) comment '爬取类型'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(columnDefinition = "int(2) comment '字典编号'")
	public Integer getRequestType() {
		return requestType;
	}

	public void setRequestType(Integer requestType) {
		this.requestType = requestType;
	}

	@Column(columnDefinition = "bit(1) comment '是否代理 0 否 1 是'")
	public Boolean getProxy() {
		return proxy;
	}

	public void setProxy(Boolean proxy) {
		this.proxy = proxy;
	}

	@Column(columnDefinition = "bit(1) comment '是否启用 0 停用 1 启用'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "varchar(255) comment '执行类'")
	public String getExcuteClassName() {
		return excuteClassName;
	}

	public void setExcuteClassName(String excuteClassName) {
		this.excuteClassName = excuteClassName;
	}

	@Column(columnDefinition = "int(2) comment '数据来源'")
	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	@Column(columnDefinition = "int(2) comment '数据类型'")
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
}
