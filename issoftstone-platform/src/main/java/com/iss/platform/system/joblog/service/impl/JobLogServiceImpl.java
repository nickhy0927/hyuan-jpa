package com.iss.platform.system.joblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.common.exception.ServiceException;
import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.system.joblog.dao.JobLogDao;
import com.iss.platform.system.joblog.entity.JobLog;
import com.iss.platform.system.joblog.service.JobLogService;

@Service
public class JobLogServiceImpl extends BaseCustomService<JobLog, String> implements JobLogService {

	@Autowired
	private JobLogDao jobLogDao;
	
	@Override
	@Transactional(readOnly = false)
	public JobLog saveEntity(JobLog entity) throws ServiceException {
		return jobLogDao.save(entity);
	}
}
