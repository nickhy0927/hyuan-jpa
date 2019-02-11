package com.iss.platform.system.autotask.controller;

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
import com.google.common.collect.Maps;
import com.iss.anno.OperateLog;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.platform.system.autotask.entity.AutoTask;
import com.iss.platform.system.autotask.service.AutoTaskService;

@Controller
public class AutoTaskController {
	@Autowired
	private AutoTaskService autoTaskService;
	
	@AccessAuthority(alias = "autoTask-save", name = "新增定时任务")
	@RequestMapping(value = "/platform/system/autotask/autoTaskCreate.do", method = RequestMethod.GET)
	public String autoTaskCreate() {
		return "platform/system/autotask/autoTaskCreate";
	}
	
	@AccessAuthority(alias = "autoTask-edit", name = "修改定时任务")
	@RequestMapping(value = "/platform/system/autotask/autoTaskEdit.do", method = RequestMethod.GET)
	public String autoTaskEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/system/autotask/autoTaskEdit";
	}
	
	@AccessAuthority(alias = "autoTask-edit", name = "保存定时任务")
	@RequestMapping(value = "/platform/system/autotask/autoTaskList.do", method = RequestMethod.GET)
	public String autoTaskList() {
		return "platform/system/autotask/autoTaskCreate";
	}
	
	@AccessAuthority(alias = "autoTask-view", name = "查看定时任务")
	@RequestMapping(value = "/platform/system/autotask/autoTaskView.do", method = RequestMethod.GET)
	public String autoTaskView() {
		return "platform/system/autotask/autoTaskView";
	}
	
	@ResponseBody
    @AccessAuthority(alias = "autoTask-save|autoTask-edit", name = "保存定时任务")
    @OperateLog(message = "保存定时任务", method = "autoTaskSave", optType = DataType.OptType.INSERT, service = AutoTaskService.class)
    @RequestMapping(value = "/platform/access/autoTask/autoTaskSave.json", method = RequestMethod.POST)
    public MessageObject<AutoTask> autoTaskSave(AutoTask autoTask) {
        MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
        try {
        	String id = autoTask.getId();
            autoTask.setStatus(IsDelete.NO);
            autoTaskService.saveEntity(autoTask);
            if (StringUtils.isNotEmpty(id)) {
				messageObject.openTip("新增定时任务成功");
			} else {
				messageObject.openTip("修改定时任务成功");
			}
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return messageObject;
    }

    @ResponseBody
    @AccessAuthority(alias = "queryAutoTaskList", name = "获取所有定时任务")
    @RequestMapping(value = "/platform/access/autoTask/queryAutoTaskList.json", method = RequestMethod.GET)
    public Map<String, Object> queryAutoTaskList(HttpServletRequest request, PageSupport support) {
        Map<String, Object> paramMap = WebUtils.getRequestToMap(request);
        Map<String, Object> maps = Maps.newConcurrentMap();
        support.setLimit(20);
        paramMap.put("status_eq", IsDelete.NO);
        PagerInfo<AutoTask> pagerInfo = autoTaskService.queryPageByMap(paramMap, support);
        maps.put("incomplete_results", pagerInfo.getContent().size() > 0);
        maps.put("total_count", pagerInfo.getTotals());
        maps.put("results", pagerInfo.getContent());
        return maps;
    }

    @ResponseBody
    @AccessAuthority(alias = "autoTask-edit", name = "修改定时任务")
    @RequestMapping(value = "/platform/access/autoTask/autoTaskEdit.json", method = RequestMethod.POST)
    public MessageObject<AutoTask> autoTaskEdit(String id) {
        MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
        try {
            AutoTask autoTask = autoTaskService.get(id);
            messageObject.ok("修改查询定时任务成功", autoTask);
        } catch (ServiceException e) {
            e.printStackTrace();
            messageObject.error("修改查询定时任务异常");
        }
        return messageObject;
    }

    @ResponseBody
    @AccessAuthority(alias = "autoTask-list", name = "定时任务列表")
    @RequestMapping(value = "/platform/access/autoTask/autoTaskList.json", method = {RequestMethod.POST})
    public MessageObject<AutoTask> autoTaskList(HttpServletRequest request, PageSupport support) {
        Map<String, Object> map = WebUtils.getRequestToMap(request);
        MessageObject<AutoTask> messageObject = MessageObject.getDefaultInstance();
        try {
            map.put("status_eq", IsDelete.NO);
            PagerInfo<AutoTask> tools = autoTaskService.queryPageByMap(map, support);
            messageObject.ok("查询定时任务成功", tools);
        } catch (ServiceException e) {
            e.printStackTrace();
            messageObject.error("查询定时任务异常");
        }
        return messageObject;
    }

    @ResponseBody
    @AccessAuthority(alias = "autoTask-delete", name = "删除定时任务")
    @OperateLog(message = "删除定时任务", method = "autoTaskDelete", optType = DataType.OptType.DELETE, service = AutoTaskService.class)
    @RequestMapping(value = "/platform/access/autoTask/autoTaskDelete.json", method = RequestMethod.POST)
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
                messageObject.openTip("删除定时任务成功", null);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            messageObject.error("删除定时任务异常");
        }
        return messageObject;
    }
}
