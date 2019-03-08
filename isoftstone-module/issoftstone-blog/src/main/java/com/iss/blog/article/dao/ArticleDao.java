package com.iss.blog.article.dao;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.blog.article.entity.Article;

public interface ArticleDao extends CustomRepostiory<Article,String> {
}