package com.iss.platform.access.icon.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iss.anno.OperateLog;
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
 * @author
 */
@Controller
public class IconControlller {
	
	@Autowired
	private IconService iconService;
	
	@ResponseBody
	@AccessAuthority(alias = "icon-save-json", name = "保存图标")
	@OperateLog(message = "保存图标信息", method = "save", optType = DataType.OptType.INSERT, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/save.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconSave(Icon icon) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			icon.setIconClass("<i class=\"layui-icon " + icon.getClassName() + "\"></i> ");
			icon.setStatus(IsDelete.NO);
			iconService.saveEntity(icon);
			messageObject.ok("新增图标成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@AccessAuthority(alias = "icon-edit-json", name = "修改图标")
	@OperateLog(message = "修改图标信息", method = "edit", optType = DataType.OptType.UPDATE, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/edit.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconEdit(String id) {
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
	@AccessAuthority(alias = "icon-list-json", name = "图标列表")
	@RequestMapping(value = "/platform/access/icon/list.json", method = { RequestMethod.POST })
	public MessageObject<Icon> iconList(HttpServletRequest request, PageSupport support) {
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

	@ResponseBody
	@AccessAuthority(alias = "icon-delete-json", name = "删除图标")
	@OperateLog(message = "删除图标信息", method = "delete", optType = DataType.OptType.DELETE, service = IconService.class)
	@RequestMapping(value = "/platform/access/icon/delete.json", method = RequestMethod.POST)
	public MessageObject<Icon> iconDelete(@RequestBody String[] ids) {
		MessageObject<Icon> messageObject = MessageObject.getDefaultInstance();
		try {
			if (ids.length > 0) {
				iconService.deleteBatch(ids);
				messageObject.ok("删除图标成功");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除图标异常");
		}
		return messageObject;
	}
}
