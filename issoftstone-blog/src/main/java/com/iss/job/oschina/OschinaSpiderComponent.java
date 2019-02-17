package com.iss.job.oschina;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.iss.blog.tag.service.TagsService;
import com.iss.common.spring.SpringContextHolder;
import com.iss.constant.SpiderManager;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

public class OschinaSpiderComponent implements Job {

	private static TagsService tagsService;

	static {
		if (tagsService == null) {
			tagsService = SpringContextHolder.getBean(TagsService.class);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("------------------");
		OschinaSpiderProcessor processor = new OschinaSpiderProcessor(tagsService);
		Spider spider = OOSpider.create(processor).addUrl("https://www.oschina.net/blog").addPipeline(new OschinaPipeline(tagsService));
		SpiderManager.setSpiderMap(OschinaSpiderComponent.class, spider);
		spider.run();
	}

}
