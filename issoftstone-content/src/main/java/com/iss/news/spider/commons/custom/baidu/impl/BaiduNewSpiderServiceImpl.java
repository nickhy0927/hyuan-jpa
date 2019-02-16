package com.iss.news.spider.commons.custom.baidu.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.iss.news.spider.commons.custom.baidu.BaiduNewSpiderService;
import com.iss.news.spider.task.entity.SpiderTask;

@Service
public class BaiduNewSpiderServiceImpl implements BaiduNewSpiderService {

	@Override
	public List<SpiderTask> querySpiderTaskList(SpiderTask spiderTask) {
		return null;
	}

	@Override
	public List<Map<String, Object>> startSpider(List<SpiderTask> spiderTasks) {
		return null;
	}

	@Override
	public void dealResult(List<Map<String, Object>> results) {

	}
}
