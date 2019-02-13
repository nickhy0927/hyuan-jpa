package com.iss.constant;

import com.iss.common.exception.ServiceException;

public class DataType {

	public static class ExceptionType {
		public static Integer SYSTEM = 1;
		public static Integer BUSINESS = 2;

		public static Integer getCode(Exception exception) {
			if (exception instanceof NullPointerException) {
				return SYSTEM;
			} else if (exception instanceof RuntimeException) {
				return BUSINESS;
			} else if (exception instanceof ServiceException) {
				return BUSINESS;
			}
			return SYSTEM;
		}

		public static String getName(Integer code) {
			if (code == SYSTEM) {
				return "系统异常";
			} else if (code == BUSINESS) {
				return "业务异常";
			}
			return "";
		}
	}

	public static class OptType {
		public static final int INSERT = 1;
		public static final int UPDATE = 2;
		public static final int DELETE = 3;
		public static final int QUERY = 4;
		public static final int EXCUTE = 5;
	}
}
