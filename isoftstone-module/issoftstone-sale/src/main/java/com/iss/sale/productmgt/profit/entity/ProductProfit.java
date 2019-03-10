package com.iss.sale.productmgt.profit.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 产品利润
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_sale_p_product_profit")
public class ProductProfit extends IdEntity {
}
