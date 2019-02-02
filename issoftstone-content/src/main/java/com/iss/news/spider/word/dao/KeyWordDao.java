package com.iss.news.spider.word.dao;

import com.iss.news.spider.word.entity.KeyWord;
import com.iss.orm.repository.CustomRepostiory;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyWordDao extends CustomRepostiory<KeyWord, String> {
}
