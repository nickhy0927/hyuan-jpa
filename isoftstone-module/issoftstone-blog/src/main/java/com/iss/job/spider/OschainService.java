package com.iss.job.spider;

import java.util.List;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

@Service
public class OschainService implements PageProcessor {
	
	private static Spider spider = null;
	// 抓取网站的相关配置，可以包括编码、抓取间隔1s、重试次数等
	private Site site = Site.me().setCharset("utf8").setRetryTimes(1000).setSleepTime(1000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	public void process(Page page) {
		Html html = page.getHtml();
		List<String> links = html.xpath("//div[@class='left-channel']/div[@class='menu']/a[@class='item']/@href").all();
		for (String href : links) {
			Spider spider = OOSpider.create(site, new OsChinaPageModelPipeline(), OsChianBlog.class)
					.addUrl(href);
			spider.run();
			spider.stop();
		}
	}

	public void init() {
		OschainService my = new OschainService();
		long startTime, endTime;
		System.out.println("开始爬取...");
		startTime = System.currentTimeMillis();
		spider = Spider.create(my).addUrl("https://www.oschina.net/blog").thread(1);
		spider.run();
		spider.stop();
		endTime = System.currentTimeMillis();
		System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
	}
	
	public static void main(String[] args) {
		OschainService my = new OschainService();
		long startTime, endTime;
		System.out.println("开始爬取...");
		startTime = System.currentTimeMillis();
		spider = Spider.create(my).addUrl("https://www.oschina.net/blog").thread(1);
		spider.run();
		spider.stop();
		endTime = System.currentTimeMillis();
		System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
	}
}
