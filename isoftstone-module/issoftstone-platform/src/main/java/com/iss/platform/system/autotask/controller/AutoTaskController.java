package com.iss.platform.system.autotask.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.aspect.quartz.QuartzManager;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.SysContants.IsStart;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.constant.PlatformManageMenu;
import com.iss.constant.SpiderManager;
import com.iss.orm.anno.MenuMonitor;
import com.iss.platform.system.autotask.entity.AutoTask;
import com.iss.platform.system.autotask.service.AutoTaskService;

@Controller
public class AutoTaskController {
	@Autowired
	private AutoTaskService autoTaskService;

	public final static String AUTOTASKMANAGE = "autoTaskManage";

	@MenuMonitor(name = "调度任务管理", orders = 1, level = 3, url = "/platform/system/autotask/autoTaskList.do", paraentAlias = PlatformManageMenu.SYSTEM_MANAGE)
	public void autoTaskManage() {
	}
	
	@MenuMonitor(name = "调度任务新增", orders = 1, level = 4, paraentAlias = AUTOTASKMANAGE)
	@RequestMapping(name = "新增调度任务页面", value = "/platform/system/autotask/autoTaskCreate.do", method = RequestMethod.GET)
	public String autoTaskCreate() {
		return "platform/system/autotask/autoTaskCreate";
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务新增-保存新增数据", orders = 1, level = 5, paraentAlias = "autoTaskCreate")
	@OperateLog(message = "保存调度任务", optType = DataType.OptType.INSERT, service = AutoTaskService.class)
	@RequestMapping(name = "保存调度任务", value = "/platform/access/autoTask/autoTaskCreateSave.json", method = RequestMethod.POST)
	public MessageObject<AutoTask> autoTaskCreateSave(AutoTask autoTask) {
		MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
		try {
			autoTask.setStatus(IsDelete.NO);
			autoTaskService.saveEntity(autoTask);
			messageObject.openTip("新增调度任务成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务删除", orders = 2, level = 4, paraentAlias = AUTOTASKMANAGE)
	@OperateLog(message = "删除调度任务", optType = DataType.OptType.DELETE, service = AutoTaskService.class)
	@RequestMapping(name = "删除调度任务", value = "/platform/access/autoTask/autoTaskDelete.json", method = RequestMethod.POST)
	public MessageObject<AutoTask> autoTaskDelete(String id) {
		MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<AutoTask> autoTasks = Lists.newArrayList();
				for (String string : ids) {
					AutoTask autoTask = autoTaskService.get(string);
					autoTask.setStatus(IsDelete.YES);
					autoTasks.add(autoTask);
				}
				autoTaskService.saveBatch(autoTasks);
				messageObject.openTip("删除调度任务成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除调度任务异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "调度任务修改", orders = 3, level = 4, paraentAlias = AUTOTASKMANAGE)
	@RequestMapping(name = "修改调度任务页面", value = "/platform/system/autotask/autoTaskEdit.do", method = RequestMethod.GET)
	public String autoTaskEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/system/autotask/autoTaskEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务新增-获取数据详情", orders = 1, level = 5, paraentAlias = "autoTaskEdit")
	@RequestMapping(name = "获取调度任务详情", value = "/platform/access/autoTask/autoTaskEditJson.json", method = RequestMethod.POST)
	public MessageObject<AutoTask> autoTaskEditJson(String id) {
		MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
		try {
			AutoTask autoTask = autoTaskService.get(id);
			messageObject.ok("修改查询调度任务成功", autoTask);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("修改查询调度任务异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务新增-保存修改数据", orders = 2, level = 5, paraentAlias = "autoTaskEdit")
	@OperateLog(message = "修改调度任务", optType = DataType.OptType.UPDATE, service = AutoTaskService.class)
	@RequestMapping(name = "修改调度任务", value = "/platform/access/autoTask/autoTaskEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<AutoTask> autoTaskEditUpdate(AutoTask autoTask) {
		MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
		try {
			autoTask.setStatus(IsDelete.NO);
			autoTaskService.saveEntity(autoTask);
			messageObject.openTip("修改调度任务成功");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "调度任务列表", orders = 4, level = 4, paraentAlias = AUTOTASKMANAGE)
	@RequestMapping(name = "调度任务列表页面", value = "/platform/system/autotask/autoTaskList.do", method = RequestMethod.GET)
	public String autoTaskList() {
		return "platform/system/autotask/autoTaskList";
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务列表-获取分页数据", orders = 1, level = 5, paraentAlias = "autoTaskList")
	@RequestMapping(name = "调度任务列表分页", value = "/platform/access/autoTask/autoTaskList.json", method = { RequestMethod.POST })
	public MessageObject<AutoTask> autoTaskPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<AutoTask> tools = autoTaskService.queryPageByMap(map, support);
			messageObject.ok("查询调度任务成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询调度任务异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "调度任务列表-执行调度任务", orders = 2, level = 5, paraentAlias = "autoTaskList")
	@OperateLog(message = "执行调度任务", optType = DataType.OptType.EXCUTE, service = AutoTaskService.class)
	@RequestMapping(name = "执行调度任务", value = "/platform/access/autoTask/excuteTask.json", method = RequestMethod.POST)
	public MessageObject<Object> excuteTask(String id) {
		MessageObject<Object> messageObject = MessageObject.getDefaultInstance();
		AutoTask autoTask = autoTaskService.get(id);
		try {
			Class<? extends Job> clazz = Class.forName(autoTask.getClassName()).asSubclass(Job.class);
			String job_name = autoTask.getTaskName();
			if (autoTask.getStartStatus() == SysContants.IsStart.YES) {
				QuartzManager.removeJob(job_name);
				autoTask.setStartStatus(IsStart.NO);
				SpiderManager.removeSpider(clazz);
			} else {
				QuartzManager.addJob(job_name, clazz, autoTask.getScheduler());
				autoTask.setStartStatus(IsStart.YES);
			}
			autoTask = autoTaskService.saveEntity(autoTask);
			String msg = String.format("任务【%s】%s成功.", autoTask.getTaskName(),
					autoTask.getStartStatus() == IsStart.YES ? "启动" : "暂停");
			messageObject.openTip(msg);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = String.format("任务【%s】 %s失败.", autoTask.getTaskName(),
					autoTask.getStartStatus() != IsStart.YES ? "启动" : "停止");
			messageObject.error(msg);
		}
		return messageObject;
	}
}
