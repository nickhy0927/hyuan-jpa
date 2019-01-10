package com.iss.platform.system.performance.service;

import java.util.List;

import com.iss.orm.service.CustomService;
import com.iss.platform.system.performance.entity.Performance;

public interface PerformanceService extends CustomService<Performance, String> {
	
	List<String> queryPerformanceGroup();

	Long queryAccessList(String alias, String time);
	
	List<Performance> queryPerformanceByalias(String alias);
}
