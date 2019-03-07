package com.iss.news.spider.commons.defualt.excute;

import java.util.List;
import java.util.Map;

import com.iss.news.spider.commons.defualt.service.DefaultSpiderService;
import com.iss.news.spider.task.entity.SpiderTask;

/**
 * 开始爬取类
 * @author Administrator
 *
 */
public class ExcuteSpider {
	
	public static void excute(SpiderTask spiderTask, DefaultSpiderService spiderService) {
		List<SpiderTask> spiderTasks = spiderService.querySpiderTaskList(spiderTask);
		List<Map<String, Object>> resultList = spiderService.startSpider(spiderTasks);
		spiderService.dealResult(resultList);
	}
}
