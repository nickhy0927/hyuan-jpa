package com.iss.news.comments.service.impl;

import org.springframework.stereotype.Service;

import com.iss.news.comments.entity.Comments;
import com.iss.news.comments.service.CommentService;
import com.iss.orm.service.impl.BaseCustomService;

@Service
public class CommentServiceImpl extends BaseCustomService<Comments, String> implements CommentService {

}
