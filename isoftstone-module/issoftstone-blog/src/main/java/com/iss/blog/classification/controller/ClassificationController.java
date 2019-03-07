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
import com.iss.aspect.anno.OperateLog;
import com.iss.blog.classification.entity.Classification;
import com.iss.blog.classification.service.ClassificationService;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType.OptType;
import com.iss.constants.BlogManageMenu;
import com.iss.orm.anno.MenuMonitor;

@Controller
public class ClassificationController {

	@Autowired
	private ClassificationService classificationService;

	public final static String CLASSIFICATIONMANAGE = "classificationManage";
	@MenuMonitor(name = "分类管理", orders = 1, level = 3, url = "/blog/classification/classificationList.do", paraentAlias = BlogManageMenu.BASE_MANAGE)
	public void classificationManage() {
	}
	
	@MenuMonitor(name = "分类新增", orders = 1, level = 4, paraentAlias = CLASSIFICATIONMANAGE)
	@RequestMapping(name = "分类管理新增页面", value = "/blog/classification/classificationCreate.do", method = RequestMethod.GET)
	public String classificationCreate() {
		return "/blog/classification/classificationCreate";
	}

	@ResponseBody
	@MenuMonitor(name = "分类新增-保存新增数据", orders = 1, level = 5, paraentAlias = "classificationCreate")
	@OperateLog(message = "保存分类", optType = OptType.INSERT, service = ClassificationService.class)
	@RequestMapping(name = "保存分类请求链接", value = "/blog/classification/classificationCreateSave.json", method = RequestMethod.POST)
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
	
	@ResponseBody
	@MenuMonitor(name = "分类删除", orders = 2, level = 4, paraentAlias = CLASSIFICATIONMANAGE)
	@RequestMapping(name = "删除分类", value = "/blog/classification/classificationDelete.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationDelete(String id) {
		MessageObject<Classification> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<Classification> classifications = Lists.newArrayList();
			for (String dictId : ids) {
				Classification classification = classificationService.get(dictId);
				classification.setStatus(IsDelete.YES);
				classifications.add(classification);
			}
			classificationService.saveBatch(classifications);
			messageObject.openTip("删除博客分类成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除博客分类异常");
		}
		return messageObject;
	}


	@MenuMonitor(name = "分类修改", orders = 3, level = 4, paraentAlias = CLASSIFICATIONMANAGE)
	@RequestMapping(name = "分类管理修改页面", value = "/blog/classification/classificationEdit.do", method = RequestMethod.GET)
	public String classificationEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "/blog/classification/classificationEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "分类修改-获取数据详情", orders = 1, level = 5, paraentAlias = "classificationEdit")
	@RequestMapping(name = "查询分类详情", value = "/blog/classification/classificationEditJSON.json", method = RequestMethod.POST)
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
	@MenuMonitor(name = "分类修改-保存修改数据", orders = 2, level = 5, paraentAlias = "classificationEdit")
	@OperateLog(message = "修改分类", optType = OptType.UPDATE, service = ClassificationService.class)
	@RequestMapping(name = "保存修改分类数据", value = "/blog/classification/classificationEditUpdate.json", method = RequestMethod.POST)
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

	@MenuMonitor(name = "分类列表", orders = 4, level = 4, paraentAlias = CLASSIFICATIONMANAGE)
	@RequestMapping(name = "分类管理列表页面", value = "/blog/classification/classificationList.do", method = RequestMethod.GET)
	public String classificationList() {
		return "/blog/classification/classificationList";
	}

	@ResponseBody
	@MenuMonitor(name = "分类列表-获取分页数据", orders = 1, level = 5, paraentAlias = "classificationList")
	@RequestMapping(name = "查询分类分页", value = "/blog/classification/classificationList.json", method = RequestMethod.POST)
	public MessageObject<Classification> classificationPage(HttpServletRequest request, PageSupport support) {
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
