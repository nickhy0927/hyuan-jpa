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
import com.iss.aspect.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.AccessConstant;
import com.iss.constant.DataType;
import com.iss.constant.PlatformManageMenu;
import com.iss.orm.anno.MenuMonitor;
import com.iss.platform.access.dict.entity.Dict;
import com.iss.platform.access.dict.service.DictService;
import com.iss.platform.access.menu.entity.Menu;

@Controller
public class DictController {
	
	private final DictService dictService;
	
	public final static String DICT_MANAGE = "dictManage";

	@Autowired
	public DictController(DictService dictService) {
		this.dictService = dictService;
	}
	
	@MenuMonitor(name = "数据字典", orders = 1, level = 3, url = "/platform/access/dict/dictList.do", paraentAlias = PlatformManageMenu.BASE_MANAGE)
	public void dictManage() {
		
	}
	
	@MenuMonitor(name = "数据字典列表", level = 4, orders = 1, paraentAlias = DICT_MANAGE)
	@RequestMapping(name = "数据字典列表页面", value = "/platform/access/dict/dictList.do", method = RequestMethod.GET)
	public String dictList() {
		return "platform/access/dict/dictList";
	}

	@ResponseBody
	@MenuMonitor(name = "数据字典列表-获取数据分页", orders = 1, level = 5, paraentAlias = "dictList")
	@RequestMapping(name = "获取数据字数据分页",value = "/platform/access/dict/dictList.json", method = { RequestMethod.POST })
	public MessageObject<Dict> dictPage(HttpServletRequest request, PageSupport support) {
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
	@MenuMonitor(name = "数据字典列表-修改数据状态", orders = 2, level = 4, paraentAlias = "dictList")
	@OperateLog(message = "修改数据字典信息状态", optType = DataType.OptType.UPDATE, service = DictService.class)
	@RequestMapping(name = "修改数据字典信息状态", value = "/platform/access/dict/dictStatusUpdate.json", method = RequestMethod.POST)
	public MessageObject<Menu> dictStatusUpdate(Dict dict) {
		MessageObject<Menu> messageObject = MessageObject.getDefaultInstance();
		try {
			dictService.saveEntity(dict);
			messageObject.openTip("数据字典" + AccessConstant.Enable.getName(dict.getEnable() ? "1" : "0") + "成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("数据字典" + AccessConstant.Enable.getName(dict.getEnable() ? "1" : "0") + "失败");
		}
		return messageObject;
	}

	@MenuMonitor(name = "数据字典新增", orders = 2, level = 3, paraentAlias = DICT_MANAGE)
	@RequestMapping(name = "新增数据字典页面", value = "/platform/access/dict/dictCreate.do", method = RequestMethod.GET)
	public String dictCreate(Model model) {
		List<Dict> dictTypeList = dictService.queryDictByParentNull();
		model.addAttribute("dictTypeList", dictTypeList);
		model.addAttribute("dictCode", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "platform/access/dict/dictCreate";
	}

	@ResponseBody
	@MenuMonitor(name = "数据字典新增-保存新增数据", orders = 1, level = 4, paraentAlias = "dictCreate")
	@OperateLog(message = "保存数据字典信息", optType = DataType.OptType.INSERT, service = DictService.class)
	@RequestMapping(name = "保存数据字典信息", value = "/platform/access/dict/dictCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictCreateSave(Dict dict) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(dict.getPid())) {
				dict.setDict(dictService.get(dict.getPid()));
			}
			dict.setStatus(IsDelete.NO);
			dictService.saveEntity(dict);
			messageObject.openTip("新增数据字典成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "数据字典删除", orders = 2, level = 3, paraentAlias = DICT_MANAGE)
	@OperateLog(message = "删除数据字典信息", optType = DataType.OptType.DELETE, service = DictService.class)
	@RequestMapping(name = "删除数据字典信息", value = "/platform/access/dict/dictDelete.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictDelete(String id) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<Dict> dicts = Lists.newArrayList();
			for (String dictId : ids) {
				Dict dict = dictService.get(dictId);
				dict.setStatus(IsDelete.YES);
				dicts.add(dict);
			}
			dictService.saveBatch(dicts);
			messageObject.openTip("删除数据字典成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除数据字典异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "数据字典修改", orders = 3, level = 3, paraentAlias = DICT_MANAGE)
	@RequestMapping(name = "修改数据字典页面", value = "/platform/access/dict/dictEdit.do", method = RequestMethod.GET)
	public String dictEdit(String id, Model model) {
		model.addAttribute("id", id);
		List<Dict> dictTypeList = dictService.queryDictByParentNull();
		model.addAttribute("dictTypeList", dictTypeList);
		return "platform/access/dict/dictEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "数据字典修改-查询数据详情", orders = 4, level = 4, paraentAlias = "dictEdit")
	@RequestMapping(name = "查询数据字典详情", value = "/platform/access/dict/dictEditJson.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictEditJson(String id) {
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
	@MenuMonitor(name = "数据字典修改-保存修改数据", orders = 4, level = 4, paraentAlias = "dictEdit")
	@OperateLog(message = "保存数据字典信息", optType = DataType.OptType.INSERT, service = DictService.class)
	@RequestMapping(name = "保存数据字典信息", value = "/platform/access/dict/dictEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Dict> dictEditUpdate(Dict dict) {
		MessageObject<Dict> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(dict.getPid())) {
				dict.setDict(dictService.get(dict.getPid()));
			}
			dict.setStatus(IsDelete.NO);
			dictService.saveEntity(dict);
			messageObject.openTip("修改数据字典成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}


	@ResponseBody
	@AccessAuthority(alias = "dicttreelist", name = "数据字典列表")
	@RequestMapping(name = "获取数据字典分页列表", value = "/platform/access/dict/dictTableList.json", method = { RequestMethod.POST })
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
}
