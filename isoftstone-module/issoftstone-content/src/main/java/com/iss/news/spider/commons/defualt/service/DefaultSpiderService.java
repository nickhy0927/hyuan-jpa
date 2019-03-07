package com.iss.news.spider.commons.defualt.service;

import java.util.List;
import java.util.Map;

import com.iss.news.spider.task.entity.SpiderTask;

public interface DefaultSpiderService {

	/**
	 * 查询所有的任务
	 * @param spiderTask
	 * @return
	 */
	List<SpiderTask> querySpiderTaskList(SpiderTask spiderTask);
	
	/**
	 * 开始爬取数据
	 * @param spiderTasks
	 * @return
	 */
	List<Map<String, Object>> startSpider(List<SpiderTask> spiderTasks);
	
	/**
	 * 处理最后的结果
	 * @param results
	 */
	void dealResult(List<Map<String, Object>> results);
	
}
