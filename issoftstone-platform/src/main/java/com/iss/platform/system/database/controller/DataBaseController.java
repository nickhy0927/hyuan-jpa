/**
 * 
 */
package com.iss.platform.system.database.controller;

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
import com.iss.constant.AccessConstant;
import com.iss.constant.AccessConstant.DataBaseType;
import com.iss.orm.anno.MenuMonitor;
import com.iss.constant.DataType;
import com.iss.constant.PlatformManageMenu;
import com.iss.platform.system.database.entity.DataBase;
import com.iss.platform.system.database.service.DataBaseService;

/**
 * @author Mr's Huang
 *
 */
@Controller
public class DataBaseController {

	private final DataBaseService dataBaseService;

	@Autowired
	public DataBaseController(DataBaseService dataBaseService) {
		this.dataBaseService = dataBaseService;
	}

	public final static String DATABASEMANAGE = "dataBaseManage";

	@MenuMonitor(name = "数据库管理", orders = 2, level = 3, url = "/platform/system/dataBase/dataBaseList.do", paraentAlias = PlatformManageMenu.SYSTEM_MANAGE)
	public void dataBaseManage() {
	}
	
	@MenuMonitor(name = "数据库新增", orders = 1, level = 4, paraentAlias = DATABASEMANAGE)
	@RequestMapping(name = "新增数据库页面", value = "/platform/system/dataBase/dataBaseCreate.do", method = RequestMethod.GET)
	public String dataBaseCreate(Model model) {
		model.addAttribute("dataBaseType", DataBaseType.getSelectList());
		return "platform/system/database/dataBaseCreate";
	}

	@ResponseBody
	@MenuMonitor(name = "数据库新增", orders = 1, level = 5, paraentAlias = "dataBaseCreate")
	@OperateLog(message = "保存数据库", optType = DataType.OptType.INSERT, service = DataBaseService.class)
	@RequestMapping(name = "保存数据库", value = "/platform/system/dataBase/dataBaseCreateSave.json", method = RequestMethod.POST)
	public MessageObject<DataBase> dataBaseCreateSave(DataBase dataBase) {
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			dataBase.setStatus(IsDelete.NO);
			dataBaseService.saveEntity(dataBase);
			messageObject.openTip("新增数据库成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "数据库删除", orders = 2, level = 4, paraentAlias = DATABASEMANAGE)
	@OperateLog(message = "删除数据库", optType = DataType.OptType.DELETE, service = DataBaseService.class)
	@RequestMapping(name = "删除数据库", value = "/platform/system/dataBase/dataBaseDelete.json", method = RequestMethod.POST)
	public MessageObject<DataBase> dataBaseDelete(String id) {
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			List<DataBase> dataBases = Lists.newArrayList();
			for (String dataBaseId : ids) {
				DataBase dataBase = dataBaseService.get(dataBaseId);
				dataBase.setStatus(IsDelete.YES);
				dataBases.add(dataBase);
			}
			dataBaseService.saveBatch(dataBases);
			messageObject.openTip("删除数据库成功", null);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除数据库异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "数据库修改", orders = 3, level = 4, paraentAlias = DATABASEMANAGE)
	@RequestMapping(name = "新增数据库页面", value = "/platform/system/dataBase/dataBaseEdit.do", method = RequestMethod.GET)
	public String dataBaseEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/system/database/dataBaseEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "数据库修改-获取数据详情", orders = 1, level = 5, paraentAlias = "dataBaseEdit")
	@RequestMapping(name = "查询数据库详情", value = "/platform/system/dataBase/dataBaseEditJson.json", method = RequestMethod.POST)
	public MessageObject<DataBase> dataBaseEditJson(String id) {
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			DataBase dataBase = dataBaseService.get(id);
			messageObject.ok("查询数据库成功", dataBase);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询数据库异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "数据库修改-保存修改数据", orders = 1, level = 5, paraentAlias = "dataBaseEdit")
	@OperateLog(message = "保存数据库", optType = DataType.OptType.INSERT, service = DataBaseService.class)
	@RequestMapping(name = "保存数据库", value = "/platform/system/dataBase/dataBaseEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<DataBase> dataBaseEditUpdate(DataBase dataBase) {
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			dataBase.setStatus(IsDelete.NO);
			dataBaseService.saveEntity(dataBase);
			messageObject.openTip("修改数据库成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("操作出现异常，请稍后.");
		}
		return messageObject;
	}

	@MenuMonitor(name = "数据库新增", orders = 4, level = 4, paraentAlias = DATABASEMANAGE)
	@RequestMapping(name = "数据库列表页面", value = "/platform/system/dataBase/dataBaseList.do", method = RequestMethod.GET)
	public String dataBaseList() {
		return "platform/system/database/dataBaseList";
	}

	@ResponseBody
	@MenuMonitor(name = "数据库列表-获取分页数据", orders = 1, level = 5, paraentAlias = "dataBaseList")
	@RequestMapping(name = "获取数据库列表分页",value = "/platform/system/dataBase/dataBaseList.json", method = { RequestMethod.POST })
	public MessageObject<DataBase> dataBaseListPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<DataBase> tools = dataBaseService.queryPageByMap(map, support);
			messageObject.ok("查询数据库成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询数据库异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "数据库列表-修改数据状态", orders = 2, level = 5, paraentAlias = "dataBaseList")
	@OperateLog(message = "修改数据库状态", optType = DataType.OptType.UPDATE, service = DataBaseService.class)
	@RequestMapping(name = "修改数据库状态", value = "/platform/system/dataBase/dataBaseStatusUpdate.json", method = RequestMethod.POST)
	public MessageObject<DataBase> dataBaseStatusUpdate(DataBase dataBase) {
		MessageObject<DataBase> messageObject = MessageObject.getDefaultInstance();
		try {
			dataBaseService.saveEntity(dataBase);
			messageObject.openTip("数据库" + AccessConstant.Enable.getName(dataBase.getEnable() ? "1" : "0") + "成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.openTip("数据库" + AccessConstant.Enable.getName(dataBase.getEnable() ? "1" : "0") + "失败");
		}
		return messageObject;
	}
}
