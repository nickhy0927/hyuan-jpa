package com.iss.news.section.controller;

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
import com.iss.news.constants.ContentManageMenu;
import com.iss.news.section.entity.Section;
import com.iss.news.section.service.SectionService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class SectionController {
	
	private static Logger logger = LoggerFactory.getLogger(SectionController.class);

	public final static String SECTION_MANAGE = "sectionManage";
	
	private final SectionService sectionService;

	@Autowired
	public SectionController(SectionService sectionService) {
		this.sectionService = sectionService;
	}
	
	@MenuMonitor(name = "新闻类型管理", orders = 2, level = 3, url = "/content/section/sectionList.do", paraentAlias = ContentManageMenu.BASE_MANAGE)
	public void sectionManage() {
	}
	
	@MenuMonitor(name = "新闻类型新增", orders = 1, level = 4, paraentAlias = SECTION_MANAGE)
	@RequestMapping(name = "新闻类型新增页面", value = "/content/section/sectionCreate.do", method = RequestMethod.GET)
	public String sectionCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "content/section/sectionCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "新闻类型新增-保存新增数据", orders = 2, level = 5, paraentAlias = "sectionCreate")
	@OperateLog(message = "保存新闻类型", optType = DataType.OptType.INSERT, service = SectionService.class)
	@RequestMapping(name = "保存新闻类型", value = "/content/section/sectionCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionCreateSave(Section section) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			section.setStatus(IsDelete.NO);
			sectionService.saveEntity(section);
			messageObject.openTip("新增新闻类型成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "新闻类型删除", orders = 2, level = 4, paraentAlias = SECTION_MANAGE)
	@OperateLog(message = "删除新闻类型", optType = DataType.OptType.DELETE, service = SectionService.class)
	@RequestMapping(name = "删除新闻类型", value = "/content/section/sectionDetete.json", method = { RequestMethod.POST })
	public MessageObject<Section> sectionDetete(String id) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Section> sections = Lists.newArrayList();
				for (String string : ids) {
					Section section = sectionService.get(string);
					section.setStatus(IsDelete.YES);
					sections.add(section);
				}
				sectionService.saveBatch(sections);
				messageObject.openTip("删除新闻类型成功");
			} else {
				messageObject.error("删除新闻类型异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除Section异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "新闻类型修改", orders = 3, level = 4, paraentAlias = SECTION_MANAGE)
	@RequestMapping(name = "新闻类型修改页面", value = "/content/section/sectionEdit.do", method = RequestMethod.GET)
	public String sectionEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/section/sectionEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "新闻类型修改-获取数据详情", orders = 1, level = 5, paraentAlias = "sectionEdit")
	@RequestMapping(name = "获取新闻类型", value = "/content/section/sectionEditJson.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionEditJson(String id) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取新闻类型成功", sectionService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取新闻类型异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "新闻类型修改-保存修改数据", orders = 2, level = 5, paraentAlias = "sectionEdit")
	@OperateLog(message = "修改新闻类型", optType = DataType.OptType.UPDATE, service = SectionService.class)
	@RequestMapping(name = "修改保存Section",value = "/content/section/sectionEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionEditUpdate(Section section) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			section.setStatus(IsDelete.NO);
			sectionService.saveEntity(section);
			messageObject.openTip("修改新闻类型成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "新闻类型列表", orders = 4, level = 4, paraentAlias = SECTION_MANAGE)
	@RequestMapping(name = "新闻类型列表页面", value = "/content/section/sectionList.do")
	public String sectionList() {
		return "content/section/sectionList";
	}

	@ResponseBody
	@MenuMonitor(name = "新闻类型列表-获取数据分页", orders = 1, level = 5, paraentAlias = "sectionList")
	@RequestMapping(name = "获取数据分页", value = "/content/section/sectionList.json", method = { RequestMethod.POST })
	public MessageObject<Section> sectionPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Section> pagerInfo = sectionService.queryPageByMap(map, support);
			messageObject.ok("查询新闻类型分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询新闻类型分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
