package com.iss.news.spider.source.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iss.common.utils.IdEntity;

/**
 * 数据源管理
 *
 * @author Mr'Huang
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_config_ds")
public class DataSource extends IdEntity {
	/**
	 * 数据源编码
	 */
	private String sourceCode;

	/**
	 * 数据源名称
	 */
	private String sourceName;

	/**
	 * 数据源类型
	 */
	private String sourceTypeId;

	/**
	 * 采集地址
	 */
	private String url;

	/**
	 * 数据源权重
	 */
	private Integer weight;

	/**
	 * 数据源来源
	 */
	private String originTypeId;

	/**
	 * 数据源保存文件名
	 */
	private String fileName;

	/**
	 * 是否启用 1 启用 2 停用
	 */
	private String enable;

	/**
	 * 是否启用代理 1 启用 2 停用
	 */
	private Integer proxy;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 请求方式
	 */
	private String requestType;

	/**
	 * 层级
	 */
	private Integer levels;

	/**
	 * 执行实现类接口实现
	 */
	private String excuteClassName;

	// 附加字段

	/**
	 * 请求方法名称
	 */
	private String requestTypeName;
	/**
	 * 数据源类型名称
	 */
	private String sourceTypeName;

	/**
	 * 数据源来源名称
	 */
	private String originName;

	/**
	 * 数据源来源ID
	 */
	private String originNameId;

	/**
	 * 是否启用 1 启用 2 停用
	 */
	private String enableName;

	/**
	 * 是否启用代理 1 启用 2 停用
	 */
	private String proxyName;

	@Column(columnDefinition = "varchar(64) comment '数据源编码'")
	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(columnDefinition = "varchar(64) comment '数据源名称'")
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Column(columnDefinition = "varchar(64) comment '数据源类型'")
	public String getSourceTypeId() {
		return sourceTypeId;
	}
	
	@Column(columnDefinition = "varchar(2) comment '采集地址'")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(columnDefinition = "int(9) comment '数据源编码'")
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@Column(name = "origin_id", columnDefinition = "varchar(64) comment '数据源来源'")
	public String getOriginTypeId() {
		return originTypeId;
	}

	@Column(columnDefinition = "varchar(64) comment '文件名称'")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(columnDefinition = "varchar(2) comment '是否启用 1 启用 0 停用'")
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "int(2) comment '是否代理'")
	public Integer getProxy() {
		return proxy;
	}

	public void setProxy(Integer proxy) {
		this.proxy = proxy;
	}

	@Column(columnDefinition = "varchar(256) comment '数据源备注'")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "request_id", columnDefinition = "varchar(64) comment '请求方式'")
	public String getRequestType() {
		return requestType;
	}

	@Column(columnDefinition = "int(9) comment '爬取层级'")
	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	@Column(columnDefinition = "varchar(256) comment '爬取类型实现类'")
	public String getExcuteClassName() {
		return excuteClassName;
	}

	public void setExcuteClassName(String excuteClassName) {
		this.excuteClassName = excuteClassName;
	}

	@Transient
	public String getRequestTypeName() {
		return requestTypeName;
	}

	public void setRequestTypeName(String requestTypeName) {
		this.requestTypeName = requestTypeName;
	}

	@Transient
	public String getSourceTypeName() {
		return sourceTypeName;
	}

	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

	@Transient
	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	@Transient
	public String getEnableName() {
		return enableName;
	}

	public void setEnableName(String enableName) {
		this.enableName = enableName;
	}

	@Transient
	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public String getOriginNameId() {
		return originNameId;
	}

	public void setOriginNameId(String originNameId) {
		this.originNameId = originNameId;
	}

	public void setSourceTypeId(String sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public void setOriginTypeId(String originTypeId) {
		this.originTypeId = originTypeId;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
