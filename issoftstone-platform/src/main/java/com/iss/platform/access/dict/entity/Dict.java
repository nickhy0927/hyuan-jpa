package com.iss.platform.access.dict.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iss.common.utils.IdEntity;

/**
 * 数据字典
 * @author Mr'Huang
 */
@Entity
@Table(name = "t_p_a_dict")
@SuppressWarnings("serial")
public class Dict extends IdEntity {

	/**
	 * 字典编号
	 */
	private String dictCode;
	
	/**
	 * 字典名称
	 */
	private String dictName;
	
	/**
	 * 字典类型
	 */
	private String dictType;
	
	/**
	 * 字典值
	 */
	private String dictValue;
	
	/**
	 * 是否启用 true 启用 false 停用
	 */
	private Boolean enable;
	
	/**
	 * 上级名称
	 */
	private Dict dict;
	
	/**
	 * 字典描述
	 */
	private String remarks;
	
	// 辅助字段
	/**
	 * 字典类型
	 */
	private String dictTypeName;
	
	/**
	 * 上级字典ID
	 */
	private String pid;

	@Column(columnDefinition = "varchar(64) comment '字典编号'")
	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	@Column(columnDefinition = "varchar(64) comment '字典名称'")
	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	@Column(columnDefinition = "varchar(64) comment '字典类型'")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	@Column(columnDefinition = "varchar(64) comment '字典值'")
	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	@Column(columnDefinition = "bit(1) comment '是否启用 0 停用 1 启用'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "varchar(255) comment '备注'")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@ManyToOne
	@JoinColumn(name = "parent_id", columnDefinition = "varchar(64) comment '字典ID'")
	public Dict getDict() {
		return dict;
	}
	
	public void setDict(Dict dict) {
		this.dict = dict;
	}
	
	@Transient
	public String getDictTypeName() {
		return dictTypeName;
	}
	
	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}
	
	@Transient
	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
}
