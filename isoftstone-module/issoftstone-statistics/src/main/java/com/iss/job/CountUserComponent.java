package com.iss.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * 统计用户数量
 * 
 * @author Mr's Huang
 */
@Component
public class CountUserComponent implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("统计用户数量...");
	}
}
