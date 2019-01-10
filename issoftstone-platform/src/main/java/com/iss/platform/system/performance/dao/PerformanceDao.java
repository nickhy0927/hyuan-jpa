package com.iss.platform.system.performance.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.iss.orm.repository.CustomRepostiory;
import com.iss.platform.system.performance.entity.Performance;

public interface PerformanceDao extends CustomRepostiory<Performance, String>{

	@Query("select distinct p.alias from Performance p")
	List<String> queryPerformanceGroup();
	
	@Query("select p from Performance p where p.alias = ?1")
	List<Performance> queryPerformanceByalias(String alias);
	
	@Query("select AVG(p.executeTime) from Performance p where p.alias = ?1 and p.createTime like ?2")
	Long queryAccessList(String alias, String time);
}
