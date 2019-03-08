package com.iss.blog.article.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.blog.article.entity.Article;
import com.iss.blog.article.service.ArticleService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Service
public class ArticleServiceImpl extends BaseCustomService<Article, String> implements ArticleService {
}
