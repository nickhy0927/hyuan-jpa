package com.iss.sale.productmgt.producttype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.baidu.code.anno.Field;
import com.iss.common.utils.IdEntity;

/**
 * 产品类别：对表一的产品进行分类，并以其进行约束
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_product_type")
public class ProductType extends IdEntity {

	// 产品类别名称
	@Field(fieldRemark = "类别名称")
	@NotEmpty(message = "产品类别名称不能空")
	@Size(max = 128, message = "产品类别名称长度不能超过{max}位")
	private String name;

	// 备注
	@Field(fieldRemark = "备注")
	@Size(max = 512, message = "备注长度不能超过{max}位")
	private String remark;

	@Column(columnDefinition = "varchar(128) comment '类别名称'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(columnDefinition = "varchar(512) comment '备注'")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
