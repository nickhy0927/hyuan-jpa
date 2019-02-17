package com.iss.job.oschina;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.iss.blog.tag.service.TagsService;
import com.iss.common.spring.SpringContextHolder;
import com.iss.constant.SpiderManager;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.example.OschinaBlog;
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
		Spider spider = OOSpider.create(Site.me(), new OschinaPipeline(tagsService), OschinaBlog.class)
				.addUrl("https://www.oschina.net/blog");
		SpiderManager.setSpiderMap(OschinaSpiderComponent.class, spider);
		spider.run();
	}

}
