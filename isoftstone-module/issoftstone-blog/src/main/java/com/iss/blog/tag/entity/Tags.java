package com.iss.blog.tag.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_b_tags")
public class Tags extends IdEntity {
	private static final long serialVersionUID = 1L;

	private String tag;

	private String remarks;

	public Tags() {
		super();
	}

	public Tags(String tag, String remarks, Boolean status) {
		super();
		this.tag = tag;
		this.status = status;
		this.remarks = remarks;
	}

	@Column(columnDefinition = "varchar(255) comment '博客标签'")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Column(columnDefinition = "varchar(255) comment '博客标签备注'")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Tags [tag=" + tag + ", remarks=" + remarks + "]";
	}
}
