package com.iss.news.category.service.impl;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.news.category.entity.Category;
import com.iss.news.category.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Curtain
 * @date 2015/9/21
 */
@Service
public class CategoryServiceImpl extends BaseCustomService<Category, String> implements CategoryService {
}
