package com.iss.sale.productmgt.storereason.controller;

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
import com.iss.constants.SaleManageMenu;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.orm.anno.MenuMonitor;
import com.iss.sale.productmgt.storereason.entity.StoreReason;
import com.iss.sale.productmgt.storereason.service.StoreReasonService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class StoreReasonController {
	
	private static Logger logger = LoggerFactory.getLogger(StoreReasonController.class);

	public final static String STOREREASON_MANAGE = "storeReasonManage";
	
	private final StoreReasonService storeReasonService;

	@Autowired
	public StoreReasonController(StoreReasonService storeReasonService) {
		this.storeReasonService = storeReasonService;
	}
	
	@MenuMonitor(name = "出/入库理由管理", orders = 6, level = 3, url = "/productmgt/storeReason/storeReasonList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void storeReasonManage() {
	}
	
	@MenuMonitor(name = "出/入库理由新增", orders = 1, level = 4, paraentAlias = STOREREASON_MANAGE)
	@RequestMapping(name = "出/入库理由新增页面", value = "/productmgt/storeReason/storeReasonCreate.do", method = RequestMethod.GET)
	public String storeReasonCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/storeReason/storeReasonCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "出/入库理由新增-保存新增数据", orders = 2, level = 5, paraentAlias = "storeReasonCreate")
	@OperateLog(message = "保存出/入库理由", optType = DataType.OptType.INSERT, service = StoreReasonService.class)
	@RequestMapping(name = "保存出/入库理由", value = "/productmgt/storeReason/storeReasonCreateSave.json", method = RequestMethod.POST)
	public MessageObject<StoreReason> storeReasonCreateSave(StoreReason storeReason) {
		MessageObject<StoreReason> messageObject = MessageObject.getDefaultInstance();
		try {
			storeReason.setStatus(IsDelete.NO);
			storeReasonService.saveEntity(storeReason);
			messageObject.openTip("新增出/入库理由成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "出/入库理由删除", orders = 2, level = 4, paraentAlias = STOREREASON_MANAGE)
	@OperateLog(message = "删除出/入库理由", optType = DataType.OptType.DELETE, service = StoreReasonService.class)
	@RequestMapping(name = "删除出/入库理由", value = "/productmgt/storeReason/storeReasonDetete.json", method = { RequestMethod.POST })
	public MessageObject<StoreReason> storeReasonDetete(String id) {
		MessageObject<StoreReason> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<StoreReason> storeReasons = Lists.newArrayList();
				for (String string : ids) {
					StoreReason storeReason = storeReasonService.get(string);
					storeReason.setStatus(IsDelete.YES);
					storeReasons.add(storeReason);
				}
				storeReasonService.saveBatch(storeReasons);
				messageObject.openTip("删除出/入库理由成功");
			} else {
				messageObject.error("删除出/入库理由异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除StoreReason异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "出/入库理由修改", orders = 3, level = 4, paraentAlias = STOREREASON_MANAGE)
	@RequestMapping(name = "出/入库理由修改页面", value = "/productmgt/storeReason/storeReasonEdit.do", method = RequestMethod.GET)
	public String storeReasonEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/storeReason/storeReasonEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "出/入库理由修改-获取数据详情", orders = 1, level = 5, paraentAlias = "storeReasonEdit")
	@RequestMapping(name = "获取出/入库理由", value = "/productmgt/storeReason/storeReasonEditJson.json", method = RequestMethod.POST)
	public MessageObject<StoreReason> storeReasonEditJson(String id) {
		MessageObject<StoreReason> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取出/入库理由成功", storeReasonService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取出/入库理由异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "出/入库理由修改-保存修改数据", orders = 2, level = 5, paraentAlias = "storeReasonEdit")
	@OperateLog(message = "修改出/入库理由", optType = DataType.OptType.UPDATE, service = StoreReasonService.class)
	@RequestMapping(name = "修改保存StoreReason",value = "/productmgt/storeReason/storeReasonEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<StoreReason> storeReasonEditUpdate(StoreReason storeReason) {
		MessageObject<StoreReason> messageObject = MessageObject.getDefaultInstance();
		try {
			storeReason.setStatus(IsDelete.NO);
			storeReasonService.saveEntity(storeReason);
			messageObject.openTip("修改出/入库理由成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "出/入库理由列表", orders = 4, level = 4, paraentAlias = STOREREASON_MANAGE)
	@RequestMapping(name = "出/入库理由列表页面", value = "/productmgt/storeReason/storeReasonList.do")
	public String storeReasonList() {
		return "productmgt/storeReason/storeReasonList";
	}

	@ResponseBody
	@MenuMonitor(name = "出/入库理由列表-获取数据分页", orders = 1, level = 5, paraentAlias = "storeReasonList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/storeReason/storeReasonList.json", method = { RequestMethod.POST })
	public MessageObject<StoreReason> storeReasonPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<StoreReason> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<StoreReason> pagerInfo = storeReasonService.queryPageByMap(map, support);
			messageObject.ok("查询出/入库理由分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询出/入库理由分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
