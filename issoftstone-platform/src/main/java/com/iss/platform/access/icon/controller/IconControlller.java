package com.iss.platform.access.icon.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.access.icon.entity.Icon;
import com.iss.platform.access.icon.service.IconService;

/**
 * @author Mr's Huang
 */
@Controller
public class IconControlller {

	private final IconService iconService;

	@Autowired
	public IconControlller(IconService iconService) {
		this.iconService = iconService;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-save", name = "新增图标信息")
	@OperateLog(message = "新增图标信息", method = "iconSave", optType = DataType.OptType.INSERT, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/iconSave.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconSave(Icon icon) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			icon.setIconClass("<i class=\"Hui-iconfont " + icon.getClassName() + "\"></i> ");
			icon.setStatus(IsDelete.NO);
			iconService.saveEntity(icon);
			messageObject.openTip("新增图标信息成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-update", name = "修改图标信息")
	@OperateLog(message = "新增图标信息", method = "iconUpdate", optType = DataType.OptType.UPDATE, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/iconUpdate.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconUpdate(Icon icon) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			icon.setIconClass("<i class=\"Hui-iconfont " + icon.getClassName() + "\"></i> ");
			icon.setStatus(IsDelete.NO);
			iconService.saveEntity(icon);
			messageObject.openTip("修改图标信息成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "queryIconList", name = "获取所有图标信息")
	@RequestMapping(value = "/platform/access/icon/queryIconList.json", method = RequestMethod.GET)
	public Map<String, Object> queryIconList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> paramMap = WebUtils.getRequestToMap(request);
		Map<String, Object> maps = Maps.newConcurrentMap();
		support.setLimit(20);
		paramMap.put("status_eq", IsDelete.NO);
		PagerInfo<Icon> pagerInfo = iconService.queryPageByMap(paramMap, support);
		maps.put("incomplete_results", pagerInfo.getContent().size() > 0);
		maps.put("total_count", pagerInfo.getTotals());
		maps.put("results", pagerInfo.getContent());
		return maps;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-edit", name = "修改图标信息")
	@RequestMapping(value = "/platform/access/icon/edit.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconEdit(String id) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			Icon icon = iconService.get(id);
			messageObject.ok("修改查询图标信息成功", icon);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询图标信息异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-list", name = "图标列表")
	@RequestMapping(value = "/platform/access/icon/list.json", method = { RequestMethod.POST })
	public MessageObject<Icon> iconList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Icon> tools = iconService.queryPageByMap(map, support);
			messageObject.ok("查询图标信息成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询图标信息异常");
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-delete", name = "删除图标信息")
	@OperateLog(message = "删除图标信息", method = "delete", optType = DataType.OptType.DELETE, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/iconDelete.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconDelete(String id) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<Icon> icons = Lists.newArrayList();
				for (String string : ids) {
					Icon icon = iconService.get(string);
					icon.setStatus(IsDelete.YES);
					icons.add(icon);
				}
				iconService.saveBatch(icons);
				messageObject.openTip("删除图标信息成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除图标信息异常");
		}
		return messageObject;
	}
}
