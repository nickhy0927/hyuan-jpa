package com.iss.sale.productmgt.store.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.baidu.code.anno.Field;
import com.iss.common.utils.IdEntity;
import com.iss.sale.productmgt.product.entity.Product;
import com.iss.sale.productmgt.productqulity.entity.ProductQulity;

/**
 * 产品出/入库：指检验合同的成品/退货产品，
 * 生产过程中的待检品、半成品以及材料等不纳入本系统
 * 相关表：产品（产品编号）、产品质量 
 * 说　明：除“序号”、“库存状态”、“库存数量”外均由用户输入（选择日期、理由、质量等级）；
 * reason_id不作为本表的外键，从而允许用户输入其他原因，
 * 并提示用户规范该字典库（“库存数量”在用户登记时由系统计算并自动填写）。
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_store")
public class Store extends IdEntity {

	// 产品
	@Field(fieldRemark = "产品名称")
	private Product product;

	// 产品批号
	@Field(fieldRemark = "产品编号")
	private String productCode;

	// “1”为入存，“0”为出库
	@Field(fieldRemark = "登记方式")
	private Integer enterStatue;

	// 登记数量
	@Field(fieldRemark = "登记数量")
	private Integer changNumber;

	// 质量等级
	@Field(fieldRemark = "质量等级")
	private ProductQulity productQulity;

	// 出/入库日期
	@Field(fieldRemark = "出/入库日期")
	private Date storeDate;

	// 来源/去向
	@Field(fieldRemark = "来源/去向")
	private String storeFrorto;

	// 出/入库理由
	@Field(fieldRemark = "出/入库理由")
	private String storeReason;

	// 库存数量
	@Field(fieldRemark = "库存数量")
	private Integer storeNumber;
	
	// 备注
	@Field(fieldRemark = "备注")
	private String remark;

	@ManyToOne
	@JoinColumn(name = "product_id", columnDefinition = "varchar(64) comment '产品ID'")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Column( columnDefinition = "varchar(64) comment '产品编号'")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(columnDefinition = "int(3) comment '登记方式'")
	public Integer getEnterStatue() {
		return enterStatue;
	}

	public void setEnterStatue(Integer enterStatue) {
		this.enterStatue = enterStatue;
	}

	@Column(columnDefinition = "int comment '登记数量'")
	public Integer getChangNumber() {
		return changNumber;
	}

	public void setChangNumber(Integer changNumber) {
		this.changNumber = changNumber;
	}

	@ManyToOne
	@JoinColumn(name = "product_qulity_id", columnDefinition = "varchar(64) comment '质量等级'")
	public ProductQulity getProductQulity() {
		return productQulity;
	}

	public void setProductQulity(ProductQulity productQulity) {
		this.productQulity = productQulity;
	}

	@Column(columnDefinition = "datetime comment '出/入库日期'")
	public Date getStoreDate() {
		return storeDate;
	}

	public void setStoreDate(Date storeDate) {
		this.storeDate = storeDate;
	}

	@Column(columnDefinition = "varchar(64) comment '来源/去向'")
	public String getStoreFrorto() {
		return storeFrorto;
	}

	public void setStoreFrorto(String storeFrorto) {
		this.storeFrorto = storeFrorto;
	}

	@Column(columnDefinition = "varchar(256) comment '出/入库理由'")
	public String getStoreReason() {
		return storeReason;
	}

	public void setStoreReason(String storeReason) {
		this.storeReason = storeReason;
	}

	@Column(columnDefinition = "int comment '库存数量'")
	public Integer getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(Integer storeNumber) {
		this.storeNumber = storeNumber;
	}
	
	@Column(columnDefinition = "varchar(512) comment '备注'")
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
