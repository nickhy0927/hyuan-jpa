package com.iss.blog.classification.dao;

import org.springframework.stereotype.Repository;

import com.iss.blog.classification.entity.Classification;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface ClassificationDao extends CustomRepostiory<Classification, String> {

}
