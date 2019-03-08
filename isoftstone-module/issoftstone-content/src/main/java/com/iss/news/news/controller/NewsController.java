package com.iss.news.news.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.orm.anno.MenuMonitor;
import com.iss.news.constants.ContentManageMenu;
import com.iss.news.news.entity.News;
import com.iss.news.news.service.NewsService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class NewsController {
	
	private static Logger logger = LoggerFactory.getLogger(NewsController.class);

	public final static String NEWS_MANAGE = "newsManage";
	
	private final NewsService newsService;

	@Autowired
	public NewsController(NewsService newsService) {
		this.newsService = newsService;
	}
	
	@MenuMonitor(name = "新闻管理", orders = 1, level = 3, url = "/content/news/newsList.do", paraentAlias = ContentManageMenu.NEWS_CONTENT_MANAGE)
	public void newsManage() {
	}
	
	@MenuMonitor(name = "新闻新增", orders = 1, level = 4, paraentAlias = NEWS_MANAGE)
	@RequestMapping(name = "新闻新增页面", value = "/content/news/newsCreate.do", method = RequestMethod.GET)
	public String newsCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "content/news/newsCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "新闻新增-保存新增数据", orders = 2, level = 5, paraentAlias = "newsCreate")
	@OperateLog(message = "保存新闻", optType = DataType.OptType.INSERT, service = NewsService.class)
	@RequestMapping(name = "保存新闻", value = "/content/news/newsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<News> newsCreateSave(News news) {
		MessageObject<News> messageObject = MessageObject.getDefaultInstance();
		try {
			news.setStatus(IsDelete.NO);
			newsService.saveEntity(news);
			messageObject.openTip("新增新闻成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "新闻删除", orders = 2, level = 4, paraentAlias = NEWS_MANAGE)
	@OperateLog(message = "删除新闻", optType = DataType.OptType.DELETE, service = NewsService.class)
	@RequestMapping(name = "删除新闻", value = "/content/news/newsDetete.json", method = { RequestMethod.POST })
	public MessageObject<News> newsDetete(String id) {
		MessageObject<News> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<News> newss = Lists.newArrayList();
				for (String string : ids) {
					News news = newsService.get(string);
					news.setStatus(IsDelete.YES);
					newss.add(news);
				}
				newsService.saveBatch(newss);
				messageObject.openTip("删除新闻成功");
			} else {
				messageObject.error("删除新闻异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除News异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "新闻修改", orders = 3, level = 4, paraentAlias = NEWS_MANAGE)
	@RequestMapping(name = "新闻修改页面", value = "/content/news/newsEdit.do", method = RequestMethod.GET)
	public String newsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/news/newsEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "新闻修改-获取数据详情", orders = 1, level = 5, paraentAlias = "newsEdit")
	@RequestMapping(name = "获取新闻", value = "/content/news/newsEditJson.json", method = RequestMethod.POST)
	public MessageObject<News> newsEditJson(String id) {
		MessageObject<News> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取新闻成功", newsService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取新闻异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "新闻修改-保存修改数据", orders = 2, level = 5, paraentAlias = "newsEdit")
	@OperateLog(message = "修改新闻", optType = DataType.OptType.UPDATE, service = NewsService.class)
	@RequestMapping(name = "修改保存News",value = "/content/news/newsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<News> newsEditUpdate(News news) {
		MessageObject<News> messageObject = MessageObject.getDefaultInstance();
		try {
			news.setStatus(IsDelete.NO);
			newsService.saveEntity(news);
			messageObject.openTip("修改新闻成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "新闻列表", orders = 4, level = 4, paraentAlias = NEWS_MANAGE)
	@RequestMapping(name = "新闻列表页面", value = "/content/news/newsList.do")
	public String newsList() {
		return "content/news/newsList";
	}

	@ResponseBody
	@MenuMonitor(name = "新闻列表-获取数据分页", orders = 1, level = 5, paraentAlias = "newsList")
	@RequestMapping(name = "获取数据分页", value = "/content/news/newsList.json", method = { RequestMethod.POST })
	public MessageObject<News> newsPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<News> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<News> pagerInfo = newsService.queryPageByMap(map, support);
			messageObject.ok("查询新闻分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询新闻分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
