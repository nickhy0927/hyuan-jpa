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
