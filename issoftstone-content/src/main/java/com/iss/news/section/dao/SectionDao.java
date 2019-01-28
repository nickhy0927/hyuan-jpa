package com.iss.news.section.dao;

import org.springframework.stereotype.Repository;

import com.iss.news.section.entity.Section;
import com.iss.orm.repository.CustomRepostiory;

@Repository
public interface SectionDao extends CustomRepostiory<Section, String> {

}
