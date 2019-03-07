package com.iss.constant;

import java.util.Map;

import com.google.common.collect.Maps;

import us.codecraft.webmagic.Spider;

public class SpiderManager {

	private static final Map<String, Spider> spiderMap = Maps.newConcurrentMap();
	
	public static Spider getSpidermap(Object object) {
		return spiderMap.get(object.getClass().getSimpleName().toUpperCase());
	}
	public static void removeSpider(Object object) {
		Spider spider = spiderMap.get(object.getClass().getSimpleName().toUpperCase());
		if (spider !=  null) {
			spider.stop();
		}
	}

	public static void setSpiderMap(Object object, Spider spider) {
		spiderMap.put(object.getClass().getSimpleName().toUpperCase(), spider);
	}
}
