package com.iss.sale.productmgt.storereason.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.baidu.code.anno.Field;
import com.iss.common.utils.IdEntity;

/**
 * 出/入库理由
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_store_reason")
public class StoreReason extends IdEntity {

	// 理由描述
	@Field(fieldRemark = "理由描述")
	private String reasonType;

	// 备注
	@Field(fieldRemark = "备注")
	private String remark;

	@Column(columnDefinition = "varchar(256) comment '理由描述'")
	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	@Column(columnDefinition = "varchar(512) comment '备注'")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
