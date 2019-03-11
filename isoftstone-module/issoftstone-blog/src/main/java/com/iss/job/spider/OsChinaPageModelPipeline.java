package com.iss.job.spider;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.blog.article.entity.Article;
import com.iss.blog.article.service.ArticleService;
import com.iss.blog.tag.entity.Tags;
import com.iss.blog.tag.service.TagsService;
import com.iss.common.exception.ServiceException;
import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.SysContants.IsDelete;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class OsChinaPageModelPipeline implements PageModelPipeline<OsChianBlog> {

	private static ArticleService articleService;

	private static TagsService tagsService;

	static {
		articleService = SpringContextHolder.getBean(ArticleService.class);
		tagsService = SpringContextHolder.getBean(TagsService.class);
	}

	private static List<Article> articles = Lists.newArrayList();

	@Override
	public void process(OsChianBlog blog, Task task) {
		try {
			if (articles.size() > 5) {
				articleService.saveBatch(articles);
				articles.clear();
			}
			Article article = new Article();
			article.setTitle(blog.getTitle());
			List<String> tags = blog.getTags();
			if (tags.size() > 0) {
				List<Tags> tagsList = Lists.newArrayList();
				Map<String, Object> paramMap = Maps.newConcurrentMap();
				for (String tag : tags) {
					paramMap.put("tag_eq", tag);
					List<Tags> list = tagsService.queryByMap(paramMap);
					if (list.size() > 0) tagsList.add(list.get(0));
					else {
						Tags obj = new Tags();
						obj.setTag(tag);
						obj.setStatus(IsDelete.NO);
						tagsService.saveEntity(obj);
						tagsList.add(obj);
					}
				}
				article.setProfile(Article.stripHtml(blog.getContent()).substring(0, 100) + "...");
				article.setContent(StringUtils.isNotEmpty(blog.getContent()) ? blog.getContent().getBytes() : new byte[0]);
				article.setStatus(IsDelete.NO);
				article.setTags(tagsList);
			}
			Integer hashCode = article.hashCode();
			Map<String, Object> params = Maps.newConcurrentMap();
			params.put("hashCode_eq", hashCode);
			System.out.println(article);
			List<Article> list = articleService.queryByMap(params);
			if (list.size() == 0) {
				article.setHashCode(hashCode);
				articles.add(article);
			}
		} catch (ServiceException e) {
		}
	}
}
