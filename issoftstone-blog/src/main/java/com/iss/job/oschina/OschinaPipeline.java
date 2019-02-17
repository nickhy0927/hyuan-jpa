package com.iss.job.oschina;

import java.util.List;

import com.google.common.collect.Lists;
import com.iss.blog.entity.Article;
import com.iss.blog.tag.entity.Tags;
import com.iss.blog.tag.service.TagsService;
import com.iss.common.utils.SysContants.IsDelete;

import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;

public class OschinaPipeline implements PageModelPipeline<OschinaBlog> {
	
	private TagsService tagsService;

	public OschinaPipeline(TagsService tagsService) {
		this.tagsService = tagsService;
	}
	
	@Override
	public void process(OschinaBlog t, Task task) {
		System.out.println(t);
		Article article = new Article();
		List<String> tags = t.getTags();
		List<Tags> tagsList = Lists.newArrayList();
		for (String tag : tags) {
			Tags entity = tagsService.queryTagsByTag(tag);
			if (entity == null) {
				Tags e = new Tags();
				e.setTag(tag);
				e.setStatus(IsDelete.NO);
				e = tagsService.saveEntity(e);
				tagsList.add(e);
			} else tagsList.add(entity);
		}
		article.setTags(tagsList);
		article.setTitle(t.getTitle());
		article.setContent(t.getContent());
		System.out.println(article);
	}
}
