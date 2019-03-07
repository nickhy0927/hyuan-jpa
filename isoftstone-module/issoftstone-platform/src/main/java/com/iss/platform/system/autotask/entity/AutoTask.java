package com.iss.platform.system.autotask.entity;

import com.iss.common.utils.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统任务配置
 */
@Entity
@SuppressWarnings("serial")
@Table(name = "t_p_s_autotask")
public class AutoTask extends IdEntity {

	/**
	 * 自动任务名称
	 */
	private String taskName;

	/**
	 * 任务类名
	 */
	private String className;

	/**
	 * 任务表达式
	 */
	private String scheduler;

	/**
	 * 是否运行
	 */
	private Integer startStatus;

	/**
	 * 任务描述
	 */
	private String remarks;

	@Column(columnDefinition = "varchar(64) comment '自动任务名称'")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(columnDefinition = "varchar(256) comment '任务类名'")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(columnDefinition = "varchar(100) comment '任务表达式'")
	public String getScheduler() {
		return scheduler;
	}

	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	@Column(columnDefinition = "int(2) comment '是否运行'")
	public Integer getStartStatus() {
		return startStatus;
	}

	public void setStartStatus(Integer startStatus) {
		this.startStatus = startStatus;
	}
	
	@Column(columnDefinition = "varchar(256) comment '任务描述'")
	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
