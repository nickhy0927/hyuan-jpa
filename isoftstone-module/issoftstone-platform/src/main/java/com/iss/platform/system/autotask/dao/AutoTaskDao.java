package com.iss.platform.system.autotask.dao;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.system.autotask.entity.AutoTask;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoTaskDao extends CustomRepostiory<AutoTask, String> {
}
