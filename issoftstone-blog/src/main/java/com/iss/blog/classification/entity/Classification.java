package com.iss.blog.classification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_b_classification")
public class Classification extends IdEntity {
	private static final long serialVersionUID = -3966462246636874855L;

	/**
	 * 分类名称
	 */
	private String name;

	/**
	 * 上级分类
	 */
	private Classification classification;

	/**
	 * 备注
	 */
	private String remarks;

	private String parentId;
	
	private String parentName;

	@Column(columnDefinition = "varchar(255) comment '分类名称'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "p_id", columnDefinition = "varchar(255) comment '上级分类'")
	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	@Column(columnDefinition = "varchar(255) comment '备注'")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Transient
	public String getParentId() {
		if (classification != null) {
			parentId = classification.getId();
		}
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Transient
	public String getParentName() {
		if (classification != null) {
			parentName = classification.getName();
		}
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
