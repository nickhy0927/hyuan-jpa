package com.iss.news.category.dao;

import org.springframework.stereotype.Repository;

import com.iss.news.category.entity.Category;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface CategoryDao extends CustomRepostiory<Category, String>{

}
