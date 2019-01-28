package com.iss.news.content.dao;

import org.springframework.stereotype.Repository;

import com.iss.news.content.entity.News;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface NewsDao extends CustomRepostiory<News, String> {

}
