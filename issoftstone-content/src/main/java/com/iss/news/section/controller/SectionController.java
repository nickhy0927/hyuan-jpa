package com.iss.news.section.controller;

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
import com.iss.news.section.entity.Section;
import com.iss.news.section.service.SectionService;

@Controller
public class SectionController {

	@Autowired
	private SectionService sectionService;
	
	@AccessAuthority(alias = "section-save", name = "进入新增版块页面")
	@RequestMapping(value = "/content/news/section/sectionCreate.do", method = RequestMethod.GET)
	public String sectionCreate() {
		return "content/section/sectionCreate";
	}
	
	@AccessAuthority(alias = "section-edit", name = "进入新增版块页面")
	@RequestMapping(value = "/content/news/section/sectionEdit.do", method = RequestMethod.GET)
	public String sectionEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/section/sectionEdit";
	}
	
	@AccessAuthority(alias = "section-save", name = "进入版块列表页面")
	@RequestMapping(value = "/content/news/section/sectionList.do", method = RequestMethod.GET)
	public String sectionList() {
		return "content/section/sectionList";
	}
	
	@ResponseBody
	@AccessAuthority(alias = "section-save|save-edit", name = "保存版块")
	@OperateLog(message = "保存版块信息", method = "sectionSave", optType = DataType.OptType.INSERT, service = SectionService.class)
	@RequestMapping(value = "/content/news/section/sectionSave.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionSave(Section section) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = section.getId();
			section.setStatus(IsDelete.NO);
			sectionService.saveEntity(section);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增版块成功");
			} else {
				messageObject.openTip("修改版块成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "section-edit", name = "修改版块")
	@OperateLog(message = "修改版块信息", method = "sectionEdit", optType = DataType.OptType.UPDATE, service = SectionService.class)
	@RequestMapping(value = "/content/news/section/sectionEdit.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionEdit(String id) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			Section section = sectionService.get(id);
			messageObject.ok("修改查询版块成功", section);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询版块异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "section-list", name = "版块列表")
	@RequestMapping(value = "/content/news/section/sectionList.json", method = { RequestMethod.POST })
	public MessageObject<Section> sectionList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Section> tools = sectionService.queryPageByMap(map, support);
			messageObject.ok("查询版块成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询版块异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "section-delete", name = "删除版块信息")
	@OperateLog(message = "删除版块信息", method = "sectionDelete", optType = DataType.OptType.DELETE, service = SectionService.class)
	@RequestMapping(value = "/content/news/section/sectionDelete.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionDelete(String id) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<Section> sections = Lists.newArrayList();
				for (String string : ids) {
					Section section = sectionService.get(string);
					section.setStatus(IsDelete.YES);
					sections.add(section);
				}
				sectionService.saveBatch(sections);
				messageObject.openTip("删除版块成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除版块异常");
		}
		return messageObject;
	}
}
