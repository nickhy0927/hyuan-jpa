package com.iss.platform.system.joblog.service.impl;

import org.springframework.stereotype.Service;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.system.joblog.entity.JobLog;
import com.iss.platform.system.joblog.service.JobLogService;

@Service
public class JobLogServiceImpl extends BaseCustomService<JobLog, String> implements JobLogService {

}
