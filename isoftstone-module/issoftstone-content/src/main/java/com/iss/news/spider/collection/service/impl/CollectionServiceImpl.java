package com.iss.news.spider.collection.service.impl;

import com.iss.news.spider.collection.entity.Collection;
import com.iss.news.spider.collection.service.CollectionService;
import com.iss.orm.service.impl.BaseCustomService;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl extends BaseCustomService<Collection, String> implements CollectionService {
}
