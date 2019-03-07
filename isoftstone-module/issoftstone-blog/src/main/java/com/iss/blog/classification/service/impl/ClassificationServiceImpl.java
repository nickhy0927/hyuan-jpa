package com.iss.blog.classification.service.impl;

import org.springframework.stereotype.Service;

import com.iss.blog.classification.entity.Classification;
import com.iss.blog.classification.service.ClassificationService;
import com.iss.orm.service.impl.BaseCustomService;

@Service
public class ClassificationServiceImpl extends BaseCustomService<Classification, String> implements ClassificationService {

}
