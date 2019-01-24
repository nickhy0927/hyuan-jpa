package com.iss.common.exception;

@SuppressWarnings("serial")
public class DaoException extends RuntimeException {
	private String exceptionCode;

	private String exceptionMsg;

	public DaoException() {
		super();
	}
	public DaoException(String exceptionCode, String exceptionMsg, Exception exception) {
		super(exception.getMessage());
		this.exceptionCode = exceptionCode;
		this.exceptionMsg = exceptionMsg;
	}
	
	public DaoException(String exceptionMsg, Exception exception) {
		super(exception.getMessage());
		this.exceptionMsg = exceptionMsg;
	}

	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	
}
