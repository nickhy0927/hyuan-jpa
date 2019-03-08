package com.iss.news.category.controller;

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
import com.iss.news.category.entity.Category;
import com.iss.news.category.service.CategoryService;
import com.iss.news.constants.ContentManageMenu;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class CategoryController {
	
	private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

	public final static String CATEGORY_MANAGE = "categoryManage";
	
	private final CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@MenuMonitor(name = "栏目分类管理", orders = 1, level = 3, url = "/content/category/categoryList.do", paraentAlias = ContentManageMenu.BASE_MANAGE)
	public void categoryManage() {
	}
	
	@MenuMonitor(name = "栏目分类新增", orders = 1, level = 4, paraentAlias = CATEGORY_MANAGE)
	@RequestMapping(name = "栏目分类新增页面", value = "/content/category/categoryCreate.do", method = RequestMethod.GET)
	public String categoryCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "content/category/categoryCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "栏目分类新增-保存新增数据", orders = 2, level = 5, paraentAlias = "categoryCreate")
	@OperateLog(message = "保存栏目分类", optType = DataType.OptType.INSERT, service = CategoryService.class)
	@RequestMapping(name = "保存栏目分类", value = "/content/category/categoryCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryCreateSave(Category category) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			category.setStatus(IsDelete.NO);
			categoryService.saveEntity(category);
			messageObject.openTip("新增栏目分类成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "栏目分类删除", orders = 2, level = 4, paraentAlias = CATEGORY_MANAGE)
	@OperateLog(message = "删除栏目分类", optType = DataType.OptType.DELETE, service = CategoryService.class)
	@RequestMapping(name = "删除栏目分类", value = "/content/category/categoryDetete.json", method = { RequestMethod.POST })
	public MessageObject<Category> categoryDetete(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Category> categorys = Lists.newArrayList();
				for (String string : ids) {
					Category category = categoryService.get(string);
					category.setStatus(IsDelete.YES);
					categorys.add(category);
				}
				categoryService.saveBatch(categorys);
				messageObject.openTip("删除栏目分类成功");
			} else {
				messageObject.error("删除栏目分类异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除Category异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "栏目分类修改", orders = 3, level = 4, paraentAlias = CATEGORY_MANAGE)
	@RequestMapping(name = "栏目分类修改页面", value = "/content/category/categoryEdit.do", method = RequestMethod.GET)
	public String categoryEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/category/categoryEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "栏目分类修改-获取数据详情", orders = 1, level = 5, paraentAlias = "categoryEdit")
	@RequestMapping(name = "获取栏目分类", value = "/content/category/categoryEditJson.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryEditJson(String id) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取栏目分类成功", categoryService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取栏目分类异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "栏目分类修改-保存修改数据", orders = 2, level = 5, paraentAlias = "categoryEdit")
	@OperateLog(message = "修改栏目分类", optType = DataType.OptType.UPDATE, service = CategoryService.class)
	@RequestMapping(name = "修改保存Category",value = "/content/category/categoryEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Category> categoryEditUpdate(Category category) {
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			category.setStatus(IsDelete.NO);
			categoryService.saveEntity(category);
			messageObject.openTip("修改栏目分类成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "栏目分类列表", orders = 4, level = 4, paraentAlias = CATEGORY_MANAGE)
	@RequestMapping(name = "栏目分类列表页面", value = "/content/category/categoryList.do")
	public String categoryList() {
		return "content/category/categoryList";
	}

	@ResponseBody
	@MenuMonitor(name = "栏目分类列表-获取数据分页", orders = 1, level = 5, paraentAlias = "categoryList")
	@RequestMapping(name = "获取数据分页", value = "/content/category/categoryList.json", method = { RequestMethod.POST })
	public MessageObject<Category> categoryPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Category> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Category> pagerInfo = categoryService.queryPageByMap(map, support);
			messageObject.ok("查询栏目分类分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询栏目分类分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
