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
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.system.joblog.entity.JobLog;
import com.iss.platform.system.joblog.service.JobLogService;

@Controller
public class JobLogController {

	@Autowired
	private JobLogService jobLogService;
	
	@AccessAuthority(alias = "jobLog-list", name = "进入调度任务日志详情列表页面")
	@RequestMapping(value = "/platform/system/jobLog/jobLogList.do", method = RequestMethod.GET)
	public String jobLogList() {
		return "platform/system/joblog/jobLogList";
	}
	
	@ResponseBody
	@AccessAuthority(alias = "jobLog-view", name = "查看调度任务日志详情")
	@RequestMapping(value = "/platform/system/jobLog/jobLogView.json", method = RequestMethod.POST)
	public MessageObject<JobLog> jobLogView(String id) {
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

	@ResponseBody
	@AccessAuthority(alias = "jobLog-list", name = "调度任务日志列表")
	@RequestMapping(value = "/platform/system/jobLog/jobLogList.json", method = { RequestMethod.POST })
	public MessageObject<JobLog> jobLogList(HttpServletRequest request, PageSupport support) {
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
	@AccessAuthority(alias = "jobLog-delete", name = "删除调度任务日志详情信息")
	@OperateLog(message = "删除调度任务日志信息", method = "jobLogDelete", optType = DataType.OptType.DELETE, service = JobLogService.class)
	@RequestMapping(value = "/platform/system/jobLog/jobLogDelete.json", method = RequestMethod.POST)
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
}
