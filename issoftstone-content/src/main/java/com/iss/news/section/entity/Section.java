package com.iss.news.section.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_d_section")
public class Section extends IdEntity{

	/**
	 * 版块名称
	 */
	private String sectionName; 
	
	/**
	 * 版块描述
	 */
	private String remarks;

	@Column(columnDefinition = "varchar(32) comment '版块名称'")
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Column(columnDefinition = "varchar(255) comment '版块描述'")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 
	
}
