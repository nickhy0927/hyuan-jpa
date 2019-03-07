package com.iss.common.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Maps;


public class AjaxResult<T> {

	private int code;
	private String message;
	private long total;
	private Map<String, Object> rows = Maps.newConcurrentMap();
	private Object content;

	private AjaxResult() {
	}

	public static <T> AjaxResult<T> getDefaultInstance() {
		return new AjaxResult<T>();
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static class ResultCode {
		public static int SUCCESS = 0;
		public static int SAVE_SUCCESS = 1;
		public static int FAILIAR = 2;
		public static int UNAUTH = 201;

	}

	public void error(String errMsg) {
		this.message = errMsg;
		this.code = ResultCode.FAILIAR;
	}

	public void unauth() {
		this.message = "你没有权限访问，详情请联系管理员";
		this.code = ResultCode.UNAUTH;
	}

	public void ok(String successMsg) {
		this.message = successMsg;
		this.code = ResultCode.SUCCESS;
	}

	public void openTip(String successMsg, Object data) {
		this.message = successMsg;
		this.code = ResultCode.SAVE_SUCCESS;
		this.content = data;
	}
	
	public void openTip(String successMsg) {
		this.message = successMsg;
		this.code = ResultCode.SAVE_SUCCESS;
	}
	
	public void ok(String successMsg, Object data) {
		this.message = successMsg;
		this.code = ResultCode.SUCCESS;
		this.content = data;
	}
	
	public void ok(String successMsg, PagerInfo<T> data) {
		this.message = successMsg;
		this.code = ResultCode.SUCCESS;
		this.total = data.getTotals();
		rows.put("item", data.getContent());
		rows.put("pageInfo", data);
	}

	public long getTotal() {
		return total;
	}

	public String toJson(AjaxResult<T> messageObject) {
		return new JsonMapper().toJson(messageObject);
	}

	public void returnData(HttpServletResponse response, AjaxResult<T> messageObject) throws IOException {
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
	
	public Map<String, Object> getRows() {
		return rows;
	}
	
	public Object getContent() {
		return content;
	}
}
