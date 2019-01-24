package com.iss.common.exception;

@SuppressWarnings("serial")
public class ServiceException extends DaoException {

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String exceptionMsg, Exception e) {
		super(exceptionMsg, e);
	}
	
	public ServiceException(String exceptionCode, String exceptionMsg, Exception e) {
		super(exceptionCode, exceptionMsg, e);
	}


	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
}
