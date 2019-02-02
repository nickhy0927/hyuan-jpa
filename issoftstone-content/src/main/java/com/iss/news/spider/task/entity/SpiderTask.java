package com.iss.news.spider.task.entity;

import com.iss.common.utils.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 爬虫任务
 *
 * @author Mr'Huang
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_d_config_task")
public class SpiderTask extends IdEntity {
}
