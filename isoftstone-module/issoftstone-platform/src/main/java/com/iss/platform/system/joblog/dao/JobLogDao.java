package com.iss.platform.system.joblog.dao;

import org.springframework.stereotype.Repository;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.system.joblog.entity.JobLog;

@Repository
public interface JobLogDao extends CustomRepostiory<JobLog, String>{

}
