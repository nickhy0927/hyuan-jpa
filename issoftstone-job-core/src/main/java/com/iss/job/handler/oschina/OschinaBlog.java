package com.iss.job.handler.oschina;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://my.oschina.net/**/blog/\\d+")
public class OschinaBlog {

	@ExtractBy("//h2[@class='header']/text()")
	private String title;

	@ExtractBy(value = "div.content", type = ExtractBy.Type.Css)
	private String content;

	@ExtractBy(value = "//div[@class='tags']/a/text()", multi = true)
	private List<String> tags;

	@Override
	public String toString() {
		return "OschinaBlog [title=" + title + ", content=" + content + ", tags=" + tags + "]";
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public List<String> getTags() {
		return tags;
	}
}
