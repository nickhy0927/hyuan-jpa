package com.iss.platform.system.performance.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.platform.system.performance.entity.Performance;
import com.iss.platform.system.performance.service.PerformanceService;

@Controller
public class PerformanceController {
	
	@Autowired
	private PerformanceService performanceService;

	@ResponseBody
	@AccessAuthority(alias = "performance-list-json", name = "性能列表")
	@RequestMapping(value = "/platform/system/performance/list.json", method = { RequestMethod.POST })
	public MessageObject<Performance> performanceList(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Performance> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Performance> tools = performanceService.queryPageByMap(map, support);
			messageObject.ok("查询数据成功", tools);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询数据异常");
		}
		return messageObject;
	}
}
