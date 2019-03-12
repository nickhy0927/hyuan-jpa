package com.iss.job.spider;

import java.util.List;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractBy.Type;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author Hyuan
 */
@TargetUrl("https://my.oschina.net/*/blog/\\d+")
public class OsChianBlog {

	@ExtractBy(value = "//div[@class='article-detail']/h2[@class='header']/text()")
	private String title;
	
	/**
	 * 发布时间
	 */
	@ExtractBy(value = "//div[@class='article-detail']/div[@class='meta-wrap']/div[@class='item']/text()", type = Type.XPath)
	private String time;

	/**
	 * 博客内容
	 */
	@ExtractBy(value = "//div[@class='article-detail']/div[@class='content']", notNull = true, type = Type.XPath)
	private String content;

	/**
	 * 博客标签
	 */
	@ExtractBy(value = "//div[@class='article-detail']/div[@class='tags']/a/text()")
	private List<String> tags;

	@ExtractBy(value = "//div[@class='article-detail']/div[@class='meta-wrap']/div[@class='item']/a[@class='__user']/span/text()")
	private String author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "OsChianTitle [title=" + title + ", content=" + content + ", tags=" + tags + "]";
	}

}
