package com.iss.news.spider.word.service.impl;

import com.iss.news.spider.word.entity.KeyWord;
import com.iss.news.spider.word.service.KeyWordService;
import com.iss.orm.service.impl.BaseCustomService;
import org.springframework.stereotype.Service;

@Service
public class KeyWordServiceImpl extends BaseCustomService<KeyWord, String> implements KeyWordService {
}
