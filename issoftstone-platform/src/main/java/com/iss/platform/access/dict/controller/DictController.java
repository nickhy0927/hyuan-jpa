package com.iss.platform.access.dict.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.iss.platform.access.dict.entity.Dict;
import com.iss.platform.access.dict.service.DictService;

@Controller
public class DictController {
	private final DictService dictService;

	@Autowired
	public DictController(DictService dictService) {
		this.dictService = dictService;
	}

	@AccessAuthority(alias = "dict-save", name = "进入新增数据字典页面")
	@RequestMapping(value = "/platform/access/dict/dictCreate.do", method = RequestMethod.GET)
	public String dictCreate(Model model) {
		List<Dict> dictTypeList = dictService.queryDictByParentNull();
		model.addAttribute("dictTypeList", dictTypeList);
		model.addAttribute("dictCode", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "platform/access/dict/dictCreate";
	}
	
	@AccessAuthority(alias = "dict-edit", name = "进入新增数据字典页面")
	@RequestMapping(value = "/platform/access/dict/dictEdit.do", method = RequestMethod.GET)
	public String dictEdit(String id, Model model) {
		model.addAttribute("id", id);
		List<Dict> dictTypeList = dictService.queryDictByParentNull();
		model.addAttribute("dictTypeList", dictTypeList);
		return "platform/access/dict/dictEdit";
	}
	
	@AccessAuthority(alias = "dict-list", name = "进入数据字典列表页面")
	@RequestMapping(value = "/platform/access/dict/dictList.do", method = RequestMethod.GET)
	public String dictList() {
		return "platform/access/dict/dictList";
	}
	
	@ResponseBody
	@AccessAuthority(alias = "dict-save|dict-edit", name = "保存数据字典")
	@OperateLog(message = "保存数据字典信息", method = "dictSave", optType = DataType.OptType.INSERT, service = DictService.class)
	@RequestMapping(value = "/platform/access/dict/dictSave.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictSave(Dict dict) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			String id = dict.getId();
			if (StringUtils.isNotEmpty(dict.getPid()) ) {
				dict.setDict(dictService.get(dict.getPid()));
			}
			dict.setStatus(IsDelete.NO);
			dictService.saveEntity(dict);
			if (StringUtils.isEmpty(id)) {
				messageObject.openTip("新增数据字典成功");
			} else {
				messageObject.openTip("修改数据字典成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "dict-edit", name = "修改数据字典")
	@RequestMapping(value = "/platform/access/dict/dictEdit.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictEdit(String id) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			Dict dict = dictService.get(id);
			messageObject.ok("查询数据字典成功", dict);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询数据字典异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "dict-list", name = "数据字典列表")
	@RequestMapping(value = "/platform/access/dict/dictList.json", method = { RequestMethod.POST })
	public MessageObject<Dict> dictList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Dict> tools = dictService.queryPageByMap(map, support);
			messageObject.ok("查询数据字典成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询数据字典异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@AccessAuthority(alias = "dict-tree-list", name = "数据字典列表")
	@RequestMapping(value = "/platform/access/dict/dictTableList.json", method = { RequestMethod.POST })
	public List<Dict> dictTableList(HttpServletRequest request) {
		try {
			Map<String, Object> map = WebUtils.getRequestToMap(request);
			map.put("status_eq", IsDelete.NO);
			return dictService.queryByMap(map);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return Lists.newArrayList();
	}

	@ResponseBody
	@AccessAuthority(alias = "dict-delete", name = "删除数据字典信息")
	@OperateLog(message = "删除数据字典信息", method = "dictDelete", optType = DataType.OptType.DELETE, service = DictService.class)
	@RequestMapping(value = "/platform/access/dict/dictDelete.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictDelete(String id) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<Dict> dicts = Lists.newArrayList();
				for (String string : ids) {
					Dict dict = dictService.get(string);
					dict.setStatus(IsDelete.YES);
					dicts.add(dict);
				}
				dictService.saveBatch(dicts);
				messageObject.openTip("删除数据字典成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除数据字典异常");
		}
		return messageObject;
	}
}
