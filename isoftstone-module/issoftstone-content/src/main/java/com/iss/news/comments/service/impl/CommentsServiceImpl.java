package com.iss.news.comments.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.news.comments.entity.Comments;
import com.iss.news.comments.service.CommentsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Service
public class CommentsServiceImpl extends BaseCustomService<Comments, String> implements CommentsService {
}
