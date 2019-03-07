package com.iss.news.spider.task.service.impl;

import com.iss.news.spider.task.entity.SpiderTask;
import com.iss.news.spider.task.service.SpiderTaskService;
import com.iss.orm.service.impl.BaseCustomService;
import org.springframework.stereotype.Service;

@Service
public class SpiderTaskServiceImpl extends BaseCustomService<SpiderTask, String> implements SpiderTaskService {
}
