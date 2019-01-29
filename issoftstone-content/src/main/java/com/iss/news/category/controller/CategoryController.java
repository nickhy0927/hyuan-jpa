package com.iss.news.category.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.iss.news.category.entity.Category;
import com.iss.news.category.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@AccessAuthority(alias = "category-save", name = "进入新增栏目页面")
	@RequestMapping(value = "/content/news/category/categoryCreate.do", method = RequestMethod.GET)
	public String CategoryCreate() {
		return "content/category/categoryCreate";
	}
	
	@AccessAuthority(alias = "category-edit", name = "进入新增栏目页面")
	@RequestMapping(value = "/content/news/category/categoryEdit.do", method = RequestMethod.GET)
	public String CategoryEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/category/categoryEdit";
	}
	
	@AccessAuthority(alias = "category-save", name = "进入栏目列表页面")
	@RequestMapping(value = "/content/news/category/categoryList.do", method = RequestMethod.GET)
	public String categoryList() {
		return "content/category/categoryList";
	}
	
	@ResponseBody
	@AccessAuthority(alias = "category-save", name = "保存栏目")
	@OperateLog(message = "保存栏目信息", method = "categorySave", optType = DataType.OptType.INSERT, service = CategoryService.class)
	@RequestMapping(value = "/content/news/category/categorySave.json", method = RequestMethod.POST)
	public MessageObject<Category> categorySave(Category category) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = category.getId();
			category.setStatus(IsDelete.NO);
			categoryService.saveEntity(category);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增栏目成功");
			} else {
				messageObject.openTip("修改栏目成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "Category-edit", name = "修改栏目")
	@OperateLog(message = "修改栏目信息", method = "categoryEdit", optType = DataType.OptType.UPDATE, service = CategoryService.class)
	@RequestMapping(value = "/content/news/category/categoryEdit.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryEdit(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			Category category = categoryService.get(id);
			messageObject.ok("修改查询栏目成功", category);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询栏目异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "category-list", name = "栏目列表")
	@RequestMapping(value = "/content/news/Category/categoryList.json", method = { RequestMethod.POST })
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

	@ResponseBody
	@AccessAuthority(alias = "category-delete", name = "删除栏目信息")
	@OperateLog(message = "删除栏目信息", method = "categoryDelete", optType = DataType.OptType.DELETE, service = CategoryService.class)
	@RequestMapping(value = "/content/news/category/categoryDelete.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryDelete(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<Category> categorys = Lists.newArrayList();
				for (String string : ids) {
					Category category = categoryService.get(string);
					category.setStatus(IsDelete.YES);
					categorys.add(category);
				}
				categoryService.saveBatch(categorys);
				messageObject.openTip("删除栏目成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除栏目异常");
		}
		return messageObject;
	}
}
