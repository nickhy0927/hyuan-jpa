package com.iss.common.exception;

@SuppressWarnings("serial")
public class VerifyCodeException extends Exception {

	public VerifyCodeException() {
	}

	public VerifyCodeException(String message) {
		super(message);
	}

	public VerifyCodeException(Throwable cause) {
		super(cause);
	}

	public VerifyCodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerifyCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
