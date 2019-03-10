package com.iss.sale.productmgt.productperiods.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baidu.code.anno.Field;
import com.iss.common.utils.IdEntity;
import com.iss.sale.productmgt.product.entity.Product;

/**
 * 产品生产周期：主要用于合同评审
 * 相关表：产品（编号）
 * 说　明：均由用户输入，所有在产品都必须在此登记，并作为在产的标志。
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_product_periods")
public class ProductPeriods extends IdEntity {

	// 产品名称
	@Field(fieldRemark = "产品名称")
	private Product product;
	
	// 产品数量
	@Field(fieldRemark = "产品数量")
	private Integer productNumber;
	
	// 一般生产周期 以天计
	@Field(fieldRemark = "生产周期(天)")
	private Integer periods;

	@ManyToOne
	@JoinColumn(name = "product_id", columnDefinition = "varchar(64) comment '产品ID'")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column(columnDefinition = "int comment '产品数量'")
	public Integer getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Integer productNumber) {
		this.productNumber = productNumber;
	}

	@Column(columnDefinition = "int comment '一般生产周期'")
	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
}
