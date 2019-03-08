package com.iss.blog.tag.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.blog.tag.dao.TagsDao;
import com.iss.blog.tag.entity.Tags;
import com.iss.blog.tag.service.TagsService;
import com.iss.orm.service.impl.BaseCustomService;

@Service
public class TagsServiceImpl extends BaseCustomService<Tags, String> implements TagsService {
	
	@Autowired
	private TagsDao tagsDao;
	
	@Override
	@Transactional(readOnly = true)
	public Tags queryTagsByTag(String tag) {
		return tagsDao.queryTagsByTag(tag);
	}
}
