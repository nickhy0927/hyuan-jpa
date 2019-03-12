package com.iss.job.oschina;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.iss.blog.tag.service.TagsService;
import com.iss.constant.SpiderManager;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author Hyuan
 */
public class OschinaSpiderChildrenProcessor implements PageProcessor {

	private TagsService tagsService;

	public OschinaSpiderChildrenProcessor(TagsService tagsService) {
		this.tagsService = tagsService;
	}

	@Override
	public void process(Page page) {
		Selectable selectable = page.getHtml().xpath("//div[@class='left-channel']/div[@class='menu']/a/@href");
		List<String> list = selectable.all();
		List<String> links = Lists.newArrayList();
		for (String link : list) {
			if (isValidation(link)) {
				System.out.println("link:" + link);
				links.add(link);
			}
		}
		for (String string : links) {
			OschinaSpiderChildrenProcessor processor = new OschinaSpiderChildrenProcessor(tagsService);
			Spider spider = OOSpider.create(processor).addUrl(string).addPipeline(new OschinaChildrenPipeline(tagsService));
			SpiderManager.setSpiderMap(OschinaSpiderComponent.class, spider);
		}
		page.addTargetRequests(links);
		page.putField("title", page.getHtml().$("h2[@class='header']").toString());
		page.putField("content", page.getHtml().$("div.content").toString());
		page.putField("tags", page.getHtml().xpath("//div[@class='tags']/a/text()").all());
	}

	@Override
	public Site getSite() {
		return Site.me();
	}

	public static boolean isValidation(String path) {
		// 正则表达式规则
		String regEx = "\\s?classification=\\d+";
		// 编译正则表达式
		Matcher matcher = Pattern.compile(regEx).matcher(path);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.find();
	}

	public static void main(String[] args) {
		String path = "https://www.oschina.net/blog?classification=429511";
		boolean validation = isValidation(path);
		System.out.println("validation =" + validation);
	}
}
