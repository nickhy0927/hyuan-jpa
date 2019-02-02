package com.iss.news.spider.collection.entity;

import com.iss.common.utils.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 采集对象
 * @author Mr'Huang
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_config_label")
public class Collection extends IdEntity {
}
