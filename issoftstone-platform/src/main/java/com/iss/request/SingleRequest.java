package com.iss.request;

import javax.servlet.http.HttpServletRequest;

public class SingleRequest {

	private final static ThreadLocal<HttpServletRequest> threadLocalRequest = new ThreadLocal<>();

	public static void set(HttpServletRequest request) {
		threadLocalRequest.set(request);
	}

	public static HttpServletRequest get() {
		HttpServletRequest request = threadLocalRequest.get();
		return request;
	}

	public static void remove() {
		threadLocalRequest.remove();
	}
}
