package com.iss.news;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DeployConstanst {

	// 数据源类型
	public static final String SOURCETYPE = "SOURCE_TYPE";

	// 数据来源类型
	public static final String ORIGINTYPE = "ORIGIN_TYPE";

	// 请求方式
	public static final String REQUESTTYPE = "REQUEST_TYPE";

	public static class ActionType {
		public static final Integer HTML = 0;
		public static final Integer JSON = 1;
		public static final Integer XML = 2;

		public static String getName(Integer code) {
			String name = "";
			if (code == HTML) {
				name = "html方式";
			} else if (code == JSON) {
				name = "JSON方式";
			} else if (code == XML) {
				name = "XML方式";
			}
			return name;
		}

		public static String getJSON() {
			List<Map<String, Object>> maps = Lists.newArrayList();
			Map<String, Object> map = Maps.newConcurrentMap();
			map.put("value", HTML);
			map.put("text", getName(HTML));
			maps.add(map);
			map = Maps.newConcurrentMap();
			map.put("value", JSON);
			map.put("text", getName(JSON));
			maps.add(map);
			map = Maps.newConcurrentMap();
			map.put("value", XML);
			map.put("text", getName(XML));
			maps.add(map);
			return com.alibaba.fastjson.JSON.toJSONString(maps);

		}
	}
}
