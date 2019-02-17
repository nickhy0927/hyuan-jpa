package com.iss.blog.classification.controller;

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
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SelectTree;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType.OptType;

@Controller
@RequestMapping(value = "/blog/classification")
public class ClassificationController {

	@Autowired
	private ClassificationService classificationService;

	@RequestMapping(name = "分类管理新增页面", value = "/classificationCreate.do", method = RequestMethod.GET)
	public String classificationCreate() {
		return "/blog/classification/classificationCreate";
	}

	@ResponseBody
	@OperateLog(message = "保存分类", optType = OptType.INSERT, service = ClassificationService.class)
	@RequestMapping(name = "保存分类请求链接", value = "/classificationCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationCreateSave(Classification classification) {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			classification.setStatus(IsDelete.NO);
			classification.setClassification(classificationService.get(classification.getParentId()));;
			classificationService.saveEntity(classification);
			messageObject.openTip("保存分类成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("保存分类失败");
		}
		return messageObject;
	}

	@RequestMapping(name = "分类管理修改页面", value = "/classificationEdit.do", method = RequestMethod.GET)
	public String classificationEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "/blog/classification/classificationEdit";
	}

	@ResponseBody
	@RequestMapping(name = "查询分类详情", value = "/classificationEditJSON.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationEditJSON(String id) {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("查询分类详情成功", classificationService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类详情失败");
		}
		return messageObject;
	}
	@ResponseBody
	@RequestMapping(name = "查询分类详情", value = "/querySelectTree.json", method = RequestMethod.POST)
	public MessageObject<Classification> querySelectTree() {
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
			messageObject.ok("查询分类下拉树成功", selectTrees);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类下拉树失败");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "修改分类", optType = OptType.UPDATE, service = ClassificationService.class)
	@RequestMapping(name = "修改分类请求链接", value = "/classificationEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationEditUpdate(Classification classification) {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			classificationService.saveEntity(classification);
			messageObject.openTip("修改分类成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改分类失败");
		}
		return messageObject;
	}

	@RequestMapping(name = "分类管理列表页面", value = "/classificationList.do", method = RequestMethod.GET)
	public String classificationList() {
		return "/blog/classification/classificationList";
	}

	@ResponseBody
	@RequestMapping(name = "查询分类分页请求链接", value = "/classificationList.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationList(HttpServletRequest request, PageSupport support) {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			PagerInfo<Classification> pagerInfo = classificationService.queryPageByMap(map, support);
			messageObject.ok("查询分类列表分页成功", pagerInfo);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询分类列表分页失败");
		}
		return messageObject;
	}
}
