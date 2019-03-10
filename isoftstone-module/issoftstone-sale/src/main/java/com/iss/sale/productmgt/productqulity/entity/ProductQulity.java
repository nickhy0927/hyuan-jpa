package com.iss.sale.productmgt.productqulity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.baidu.code.anno.Field;
import com.iss.common.utils.IdEntity;

/**
 * 产品质量
 * 相关表：产品出/入库、定单
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_product_qulity")
public class ProductQulity extends IdEntity {

	// 质量等级
	@Field(fieldRemark = "质量等级")
	private String qulityType;

	// 备注
	@Field(fieldRemark = "备注")
	private String remark;
	
	@Column(columnDefinition = "varchar(256) comment '质量等级'")
	public String getQulityType() {
		return qulityType;
	}

	public void setQulityType(String qulityType) {
		this.qulityType = qulityType;
	}
	
	@Column(columnDefinition = "varchar(512) comment '备注'")
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
