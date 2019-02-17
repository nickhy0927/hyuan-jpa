package com.iss.news.section.controller;

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
@RequestMapping(value = "/content/news/section")
public class SectionController {

	@Autowired
	private SectionService sectionService;

	@RequestMapping(name = "新增版块页面", value = "/sectionCreate.do", method = RequestMethod.GET)
	public String sectionCreate() {
		return "content/section/sectionCreate";
	}

	@ResponseBody
	@OperateLog(message = "保存版块", optType = DataType.OptType.INSERT, service = SectionService.class)
	@RequestMapping(value = "/sectionCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionCreateSave(Section section) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			section.setStatus(IsDelete.NO);
			sectionService.saveEntity(section);
			messageObject.openTip("新增版块成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "section-delete", name = "删除版块")
	@OperateLog(message = "删除版块", optType = DataType.OptType.DELETE, service = SectionService.class)
	@RequestMapping(value = "/sectionDelete.json", method = RequestMethod.POST)
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

	@AccessAuthority(alias = "section-edit", name = "进入新增版块页面")
	@RequestMapping(value = "/sectionEdit.do", method = RequestMethod.GET)
	public String sectionEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "content/section/sectionEdit";
	}

	@ResponseBody
	@OperateLog(message = "修改版块", optType = DataType.OptType.UPDATE, service = SectionService.class)
	@RequestMapping(value = "/sectionEditJson.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionEditJson(String id) {
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
	@OperateLog(message = "修改版块", optType = DataType.OptType.UPDATE, service = SectionService.class)
	@RequestMapping(name = "修改版块", value = "/sectionEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Section> sectionEditUpdate(Section section) {
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			section.setStatus(IsDelete.NO);
			sectionService.saveEntity(section);
			messageObject.openTip("修改版块成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@RequestMapping(name = "版块列表页面", value = "/sectionList.do", method = RequestMethod.GET)
	public String sectionList() {
		return "content/section/sectionList";
	}

	@ResponseBody
	@RequestMapping(name = "版块列表分页", value = "/sectionList.json", method = { RequestMethod.POST })
	public MessageObject<Section> sectionList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Section> messageObject = MessageObject.getDefaultInstance();
		try {
			PagerInfo<Section> tools = sectionService.queryPageByMap(map, support);
			messageObject.ok("查询版块成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询版块异常");
		}
		return messageObject;
	}
}
