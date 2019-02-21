package com.iss.news.category.controller;

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
import com.iss.news.category.entity.Category;
import com.iss.news.category.service.CategoryService;

@Controller
@RequestMapping(name = "栏目管理", value = "/content/news/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(name = "新增栏目页面", value = "/categoryCreate.do", method = RequestMethod.GET)
	public String categoryCreate() {
		return "content/category/categoryCreate";
	}

	@ResponseBody
	@OperateLog(message = "保存栏目", optType = DataType.OptType.INSERT, service = CategoryService.class)
	@RequestMapping(name = "保存栏目", value = "/categoryCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryCreateSave(Category category) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			category.setStatus(IsDelete.NO);
			categoryService.saveEntity(category);
			messageObject.openTip("新增栏目成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "删除栏目", optType = DataType.OptType.DELETE, service = CategoryService.class)
	@RequestMapping(name = "删除栏目", value = "/categoryDelete.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryDelete(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<Category> categorys = Lists.newArrayList();
			for (String string : ids) {
				Category category = categoryService.get(string);
				category.setStatus(IsDelete.YES);
				categorys.add(category);
			}
			categoryService.saveBatch(categorys);
			messageObject.openTip("删除栏目成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除栏目异常");
		}
		return messageObject;
	}

	@RequestMapping(name = "修改栏目页面", value = "/categoryEdit.do", method = RequestMethod.GET)
	public String categoryEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/category/categoryEdit";
	}

	@ResponseBody
	@OperateLog(message = "查询栏目", optType = DataType.OptType.UPDATE, service = CategoryService.class)
	@RequestMapping(name = "查询栏目", value = "/categoryEditJson.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryEditJson(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			Category category = categoryService.get(id);
			messageObject.ok("查询栏目成功", category);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询栏目异常");
		}
		return messageObject;
	}

	@ResponseBody
	@OperateLog(message = "修改栏目", optType = DataType.OptType.INSERT, service = CategoryService.class)
	@RequestMapping(name = "修改栏目", value = "/categoryEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryEditUpdate(Category category) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			category.setStatus(IsDelete.NO);
			categoryService.saveEntity(category);
			messageObject.openTip("修改栏目成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@RequestMapping(name = "栏目列表页面", value = "/categoryList.do", method = RequestMethod.GET)
	public String categoryList() {
		return "content/category/categoryList";
	}

	@ResponseBody
	@RequestMapping(name = "栏目分类列表分页", value = "/categoryList.json", method = { RequestMethod.POST })
	public MessageObject<Category> categoryList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Category> tools = categoryService.queryPageByMap(map, support);
			messageObject.ok("查询栏目成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询栏目异常");
		}
		return messageObject;
	}
}
