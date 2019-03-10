package com.iss.sale.productmgt.product.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import com.baidu.code.anno.Field;
import com.iss.common.utils.GroupOne;
import com.iss.common.utils.IdEntity;
import com.iss.sale.productmgt.producttype.entity.ProductType;

/**
 * 存储公司所有产品目录
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_product")
public class Product extends IdEntity {

	// 产品名称
	@Field(fieldRemark = "产品名称")
	@NotEmpty(message = "产品名称不能空")
	@Size(max = 256, message = "产品名称长度不能超过{max}位", groups = { GroupOne.class })
	private String productName;

	// 产品类别
	@Field(fieldRemark = "产品类别")
	private ProductType productType;

	// 成本价格
	@Field(fieldRemark = "成本价格")
	@NumberFormat(pattern = "#,###,###.#")
	@DecimalMin(message = "成本价格不能小于0", value = "0", groups = { GroupOne.class })
	private BigDecimal productCost;

	// 备注
	@Size(max = 512, message = "备注长度不能超过{max}位")
	@Field(fieldRemark = "备注")
	private String remark;

	// 辅助字段
	private String productTypeName;
	private String productTypeId;

	@Column(columnDefinition = "varchar(256) comment '产品名称'")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@ManyToOne
	@JoinColumn(name = "product_type_id", columnDefinition = "varchar(64) comment '产品类别'")
	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	@Column(columnDefinition = "decimal(20,2) comment '成本价格'")
	public BigDecimal getProductCost() {
		return productCost;
	}

	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}

	@Column(columnDefinition = "varchar(512) comment '备注'")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Transient
	public String getProductTypeName() {
		if (productType != null) {
			productTypeName = productType.getName();
		}
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	@Transient
	public String getProductTypeId() {
		if (productType != null) {
			productTypeId = productType.getId();
		}
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
}
