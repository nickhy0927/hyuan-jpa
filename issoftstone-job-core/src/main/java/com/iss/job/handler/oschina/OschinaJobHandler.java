package com.iss.job.handler.oschina;

import org.springframework.stereotype.Component;

import com.iss.blog.tag.service.TagsService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

@Component
@JobHandler(value="oschinaJobHandler")
public class OschinaJobHandler extends IJobHandler {

	private TagsService tagsService;
	
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		Spider spider = OOSpider.create(Site.me(), new OschinaPipeline(tagsService), OschinaBlog.class).addUrl("https://www.oschina.net/blog");
		spider.run();
		return ReturnT.SUCCESS;
	}

}
