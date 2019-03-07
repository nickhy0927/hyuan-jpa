package com.iss.news.category.service.impl;

import org.springframework.stereotype.Service;

import com.iss.news.category.entity.Category;
import com.iss.news.category.service.CategoryService;
import com.iss.orm.service.impl.BaseCustomService;

@Service
public class CategoryServiceImpl extends BaseCustomService<Category, String> implements CategoryService {

}
