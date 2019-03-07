package com.iss.constant;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.common.utils.JsonMapper;

/**
 * @author Administrator
 */
public class AccessConstant {

	public final static String DATA_SOURCE_TYPE = "DATA_SOURCE_TYPE";
	
	/**
	 * 是否显示
	 */
	public static class Enable {
		public static String YES = "1";
		public static String NO = "0";

		public static String getName(String code) {
			if (StringUtils.equals(code, YES)) {
				return "启用";
			} else {
				return "停用";
			}
		}
		
		public String getSelectList() {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			Map<String, Object> selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "请选择");
			selectMap.put("value", "");
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "是");
			selectMap.put("value", YES);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "否");
			selectMap.put("value", NO);
			mapList.add(selectMap);
			return new JsonMapper().toJson(selectMap);
		}
	}
	public static class DataBaseType {
		public static Integer MYSQL = 1;
		public static Integer ORACLE = 2;
		public static Integer SQLSERVER = 3;
		
		public static String getName(Integer code) {
			if (code == 1) {
				return "mysql";
			} else if (ORACLE == 2){
				return "ORACLE";
			} else if (ORACLE == 3){
				return "sqlserver";
			}
			return "mysql";
		}
		
		public static String getSelectList() {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			Map<String, Object> selectMap = Maps.newConcurrentMap();
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "mysql");
			selectMap.put("value", MYSQL);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "oracle");
			selectMap.put("value", ORACLE);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "sqlserver");
			selectMap.put("value", SQLSERVER);
			mapList.add(selectMap);
			return new JsonMapper().toJson(mapList);
		}
	}

    /**
     * 是否锁定
     */
	public static class Locked {
		public static String YES = "1";
		public static String NO = "0";
		
		public static String getName(String code) {
			if (StringUtils.equals(code, YES)) {
				return "锁定";
			} else {
				return "解锁";
			}
		}
		public String getSelectList() {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			Map<String, Object> selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "请选择");
			selectMap.put("value", "");
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "是");
			selectMap.put("value", YES);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "否");
			selectMap.put("value", NO);
			mapList.add(selectMap);
			return new JsonMapper().toJson(selectMap);
		}
	}
}
