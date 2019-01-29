package com.iss.news.comments.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.news.comments.entity.Comments;
import com.iss.news.comments.service.CommentService;

public class CommentsController {

	@Autowired
	private CommentService commentService;
	
	@AccessAuthority(alias = "comments-save", name = "进入评论新增页面")
	@RequestMapping(value = "/content/news/comments/commentsCreate.do", method = RequestMethod.GET)
	public String CommentsCreate() {
		return "content/comments/commentsCreate";
	}
	
	@AccessAuthority(alias = "comments-edit", name = "进入评论编辑页面")
	@RequestMapping(value = "/content/news/comments/commentsEdit.do", method = RequestMethod.GET)
	public String CommentsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/comments/commentsEdit";
	}
	
	@AccessAuthority(alias = "comments-save", name = "进入评论列表页面")
	@RequestMapping(value = "/content/news/comments/commentsList.do", method = RequestMethod.GET)
	public String commentsList() {
		return "content/comments/commentsList";
	}
	
	@ResponseBody
	@AccessAuthority(alias = "comments-save", name = "保存评论信息")
	@OperateLog(message = "保存评论信息", method = "commentsSave", optType = DataType.OptType.INSERT, service = CommentService.class)
	@RequestMapping(value = "/content/news/comments/commentsSave.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsSave(Comments comments) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = comments.getId();
			comments.setStatus(IsDelete.NO);
			commentService.saveEntity(comments);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增评论成功");
			} else {
				messageObject.openTip("修改评论成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "Comments-edit", name = "修改评论信息")
	@OperateLog(message = "修改评论信息", method = "commentsEdit", optType = DataType.OptType.UPDATE, service = CommentService.class)
	@RequestMapping(value = "/content/news/comments/commentsEdit.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsEdit(String id) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			Comments comments = commentService.get(id);
			messageObject.ok("修改查询评论成功", comments);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询评论异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "comments-list", name = "评论信息列表")
	@RequestMapping(value = "/content/news/comments/commentsList.json", method = { RequestMethod.POST })
	public MessageObject<Comments> commentsList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Comments> tools = commentService.queryPageByMap(map, support);
			messageObject.ok("查询评论成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询评论异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "comments-delete", name = "删除评论信息")
	@OperateLog(message = "删除评论信息", method = "commentsDelete", optType = DataType.OptType.DELETE, service = CommentService.class)
	@RequestMapping(value = "/content/news/Comments/commentsDelete.json", method = RequestMethod.POST)
	public MessageObject<Comments> CommentsDelete(String id) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<Comments> commentss = Lists.newArrayList();
				for (String string : ids) {
					Comments comments = commentService.get(string);
					comments.setStatus(IsDelete.YES);
					commentss.add(comments);
				}
				commentService.saveBatch(commentss);
				messageObject.openTip("删除评论成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除评论异常");
		}
		return messageObject;
	}
}
