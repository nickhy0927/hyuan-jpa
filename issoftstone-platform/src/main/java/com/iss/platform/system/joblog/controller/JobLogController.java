package com.iss.platform.system.joblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.iss.platform.system.joblog.entity.JobLog;
import com.iss.platform.system.joblog.service.JobLogService;

@Controller
public class JobLogController {

	@Autowired
	private JobLogService jobLogService;
	
	public final static String JOBLOGMANAGE = "jobLogManage";

	@MenuMonitor(name = "调度任务日志管理", orders = 3, level = 3, url = "/platform/system/jobLog/jobLogList.do", paraentAlias = PlatformManageMenu.SYSTEM_MANAGE)
	public void jobLogManage() {
	}
	
	@MenuMonitor(name = "调度任务日志列表", orders = 2, level = 4, paraentAlias = JOBLOGMANAGE)
	@RequestMapping(name = "调度任务日志详情列表页面", value = "/platform/system/jobLog/jobLogList.do", method = RequestMethod.GET)
	public String jobLogList() {
		return "platform/system/joblog/jobLogList";
	}
	
	@ResponseBody
	@MenuMonitor(name = "调度任务日志列表-获取分页数据", orders = 1, level = 5, paraentAlias = "jobLogList")
	@RequestMapping(name = "调度任务日志列表", value = "/platform/system/jobLog/jobLogList.json", method = { RequestMethod.POST })
	public MessageObject<JobLog> jobLogPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<JobLog> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<JobLog> tools = jobLogService.queryPageByMap(map, support);
			messageObject.ok("查询调度任务日志成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询调度任务日志异常");
		}
		return messageObject;
	}
	
	@ResponseBody
	@MenuMonitor(name = "调度任务日志删除", orders = 1, level = 4, paraentAlias = JOBLOGMANAGE)
	@OperateLog(message = "删除调度任务日志", optType = DataType.OptType.DELETE, service = JobLogService.class)
	@RequestMapping(name = "删除调度任务日志", value = "/platform/system/jobLog/jobLogDelete.json", method = RequestMethod.POST)
	public MessageObject<JobLog> jobLogDelete(String id) {
		MessageObject<JobLog> messageObject = MessageObject.getDefaultInstance();
		try {
			String[] ids = id.split(",");
			if (ids.length > 0) {
				List<JobLog> jobLogs = Lists.newArrayList();
				for (String string : ids) {
					JobLog jobLog = jobLogService.get(string);
					jobLog.setStatus(IsDelete.YES);
					jobLogs.add(jobLog);
				}
				jobLogService.saveBatch(jobLogs);
				messageObject.openTip("删除调度任务日志成功", null);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("删除调度任务日志异常");
		}
		return messageObject;
	}
	
	@MenuMonitor(name = "调度任务日志查看", orders = 3, level = 4, paraentAlias = JOBLOGMANAGE)
	@RequestMapping(name = "调度任务日志详情页面", value = "/platform/system/jobLog/jobLogView.do", method = RequestMethod.GET)
	public String jobLogView() {
		return "platform/system/joblog/jobLogView";
	}
	
	@ResponseBody
	@MenuMonitor(name = "调度任务日志查看-获取数据详情", orders = 1, level = 5, paraentAlias = "jobLogView")
	@RequestMapping(name = "查看调度任务日志详情", value = "/platform/system/jobLog/jobLogView.json", method = RequestMethod.POST)
	public MessageObject<JobLog> jobLogViewDetail(String id) {
		MessageObject<JobLog> messageObject = MessageObject.getDefaultInstance();
		try {
			JobLog jobLog = jobLogService.get(id);
			messageObject.ok("查询调度任务日志详情成功", jobLog);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询调度任务日志详情异常");
		}
		return messageObject;
	}
}
