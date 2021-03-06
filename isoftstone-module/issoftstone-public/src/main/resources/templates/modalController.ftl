package ${packages}.${domainObjectName?lower_case}.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
${aliasPackage}
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.orm.anno.MenuMonitor;
import ${packages}.${domainObjectName?lower_case}.entity.${domainObjectName};
import ${packages}.${domainObjectName?lower_case}.service.${domainObjectName}Service;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ${domainObjectName}Controller {
	
	private static Logger logger = LoggerFactory.getLogger(${domainObjectName}Controller.class);

	public final static String ${domainObjectName?upper_case}_MANAGE = "${domainObjectName?uncap_first}Manage";
	
	private final ${domainObjectName}Service ${domainObjectName?uncap_first}Service;

	@Autowired
	public ${domainObjectName}Controller(${domainObjectName}Service ${domainObjectName?uncap_first}Service) {
		this.${domainObjectName?uncap_first}Service = ${domainObjectName?uncap_first}Service;
	}
	
	@MenuMonitor(name = "${desc}管理", orders = 1, level = 3, url = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}List.do", paraentAlias = ${parentAlias})
	public void ${domainObjectName?uncap_first}Manage() {
	}
	
	@MenuMonitor(name = "${desc}新增", orders = 1, level = 4, paraentAlias = ${domainObjectName?upper_case}_MANAGE)
	@RequestMapping(name = "${desc}新增页面", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}Create.do", method = RequestMethod.GET)
	public String ${domainObjectName?uncap_first}Create(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}Create";
	}
	
	@ResponseBody
	@MenuMonitor(name = "${desc}新增-保存新增数据", orders = 2, level = 5, paraentAlias = "${domainObjectName?uncap_first}Create")
	@OperateLog(message = "保存${desc}", optType = DataType.OptType.INSERT, service = ${domainObjectName}Service.class)
	@RequestMapping(name = "保存${desc}", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}CreateSave.json", method = RequestMethod.POST)
	public MessageObject<${domainObjectName}> ${domainObjectName?uncap_first}CreateSave(${domainObjectName} ${domainObjectName?uncap_first}) {
		MessageObject<${domainObjectName}> messageObject = MessageObject.getDefaultInstance();
		try {
			${domainObjectName?uncap_first}.setStatus(IsDelete.NO);
			${domainObjectName?uncap_first}Service.saveEntity(${domainObjectName?uncap_first});
			messageObject.openTip("新增${desc}成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "${desc}删除", orders = 2, level = 4, paraentAlias = ${domainObjectName?upper_case}_MANAGE)
	@OperateLog(message = "删除${desc}", optType = DataType.OptType.DELETE, service = ${domainObjectName}Service.class)
	@RequestMapping(name = "删除${desc}", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}Detete.json", method = { RequestMethod.POST })
	public MessageObject<${domainObjectName}> ${domainObjectName?uncap_first}Detete(String id) {
		MessageObject<${domainObjectName}> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<${domainObjectName}> ${domainObjectName?uncap_first}s = Lists.newArrayList();
				for (String string : ids) {
					${domainObjectName} ${domainObjectName?uncap_first} = ${domainObjectName?uncap_first}Service.get(string);
					${domainObjectName?uncap_first}.setStatus(IsDelete.YES);
					${domainObjectName?uncap_first}s.add(${domainObjectName?uncap_first});
				}
				${domainObjectName?uncap_first}Service.saveBatch(${domainObjectName?uncap_first}s);
				messageObject.openTip("删除${desc}成功");
			} else {
				messageObject.error("删除${desc}异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除${domainObjectName}异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "${desc}修改", orders = 3, level = 4, paraentAlias = ${domainObjectName?upper_case}_MANAGE)
	@RequestMapping(name = "${desc}修改页面", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}Edit.do", method = RequestMethod.GET)
	public String ${domainObjectName?uncap_first}Edit(String id, Model model) {
		model.addAttribute("id", id);
		return "${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}Edit";
	}

	@ResponseBody
	@MenuMonitor(name = "${desc}修改-获取数据详情", orders = 1, level = 5, paraentAlias = "${domainObjectName?uncap_first}Edit")
	@RequestMapping(name = "获取${desc}", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}EditJson.json", method = RequestMethod.POST)
	public MessageObject<${domainObjectName}> ${domainObjectName?uncap_first}EditJson(String id) {
		MessageObject<${domainObjectName}> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取${desc}成功", ${domainObjectName?uncap_first}Service.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取${desc}异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "${desc}修改-保存修改数据", orders = 2, level = 5, paraentAlias = "${domainObjectName?uncap_first}Edit")
	@OperateLog(message = "修改${desc}", optType = DataType.OptType.UPDATE, service = ${domainObjectName}Service.class)
	@RequestMapping(name = "修改保存${domainObjectName}",value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}EditUpdate.json", method = RequestMethod.POST)
	public MessageObject<${domainObjectName}> ${domainObjectName?uncap_first}EditUpdate(${domainObjectName} ${domainObjectName?uncap_first}) {
		MessageObject<${domainObjectName}> messageObject = MessageObject.getDefaultInstance();
		try {
			${domainObjectName?uncap_first}.setStatus(IsDelete.NO);
			${domainObjectName?uncap_first}Service.saveEntity(${domainObjectName?uncap_first});
			messageObject.openTip("修改${desc}成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "${desc}列表", orders = 4, level = 4, paraentAlias = ${domainObjectName?upper_case}_MANAGE)
	@RequestMapping(name = "${desc}列表页面", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}List.do")
	public String ${domainObjectName?uncap_first}List() {
		return "${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}List";
	}

	@ResponseBody
	@MenuMonitor(name = "${desc}列表-获取数据分页", orders = 1, level = 5, paraentAlias = "${domainObjectName?uncap_first}List")
	@RequestMapping(name = "获取数据分页", value = "/${pathSuffix}/${domainObjectName?uncap_first}/${domainObjectName?uncap_first}List.json", method = { RequestMethod.POST })
	public MessageObject<${domainObjectName}> ${domainObjectName?uncap_first}Page(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<${domainObjectName}> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<${domainObjectName}> pagerInfo = ${domainObjectName?uncap_first}Service.queryPageByMap(map, support);
			messageObject.ok("查询${desc}分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询${desc}分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
