package com.iss.news.spider;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Hyuan
 */
public class BaiduNewsPage implements PageProcessor {

	@Override
	public void process(Page page) {
		List<String> links = page.getHtml().xpath("//h3[@class='vrTitle']/a/@href").all();
		for (String string : links) {
			System.out.println(string);
		}
		Selectable selectable = page.getHtml().xpath("//div[@class='vrwrap']");
		Selectable selectable2 = selectable.$("div.vrwrap>div>div.news-detail>div.news-info>p.news-from");
		List<String> all = selectable2.all();
		for (String string : all) {
			System.out.println(string);
		}
		List<String> list = selectable.$("div.vrwrap>div>h3.vrTitle>a", "text").all();
		for (String string : list) {
			System.out.println(string);
		}
		page.putField("title", page.getHtml().$("div.vrwrap>div>h3.vrTitle>a", "text"));
//		page.addTargetRequests(links);
	}

	@Override
	public Site getSite() {
		return Site.me();
	}

	public static void main(String[] args) {
		String key = "习近平";
		Spider.create(new BaiduNewsPage())
				.addUrl("https://news.sogou.com/news?mode=1&sort=0&fixrank=1&query=" + key)
				.addPipeline(new ConsolePipeline()).run();
	}
}
