package com.iss.common.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class MessageObject<T> {

	private int status;
	private String message;
	private Object object;

	private MessageObject() {
	}

	public static <T> MessageObject<T> getDefaultInstance() {
		return new MessageObject<>();
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public static class ResultCode {
		public static int SUCCESS = 0;
		public static int UNAUTH = 201;
		public static int FAILIAR = 403;

	}

	public void error(String errMsg) {
		this.message = errMsg;
		this.status = ResultCode.FAILIAR;
	}

	public void unauth() {
		this.message = "你没有权限访问，详情请联系管理员";
		this.status = ResultCode.UNAUTH;
	}

	public void ok(String successMsg) {
		this.message = successMsg;
		this.status = ResultCode.SUCCESS;
	}

	public void ok(String successMsg, Object object) {
		this.message = successMsg;
		this.status = ResultCode.SUCCESS;
		this.object = object;
	}

	public String toJson(MessageObject<T> messageObject) {
		return new JsonMapper().toJson(messageObject);
	}

	public void returnData(HttpServletResponse response, MessageObject<T> messageObject) throws IOException {
		// 这句话的意思，是让浏览器用utf8来解析返回的数据
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		// 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		if (writer != null) {
			writer.write(toJson(messageObject));
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
}
