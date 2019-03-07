package com.iss.news.section.service.impl;

import org.springframework.stereotype.Service;

import com.iss.news.section.entity.Section;
import com.iss.news.section.service.SectionService;
import com.iss.orm.service.impl.BaseCustomService;

@Service
public class SectionServiceImpl extends BaseCustomService<Section, String> implements SectionService {

}
