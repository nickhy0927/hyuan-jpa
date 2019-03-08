package com.iss.news.comments.controller;

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
import com.iss.news.comments.entity.Comments;
import com.iss.news.comments.service.CommentsService;
import com.iss.news.constants.ContentManageMenu;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class CommentsController {
	
	private static Logger logger = LoggerFactory.getLogger(CommentsController.class);

	public final static String COMMENTS_MANAGE = "commentsManage";
	
	private final CommentsService commentsService;

	@Autowired
	public CommentsController(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
	
	@MenuMonitor(name = "评论管理", orders = 2, level = 3, url = "/content/comments/commentsList.do", paraentAlias = ContentManageMenu.NEWS_CONTENT_MANAGE)
	public void commentsManage() {
	}
	
	@MenuMonitor(name = "评论新增", orders = 1, level = 4, paraentAlias = COMMENTS_MANAGE)
	@RequestMapping(name = "评论新增页面", value = "/content/comments/commentsCreate.do", method = RequestMethod.GET)
	public String commentsCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "content/comments/commentsCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "评论新增-保存新增数据", orders = 2, level = 5, paraentAlias = "commentsCreate")
	@OperateLog(message = "保存评论", optType = DataType.OptType.INSERT, service = CommentsService.class)
	@RequestMapping(name = "保存评论", value = "/content/comments/commentsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsCreateSave(Comments comments) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			comments.setStatus(IsDelete.NO);
			commentsService.saveEntity(comments);
			messageObject.openTip("新增评论成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "评论删除", orders = 2, level = 4, paraentAlias = COMMENTS_MANAGE)
	@OperateLog(message = "删除评论", optType = DataType.OptType.DELETE, service = CommentsService.class)
	@RequestMapping(name = "删除评论", value = "/content/comments/commentsDetete.json", method = { RequestMethod.POST })
	public MessageObject<Comments> commentsDetete(String id) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Comments> commentss = Lists.newArrayList();
				for (String string : ids) {
					Comments comments = commentsService.get(string);
					comments.setStatus(IsDelete.YES);
					commentss.add(comments);
				}
				commentsService.saveBatch(commentss);
				messageObject.openTip("删除评论成功");
			} else {
				messageObject.error("删除评论异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除Comments异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "评论修改", orders = 3, level = 4, paraentAlias = COMMENTS_MANAGE)
	@RequestMapping(name = "评论修改页面", value = "/content/comments/commentsEdit.do", method = RequestMethod.GET)
	public String commentsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/comments/commentsEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "评论修改-获取数据详情", orders = 1, level = 5, paraentAlias = "commentsEdit")
	@RequestMapping(name = "获取评论", value = "/content/comments/commentsEditJson.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsEditJson(String id) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取评论成功", commentsService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取评论异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "评论修改-保存修改数据", orders = 2, level = 5, paraentAlias = "commentsEdit")
	@OperateLog(message = "修改评论", optType = DataType.OptType.UPDATE, service = CommentsService.class)
	@RequestMapping(name = "修改保存Comments",value = "/content/comments/commentsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsEditUpdate(Comments comments) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			comments.setStatus(IsDelete.NO);
			commentsService.saveEntity(comments);
			messageObject.openTip("修改评论成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "评论列表", orders = 4, level = 4, paraentAlias = COMMENTS_MANAGE)
	@RequestMapping(name = "评论列表页面", value = "/content/comments/commentsList.do")
	public String commentsList() {
		return "content/comments/commentsList";
	}

	@ResponseBody
	@MenuMonitor(name = "评论列表-获取数据分页", orders = 1, level = 5, paraentAlias = "commentsList")
	@RequestMapping(name = "获取数据分页", value = "/content/comments/commentsList.json", method = { RequestMethod.POST })
	public MessageObject<Comments> commentsPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Comments> pagerInfo = commentsService.queryPageByMap(map, support);
			messageObject.ok("查询评论分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询评论分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
