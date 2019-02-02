package com.iss.news.spider.task.dao;

import com.iss.news.spider.task.entity.SpiderTask;
import com.iss.orm.repository.CustomRepostiory;
import org.springframework.stereotype.Repository;

@Repository
public interface SpiderTaskDao extends CustomRepostiory<SpiderTask, String> {
}
