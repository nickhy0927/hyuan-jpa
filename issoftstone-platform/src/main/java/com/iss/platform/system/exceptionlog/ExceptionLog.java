package com.iss.platform.system.exceptionlog;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.iss.common.utils.IdEntity;

/**
 * 异常日志
 */
@Entity
@Table(name = "t_p_s_exce_log")
public class ExceptionLog extends IdEntity {

	private String methodName; // 方法名称
	private String exceMsg;// 异常信息
	private String message;// 完整的异常信息
	private Integer exceptionType; // 异常信息类型

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getExceMsg() {
		return exceMsg;
	}

	public void setExceMsg(String exceMsg) {
		this.exceMsg = exceMsg;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(Integer exceptionType) {
		this.exceptionType = exceptionType;
	}
}
