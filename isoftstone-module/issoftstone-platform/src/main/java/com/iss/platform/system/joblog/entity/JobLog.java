package com.iss.platform.system.joblog.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

@Entity
@Table(name = "t_p_s_joblog")
public class JobLog extends IdEntity {

	private static final long serialVersionUID = 9114417896139199656L;

	// 任务备注
	private String methodDesc;
	// 方法名称
	private String methodName;
	// 执行状态
	private Boolean excuteStatus;
	// 执行开始时间
	private String startTime;
	// 执行结束时间
	private String endTime;
	// 执行持续时间
	private Long lastTime;
	// 执行参数
	private String params;
	// 执行结果/异常
	private String result;

	@Column(columnDefinition = "varchar(200) comment '任务备注'")
	public String getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(String methodDesc) {
		this.methodDesc = methodDesc;
	}

	@Column(columnDefinition = "varchar(256) comment '方法名称'")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(columnDefinition = "int(2) comment '执行状态 true 成功 false 失败'")
	public Boolean getExcuteStatus() {
		return excuteStatus;
	}

	public void setExcuteStatus(Boolean excuteStatus) {
		this.excuteStatus = excuteStatus;
	}

	@Column(columnDefinition = "varchar(32) comment '执行开始时间'")
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(columnDefinition = "varchar(32) comment '执行结束时间'")
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(columnDefinition = "bigint(20) comment '执行持续时间'")
	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	@Column(columnDefinition = "varchar(256) comment '执行参数'")
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Lob
	@Basic
	@Column(columnDefinition = "mediumtext comment '执行结果'")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
