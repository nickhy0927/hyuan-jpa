package com.iss.platform.access.dict.dao;

import org.springframework.stereotype.Repository;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.access.dict.entity.Dict;

@Repository
public interface DictDao extends CustomRepostiory<Dict, String> {

}
