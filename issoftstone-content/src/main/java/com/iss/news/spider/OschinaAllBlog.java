package com.iss.news.spider;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://my.oschina.net/**/blog/\\d+")
public class OschinaAllBlog {

    @ExtractBy("//h2[@class='header']/text()")
    private String title;

    @ExtractBy(value = "div.content",type = ExtractBy.Type.Css)
    private String content;

    @ExtractBy(value = "//div[@class='tags']/a/text()", multi = true)
    private List<String> tags;
    
    @Override
	public String toString() {
		return "OschinaBlog [title=" + title + ", content=" + content + ", tags=" + tags + "]";
	}

	public static void main(String[] args) {
        OOSpider.create(
        	Site.me(),
			new ConsolePageModelPipeline(), OschinaAllBlog.class).addUrl("https://www.oschina.net/blog").run();
    }
}
