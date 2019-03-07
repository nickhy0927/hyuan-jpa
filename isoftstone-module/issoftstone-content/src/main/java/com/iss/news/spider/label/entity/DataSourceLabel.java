package com.iss.news.spider.label.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iss.common.utils.IdEntity;
import com.iss.news.spider.source.entity.DataSource;

/**
 * 数据源标签
 * 
 * @author Mr'Huang
 *
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_config_label")
public class DataSourceLabel extends IdEntity {

	/**
	 * 数据源
	 */
	private DataSource dataSource;

	/**
	 * 标签名称
	 */
	private String labelName;

	/**
	 * 标签类型 （1.新闻标题标签 2.新闻内容标签 3.新闻时间标签 4.列表标签 5.分页标签 6.搜索标签）
	 */
	private String labelTypeId;

	/**
	 * 标签内容
	 */
	private String labelContent;

	/**
	 * 是否启用
	 */
	private Integer enable;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 规则对应的属性
	 */
	private String field;

	/**
	 * 类名称
	 */
	private String className;

	/**
	 * 标签执行的动作
	 */
	private Integer action;

	/**
	 * 内容类型
	 */
	private String contentTypeId;

	/**
	 * 层级
	 */
	private Integer levels;

	// 辅助字段
	/**
	 * 是否启用名称
	 */
	private String enableName;

	/**
	 * 标签类型名称
	 */
	private String labelTypeName;

	/**
	 * 数据源名称
	 */
	private String sourceName;

	/**
	 * 数据源ID
	 */
	private String sourceId;

	@ManyToOne
	@JoinColumn(name = "source_id", columnDefinition = "varchar(64) comment '数据源ID'")
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Column(columnDefinition = "varchar(256) comment '标签内容'")
	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	@JoinColumn(name = "label_type_id", columnDefinition = "varchar(64) comment '标签类型'")
	public String getLabelTypeId() {
		return labelTypeId;
	}


	@Column(columnDefinition = "varchar(256) comment '标签名称'")
	public String getLabelContent() {
		return labelContent;
	}

	public void setLabelContent(String labelContent) {
		this.labelContent = labelContent;
	}

	@Column(columnDefinition = "int(2) comment '是否启用 1 启用 0 停用'")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "varchar(256) comment '标签名称'")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(columnDefinition = "varchar(64) comment '标签字段'")
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Column(columnDefinition = "varchar(256) comment '实体类名称'")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(columnDefinition = "int(9) comment '标签动作'")
	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	@Column(name = "content_type_id", columnDefinition = "varchar(64) comment '内容类型'")
	public String getContentTypeId() {
		return contentTypeId;
	}


	@Column(columnDefinition = "int(9) comment '标签层级'")
	public Integer getLevels() {
		return levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}

	@Transient
	public String getEnableName() {
		return enableName;
	}

	public void setEnableName(String enableName) {
		this.enableName = enableName;
	}

	@Transient
	public String getLabelTypeName() {
		return labelTypeName;
	}

	public void setLabelTypeName(String labelTypeName) {
		this.labelTypeName = labelTypeName;
	}

	@Transient
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	@Transient
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setLabelTypeId(String labelTypeId) {
		this.labelTypeId = labelTypeId;
	}

	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}
}
