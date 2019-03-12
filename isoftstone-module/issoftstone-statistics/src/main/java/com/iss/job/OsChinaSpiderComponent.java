package com.iss.job;

import com.iss.common.spring.SpringContextHolder;
import com.iss.job.spider.OschainService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

/**
 * @author Hyuan
 */
@Component
public class OsChinaSpiderComponent implements Job {

    private static OschainService oschainService;

    static {
        oschainService = SpringContextHolder.getBean(OschainService.class);
    }
    @Override
    public void execute(JobExecutionContext context) {
        oschainService.init();
    }
}
