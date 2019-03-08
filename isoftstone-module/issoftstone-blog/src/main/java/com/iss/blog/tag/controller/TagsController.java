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
import com.iss.blog.classification.entity.Classification;
import com.iss.blog.classification.service.ClassificationService;
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
import com.iss.constants.BlogManageMenu;
import com.iss.orm.anno.MenuMonitor;

@Controller
public class TagsController {

	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private ClassificationService classificationService;
	
	public final static String TAGSMANAGE = "tagsManage";
	@MenuMonitor(name = "标签管理", orders = 2, level = 3, url = "/blog/tags/tagsList.do", paraentAlias = BlogManageMenu.BASE_MANAGE)
	public void tagsManage() {
	}
	
	@MenuMonitor(name = "标签新增", orders = 1, level = 4, paraentAlias = TAGSMANAGE)
	@RequestMapping(name = "博客标签新增页面", value = "/blog/tags/tagsCreate.do", method = RequestMethod.GET)
	public String tagsCreate() {
		return "/blog/tags/tagsCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "标签新增-获取博客标签", orders = 1, level = 5, paraentAlias = "tagsCreate")
	@RequestMapping(name = "查询标签详情", value = "/blog/tags/queryClassificationSelectTree.json", method = RequestMethod.POST)
	public MessageObject<Classification> queryClassificationSelectTree() {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> paramMap = Maps.newConcurrentMap();
			paramMap.put("status_eq", IsDelete.NO);
			List<Classification> classifications = classificationService.queryByMap(paramMap);
			List<SelectTree> selectTrees = Lists.newArrayList();
			for (Classification classification : classifications) {
				Classification cls = classification.getClassification();
				String pId = cls == null ? "" : cls.getId();
				selectTrees.add(new SelectTree(classification.getId(), classification.getName(), classification, pId));
			}
			messageObject.ok("查询标签下拉树成功", selectTrees);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询标签下拉树失败");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "标签新增-保存新增数据", orders = 2, level = 5, paraentAlias = "tagsCreate")
	@OperateLog(message = "保存博客标签", optType = OptType.INSERT, service = TagsService.class)
	@RequestMapping(name = "保存博客标签请求", value = "/blog/tags/tagsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Tags> TagsCreateSave(Tags tags) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			tags.setStatus(IsDelete.NO);
			tagsService.saveEntity(tags);
			messageObject.openTip("保存标签成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("保存标签失败");
		}
		return messageObject;
	}
	
	@ResponseBody
	@MenuMonitor(name = "标签删除", orders = 2, level = 4, paraentAlias = TAGSMANAGE)
	@RequestMapping(name = "删除标签", value = "/blog/tags/tagsDelete.json", method = RequestMethod.POST)
	public MessageObject<Tags> tagsDelete(String id) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<Tags> tList = Lists.newArrayList();
			for (String dictId : ids) {
				Tags tags = tagsService.get(dictId);
				tags.setStatus(IsDelete.YES);
				tList.add(tags);
			}
			tagsService.saveBatch(tList);
			messageObject.openTip("删除博客标签成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除博客标签异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "标签修改", orders = 3, level = 4, paraentAlias = TAGSMANAGE)
	@RequestMapping(name = "博客标签修改页面", value = "/blog/tags/tagsEdit.do", method = RequestMethod.GET)
	public String tagsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "/blog/tags/TagsEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "标签修改-获取标签详情", orders = 1, level = 5, paraentAlias = "tagsEdit")
	@RequestMapping(name = "查询标签详情", value = "/blog/tags/tagsEditJSON.json", method = RequestMethod.POST)
	public MessageObject<Tags> tagsEditJSON(String id) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("查询标签详情成功", tagsService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询标签详情失败");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "标签修改-保存修改数据", orders = 1, level = 5, paraentAlias = "tagsEdit")
	@OperateLog(message = "修改标签", optType = OptType.UPDATE, service = TagsService.class)
	@RequestMapping(name = "修改标签请求链接", value = "/blog/tags/tagsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Tags> TagsEditUpdate(Tags tags) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			tagsService.saveEntity(tags);
			messageObject.openTip("修改标签成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改标签失败");
		}
		return messageObject;
	}

	@MenuMonitor(name = "标签修改", orders = 4, level = 4, paraentAlias = TAGSMANAGE)
	@RequestMapping(name = "标签管理列表页面", value = "/blog/tags/tagsList.do", method = RequestMethod.GET)
	public String tagsList() {
		return "/blog/tags/tagsList";
	}

	@ResponseBody
	@MenuMonitor(name = "标签列表-获取分页数据", orders = 1, level = 5, paraentAlias = "tagsList")
	@RequestMapping(name = "获取博客标签分页", value = "/blog/tags/tagsList.json", method = RequestMethod.POST)
	public MessageObject<Tags> tagsPage(HttpServletRequest request, PageSupport support) {
		MessageObject<Tags> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			PagerInfo<Tags> pagerInfo = tagsService.queryPageByMap(map, support);
			messageObject.ok("查询标签列表分页成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询标签列表分页失败");
		}
		return messageObject;
	}
}
