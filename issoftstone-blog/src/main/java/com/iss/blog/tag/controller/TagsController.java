package com.iss.blog.tag.controller;

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
import com.google.common.collect.Maps;
import com.iss.aspect.anno.OperateLog;
import com.iss.blog.tag.entity.Tags;
import com.iss.blog.tag.service.TagsService;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SelectTree;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType.OptType;

@Controller
@RequestMapping(value = "/blog/tags")
public class TagsController {

	@Autowired
	private TagsService tagsService;

	@RequestMapping(name = "博客标签新增页面", value = "/tagsCreate.do", method = RequestMethod.GET)
	public String tagsCreate() {
		return "/blog/tags/tagsCreate";
	}

	@ResponseBody
	@OperateLog(message = "保存分类", optType = OptType.INSERT, service = TagsService.class)
	@RequestMapping(name = "保存分类请求链接", value = "/tagsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Tags> TagsCreateSave(Tags tags) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			tags.setStatus(IsDelete.NO);
			tagsService.saveEntity(tags);
			messageObject.openTip("保存分类成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("保存分类失败");
		}
		return messageObject;
	}

	@RequestMapping(name = "分类管理修改页面", value = "/tagsEdit.do", method = RequestMethod.GET)
	public String tagsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "/blog/tags/TagsEdit";
	}

	@ResponseBody
	@RequestMapping(name = "查询分类详情", value = "/tagsEditJSON.json", method = RequestMethod.POST)
	public MessageObject<Tags> tagsEditJSON(String id) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("查询分类详情成功", tagsService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类详情失败");
		}
		return messageObject;
	}
	@ResponseBody
	@RequestMapping(name = "查询分类详情", value = "/querySelectTree.json", method = RequestMethod.POST)
	public MessageObject<Tags> querySelectTree() {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> paramMap = Maps.newConcurrentMap();
			paramMap.put("status_eq", IsDelete.NO);
			List<Tags> tagss = tagsService.queryByMap(paramMap);
			List<SelectTree> selectTrees = Lists.newArrayList();
			for (Tags tags : tagss) {
				selectTrees.add(new SelectTree(tags.getId(), tags.getTag(), tags, ""));
			}
			messageObject.ok("查询分类下拉树成功", selectTrees);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类下拉树失败");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "修改分类", optType = OptType.UPDATE, service = TagsService.class)
	@RequestMapping(name = "修改分类请求链接", value = "/TagsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Tags> TagsEditUpdate(Tags tags) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			tagsService.saveEntity(tags);
			messageObject.openTip("修改分类成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改分类失败");
		}
		return messageObject;
	}

	@RequestMapping(name = "分类管理列表页面", value = "/tagsList.do", method = RequestMethod.GET)
	public String tagsList() {
		return "/blog/tags/tagsList";
	}

	@ResponseBody
	@RequestMapping(name = "查询分类分页请求链接", value = "/tagsList.json", method = RequestMethod.POST)
	public MessageObject<Tags> TagsList(HttpServletRequest request, PageSupport support) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			PagerInfo<Tags> pagerInfo = tagsService.queryPageByMap(map, support);
			messageObject.ok("查询分类列表分页成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类列表分页失败");
		}
		return messageObject;
	}
}
