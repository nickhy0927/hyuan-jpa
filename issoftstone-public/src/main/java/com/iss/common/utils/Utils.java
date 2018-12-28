package com.iss.common.utils;

public class Utils {
	public static String BLOCK = "__jsp_override__";

	public static String getOverrideVariableName(String name) {
		return BLOCK + name;
	}
}
