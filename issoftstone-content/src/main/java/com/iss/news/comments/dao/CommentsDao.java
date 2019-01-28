package com.iss.news.comments.dao;

import org.springframework.stereotype.Repository;

import com.iss.news.comments.entity.Comments;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface CommentsDao extends CustomRepostiory<Comments, String> {

}
