package com.iss.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.iss.common.utils.SysContants.IsDelete;

public class WebUtils {

	public static Map<String, Object> getRequestToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					String[] str = paramName.split("_");
					if (str.length > 1) {
						map.put(paramName, paramValue);
					}
				} else map.put(paramName, paramValue);
				
			}
		}
		map.put("status_eq", IsDelete.NO);
		return map;
	}
	
	/**
	 * 转化为对象map
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getRequestParamterToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				map.put(paramName, paramValue);
			}
		}
		return map;
	}

	/**
	 * 流转换为字符串
	 * @param inputStream
	 * @return
	 */
	public static String convertToString(InputStream inputStream){
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuilder result = new StringBuilder();
		String line = null;
		try {
			while(!(line = bufferedReader.readLine()).equals("")){
				result.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				inputStreamReader.close();
				inputStream.close();
				bufferedReader.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return result.toString();
	}

}
