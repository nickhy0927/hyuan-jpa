package com.iss.news.spider.source.dao;

import com.iss.news.spider.source.entity.DataSource;
import com.iss.orm.repository.CustomRepostiory;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSourceDao extends CustomRepostiory<DataSource, String> {
}
