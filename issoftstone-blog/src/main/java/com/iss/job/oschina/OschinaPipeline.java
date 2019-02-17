package com.iss.job.oschina;

import java.util.Map;
import java.util.Set;

import com.iss.blog.tag.service.TagsService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class OschinaPipeline implements Pipeline {
	
	private TagsService tagsService;

	public OschinaPipeline(TagsService tagsService) {
		this.tagsService = tagsService;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		System.out.println(tagsService);
		Map<String, Object> map = resultItems.getAll();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			System.out.println("key:"+ key + ", value = " + map.get(key));
		}
	}
	
}
