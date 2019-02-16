package com.iss.news.spider.commons.defualt.service.impl;

import java.util.List;
import java.util.Map;

import com.iss.news.spider.commons.defualt.service.DefaultSpiderService;
import com.iss.news.spider.task.entity.SpiderTask;

public abstract class DefaultSpiderServiceImpl implements DefaultSpiderService {

	@Override
	public List<Map<String, Object>> startSpider(List<SpiderTask> spiderTasks) {
		return null;
	}
}
