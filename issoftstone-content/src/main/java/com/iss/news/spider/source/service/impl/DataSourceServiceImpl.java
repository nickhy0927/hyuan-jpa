package com.iss.news.spider.source.service.impl;

import com.iss.news.spider.source.entity.DataSource;
import com.iss.news.spider.source.service.DataSourceService;
import com.iss.orm.service.impl.BaseCustomService;
import org.springframework.stereotype.Service;

@Service
public class DataSourceServiceImpl extends BaseCustomService<DataSource, String> implements DataSourceService {
}
