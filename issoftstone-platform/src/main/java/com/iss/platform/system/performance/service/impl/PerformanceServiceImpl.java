package com.iss.platform.system.performance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iss.orm.service.impl.BaseCustomService;
import com.iss.platform.system.performance.dao.PerformanceDao;
import com.iss.platform.system.performance.entity.Performance;
import com.iss.platform.system.performance.service.PerformanceService;

@Service
@Transactional(readOnly = true)
public class PerformanceServiceImpl extends BaseCustomService<Performance, String> implements PerformanceService {

	@Autowired
	private PerformanceDao performanceDao;

	@Override
	public Long queryAccessList(String alias, String createDate) {
		return performanceDao.queryAccessList(alias, createDate);
	}

	@Override
	public List<String> queryPerformanceGroup() {
		return performanceDao.queryPerformanceGroup();
	}
	
	@Override
	public String queryPerformanceByalias(String alias) {
		List<Performance> list = performanceDao.queryPerformanceByalias(alias);
		return list != null && list.size() > 0 ? list.get(0).getName() : "";
	}
}