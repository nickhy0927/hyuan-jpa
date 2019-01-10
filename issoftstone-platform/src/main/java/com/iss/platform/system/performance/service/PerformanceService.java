package com.iss.platform.system.performance.service;

import java.util.List;

import com.iss.orm.service.CustomService;
import com.iss.platform.system.performance.entity.Performance;

public interface PerformanceService extends CustomService<Performance, String> {
	
	List<String> queryPerformanceGroup();
	
	String queryPerformanceByalias(String alias);
	
	Long queryAccessList(String alias, String createDate);
}
