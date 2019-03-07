package com.iss.platform.access.icon.controller;

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
import com.iss.constant.PlatformManageMenu;
import com.iss.orm.anno.MenuMonitor;
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

	public final static String ICON_MANAGE = "iconManage";
	@MenuMonitor(name = "图标管理", orders = 2, level = 3, url = "/platform/access/icon/iconList.do", paraentAlias = PlatformManageMenu.BASE_MANAGE)
	public void iconManage() {
	}

	@MenuMonitor(name = "图标新增", orders = 1, level = 4, paraentAlias = ICON_MANAGE)
	@RequestMapping(name = "图标新增页面", value = "/platform/access/icon/iconCreate.do", method = RequestMethod.GET)
	public String iconCreate() {
		return "platform/access/icon/create";
	}

	@ResponseBody
	@MenuMonitor(name = "图标新增-保存新增数据", orders = 1, level = 5, paraentAlias = "iconCreate")
	@OperateLog(message = "保存图标请求", optType = DataType.OptType.INSERT, service = IconService.class)
	@RequestMapping(name = "保存图标请求", value = "/platform/access/icon/iconCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconCreateSave(Icon icon) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			icon.setIconClass("<i class=\"Hui-iconfont " + icon.getClassName() + "\"></i> ");
			icon.setStatus(IsDelete.NO);
			iconService.saveEntity(icon);
			messageObject.openTip("新增图标成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "图标删除", orders = 2, level = 4, paraentAlias = ICON_MANAGE)
	@OperateLog(message = "删除图标", optType = DataType.OptType.DELETE, service = IconService.class)
	@RequestMapping(name = "删除图标", value = "/platform/access/icon/iconDelete.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconDelete(String id) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<Icon> icons = Lists.newArrayList();
			for (String string : ids) {
				Icon icon = iconService.get(string);
				icon.setStatus(IsDelete.YES);
				icons.add(icon);
			}
			iconService.saveBatch(icons);
			messageObject.openTip("删除图标成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除图标异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "图标修改", orders = 3, level = 4, paraentAlias = ICON_MANAGE)
	@RequestMapping(name = "图标修改页面", value = "/platform/access/icon/iconEdit.do", method = RequestMethod.GET)
	public String iconEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/icon/edit";
	}

	@ResponseBody
	@MenuMonitor(name = "图标修改-查询图标详情", orders = 1, level = 5, paraentAlias = "iconEdit")
	@RequestMapping(name = "查询图标详情", value = "/platform/access/icon/iconEditJson.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconEditJson(String id) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			Icon icon = iconService.get(id);
			messageObject.ok("修改查询图标成功", icon);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询图标异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "图标修改-保存修改数据", orders = 2, level = 5, paraentAlias = "iconEdit")
	@OperateLog(message = "修改图标", optType = DataType.OptType.UPDATE, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/iconEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconEditUpdate(Icon icon) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			icon.setIconClass("<i class=\"Hui-iconfont " + icon.getClassName() + "\"></i> ");
			icon.setStatus(IsDelete.NO);
			iconService.saveEntity(icon);
			messageObject.openTip("修改图标成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "图标列表", orders = 4, level = 4, paraentAlias = ICON_MANAGE)
	@RequestMapping(name = "图标列表页面", value = "/platform/access/icon/iconList.do", method = RequestMethod.GET)
	public String iconList() {
		return "platform/access/icon/list";
	}

	@ResponseBody
	@MenuMonitor(name = "图标列表-获取数据分页", orders = 1, level = 5, paraentAlias = "iconList")
	@RequestMapping(name = "获取图标列表分页", value = "/platform/access/icon/iconList.json", method = { RequestMethod.POST })
	public MessageObject<Icon> iconPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Icon> tools = iconService.queryPageByMap(map, support);
			messageObject.ok("查询图标成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询图标异常");
		}
		return messageObject;
	}
	
}
