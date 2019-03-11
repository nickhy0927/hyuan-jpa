package com.iss.blog.article.controller;

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
import com.iss.constants.BlogManageMenu;
import com.iss.orm.anno.MenuMonitor;
import com.iss.blog.article.entity.Article;
import com.iss.blog.article.service.ArticleService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ArticleController {
	
	private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	public final static String ARTICLE_MANAGE = "ArticleManage";
	
	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@MenuMonitor(name = "文章管理", orders = 1, level = 3, url = "/blog/article/articleList.do", paraentAlias = BlogManageMenu.BLOG_CONTENT_MANAGE)
	public void articleManage() {
	}
	
	@MenuMonitor(name = "文章新增", orders = 1, level = 4, paraentAlias = ARTICLE_MANAGE)
	@RequestMapping(name = "文章新增页面", value = "/blog/article/articleCreate.do", method = RequestMethod.GET)
	public String articleCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "blog/article/articleCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "文章新增-保存新增数据", orders = 1, level = 5, paraentAlias = "articleCreate")
	@OperateLog(message = "保存文章", optType = DataType.OptType.INSERT, service = ArticleService.class)
	@RequestMapping(name = "保存文章", value = "/blog/article/articleCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Article> articleCreateSave(Article article) {
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			article.setStatus(IsDelete.NO);
			articleService.saveEntity(article);
			messageObject.openTip("新增文章成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "文章删除", orders = 2, level = 4, paraentAlias = ARTICLE_MANAGE)
	@OperateLog(message = "删除文章", optType = DataType.OptType.DELETE, service = ArticleService.class)
	@RequestMapping(name = "删除文章", value = "/blog/article/articleDetete.json", method = { RequestMethod.POST })
	public MessageObject<Article> articleDetete(String id) {
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Article> articles = Lists.newArrayList();
				for (String string : ids) {
					Article article = articleService.get(string);
					article.setStatus(IsDelete.YES);
					articles.add(article);
				}
				articleService.saveBatch(articles);
				messageObject.openTip("删除文章成功");
			} else {
				messageObject.error("删除文章异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除文章异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "文章修改", orders = 3, level = 4, paraentAlias = ARTICLE_MANAGE)
	@RequestMapping(name = "文章修改页面", value = "/blog/article/articleEdit.do", method = RequestMethod.GET)
	public String articleEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "blog/article/articleEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "文章修改-获取数据详情", orders = 1, level = 5, paraentAlias = "articleEdit")
	@RequestMapping(name = "获取文章", value = "/blog/article/articleEditJson.json", method = RequestMethod.POST)
	public MessageObject<Article> articleEditJson(String id) {
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取文章成功", articleService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取文章异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "文章修改-保存修改数据", orders = 2, level = 5, paraentAlias = "articleEdit")
	@OperateLog(message = "修改文章", optType = DataType.OptType.UPDATE, service = ArticleService.class)
	@RequestMapping(name = "修改保存Article",value = "/blog/article/articleEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Article> articleEditUpdate(Article article) {
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			article.setStatus(IsDelete.NO);
			articleService.saveEntity(article);
			messageObject.openTip("修改文章成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "文章列表", orders = 4, level = 4, paraentAlias = ARTICLE_MANAGE)
	@RequestMapping(name = "文章列表页面", value = "/blog/article/articleList.do")
	public String articleList() {
		return "blog/article/articleList";
	}

	@ResponseBody
	@MenuMonitor(name = "文章列表-获取数据分页", orders = 1, level = 5, paraentAlias = "articleList")
	@RequestMapping(name = "获取数据分页", value = "/blog/article/articleList.json", method = { RequestMethod.POST })
	public MessageObject<Article> articlePage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Article> pagerInfo = articleService.queryPageByMap(map, support);
			messageObject.ok("查询文章分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询文章分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
	
	
	@ResponseBody
	@RequestMapping(name = "获取数据分页", value = "/blog/article/queryArticlePage.json", method = { RequestMethod.POST })
	public MessageObject<Article> queryArticlePage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Article> pagerInfo = articleService.queryPageByMap(map, support);
			messageObject.ok("查询文章分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询文章分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
	
	@ResponseBody
	@RequestMapping(name = "获取数据分页", value = "/blog/article/queryArticleDetail.json", method = { RequestMethod.GET })
	public MessageObject<Article> queryArticleDetail(String id) {
		MessageObject<Article> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("查询文章分页成功", articleService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询文章分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
