package com.iss.news.category.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 栏目分类
 * @author Mr's Huang
 *
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_category")
public class Category extends IdEntity {

	/**
	 * 栏目编号
	 */
	private String code;

	/**
	 * 栏目名称
	 */
	private String name;

	/**
	 * 上级分类
	 */
	private Category category;
	
	@Column(columnDefinition = "varchar(64) comment '栏目编号'")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(columnDefinition = "varchar(64) comment '栏目名称'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "p_id", columnDefinition = "varchar(255) comment '上级栏目'")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
