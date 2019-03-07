package com.iss.blog.tag.service;

import com.iss.blog.tag.entity.Tags;
import com.iss.orm.service.CustomService;

/**
 * @author Administrator
 *
 */
public interface TagsService extends CustomService<Tags, String> {

	/**
	 * 根据名字查询标签
	 * 
	 * @param name 标签名称
	 * @return com.iss.blog.tag.entity.Tags
	 */
	Tags queryTagsByTag(String tag);
}
