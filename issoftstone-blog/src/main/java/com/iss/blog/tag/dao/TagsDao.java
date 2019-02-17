/**
 * 
 */
package com.iss.blog.tag.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.blog.tag.entity.Tags;
import com.iss.orm.repository.CustomRepostiory;

/**
 * 博客标签
 * 
 * @author Mr's Huang
 *
 */
@Repository
public interface TagsDao extends CustomRepostiory<Tags, String> {
	
	@Query("select t from Tags t where t.tag = ?1 and t.status = 0")
	Tags queryTagsByTag(String tag);
}
