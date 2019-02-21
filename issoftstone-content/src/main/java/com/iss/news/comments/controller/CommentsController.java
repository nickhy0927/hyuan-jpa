package com.iss.news.comments.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.news.comments.entity.Comments;
import com.iss.news.comments.service.CommentService;

@Controller
@RequestMapping(name = "评论", value = "/content/news/comments")
public class CommentsController {

	@Autowired
	private CommentService commentService;

	@RequestMapping(name = "新增评论页面", value = "/commentsCreate.do", method = RequestMethod.GET)
	public String commentsCreate() {
		return "content/comments/commentsCreate";
	}

	@RequestMapping(name = "修改评论页面", value = "/commentsEdit.do", method = RequestMethod.GET)
	public String CommentsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/comments/commentsEdit";
	}

	@RequestMapping(name = "评论列表页面", value = "/commentsList.do", method = RequestMethod.GET)
	public String commentsList() {
		return "content/comments/commentsList";
	}

	@ResponseBody
	@OperateLog(message = "保存评论", optType = DataType.OptType.INSERT, service = CommentService.class)
	@RequestMapping(name = "保存评论", value = "/commentsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsCreateSave(Comments comments) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			comments.setStatus(IsDelete.NO);
			commentService.saveEntity(comments);
			messageObject.openTip("新增评论成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "修改评论", optType = DataType.OptType.UPDATE, service = CommentService.class)
	@RequestMapping(name = "修改评论", value = "/commentsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsEditUpdate(Comments comments) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			comments.setStatus(IsDelete.NO);
			commentService.saveEntity(comments);
			messageObject.openTip("修改评论成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(name = "获取评论详情", value = "/commentsEdit.json", method = RequestMethod.POST)
	public MessageObject<Comments> commentsEdit(String id) {
		MessageObject<Comments> messageObject = MessageObject.getDefaultInstance();
		try {
			Comments comments = commentService.get(id);
			messageObject.ok("获取评论详情成功", comments);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("获取评论详情异常");
		}
		return messageObject;
	}

	@ResponseBody
	@RequestMapping(name = "评论列表分页", value = "/commentsList.json", method = RequestMethod.POST)
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
	@OperateLog(message = "删除评论", optType = DataType.OptType.DELETE, service = CommentService.class)
	@RequestMapping(name = "删除评论", value = "/commentsDelete.json", method = RequestMethod.POST)
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
