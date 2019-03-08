package com.iss.news.news.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.news.news.entity.News;
import com.iss.news.news.service.NewsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Service
public class NewsServiceImpl extends BaseCustomService<News, String> implements NewsService {
}
