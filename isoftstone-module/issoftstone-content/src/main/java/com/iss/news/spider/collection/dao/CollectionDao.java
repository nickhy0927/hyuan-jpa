package com.iss.news.spider.collection.dao;

import com.iss.news.spider.collection.entity.Collection;
import com.iss.orm.repository.CustomRepostiory;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionDao extends CustomRepostiory<Collection, String> {
}
