package com.iss.constant;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.common.utils.JsonMapper;

public class AccessConstant {

	// 是否显示
	public static class Enable {
		public static Boolean _YES = Boolean.TRUE;
		public static Boolean _NO = Boolean.FALSE;

		public static String getName(Boolean code) {
			if (code) return "是";
			else return "否";
		}
		
		public String getSelectList() {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			Map<String, Object> selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "请选择");
			selectMap.put("value", "");
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "是");
			selectMap.put("value", _YES);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "否");
			selectMap.put("value", _NO);
			mapList.add(selectMap);
			return new JsonMapper().toJson(selectMap);
		}
	}
	
	// 是否锁定
	public static class Locked {
		public static Boolean _YES = Boolean.TRUE;
		public static Boolean _NO = Boolean.FALSE;
		
		public static String getName(Boolean code) {
			if (code) return "是";
			else return "否";
		}
		public String getSelectList() {
			List<Map<String, Object>> mapList = Lists.newArrayList();
			Map<String, Object> selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "请选择");
			selectMap.put("value", "");
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "是");
			selectMap.put("value", _YES);
			mapList.add(selectMap);
			selectMap = Maps.newConcurrentMap();
			selectMap.put("name", "否");
			selectMap.put("value", _NO);
			mapList.add(selectMap);
			return new JsonMapper().toJson(selectMap);
		}
	}
}
