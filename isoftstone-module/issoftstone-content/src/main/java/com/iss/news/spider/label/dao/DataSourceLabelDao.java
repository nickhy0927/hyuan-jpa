package com.iss.news.spider.label.dao;

import org.springframework.stereotype.Repository;

import com.iss.news.spider.label.entity.DataSourceLabel;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface DataSourceLabelDao extends CustomRepostiory<DataSourceLabel, String> {

}
