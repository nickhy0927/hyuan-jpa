package com.iss.news.spider.test;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;

public class TestOschinaBlog {
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private static Site site = Site.me().setDomain("www.oschina.net").setUserAgent(
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36")
			.setSleepTime(0).setRetryTimes(3).setRetryTimes(3).setSleepTime(1000);

	public static void main(String[] args) {
		Spider spider = OOSpider.create(site, new OsChinaPageModelPipeline(), OsChianTitle.class)
				.addUrl("https://www.oschina.net/blog");
		spider.thread(5).run();
	}
}
