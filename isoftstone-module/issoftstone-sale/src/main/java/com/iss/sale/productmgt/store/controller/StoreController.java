package com.iss.sale.productmgt.store.controller;

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
import com.iss.sale.productmgt.store.entity.Store;
import com.iss.sale.productmgt.store.service.StoreService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class StoreController {
	
	private static Logger logger = LoggerFactory.getLogger(StoreController.class);

	public final static String STORE_MANAGE = "storeManage";
	
	private final StoreService storeService;

	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}
	
	@MenuMonitor(name = "出库管理", orders = 5, level = 3, url = "/productmgt/store/storeList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void storeManage() {
	}
	
	@MenuMonitor(name = "产品出库新增", orders = 1, level = 4, paraentAlias = STORE_MANAGE)
	@RequestMapping(name = "产品出库新增页面", value = "/productmgt/store/storeCreate.do", method = RequestMethod.GET)
	public String storeCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/store/storeCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "产品出库新增-保存新增数据", orders = 2, level = 5, paraentAlias = "storeCreate")
	@OperateLog(message = "保存产品出库", optType = DataType.OptType.INSERT, service = StoreService.class)
	@RequestMapping(name = "保存产品出库", value = "/productmgt/store/storeCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Store> storeCreateSave(Store store) {
		MessageObject<Store> messageObject = MessageObject.getDefaultInstance();
		try {
			store.setStatus(IsDelete.NO);
			storeService.saveEntity(store);
			messageObject.openTip("新增产品出库成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品出库删除", orders = 2, level = 4, paraentAlias = STORE_MANAGE)
	@OperateLog(message = "删除产品出库", optType = DataType.OptType.DELETE, service = StoreService.class)
	@RequestMapping(name = "删除产品出库", value = "/productmgt/store/storeDetete.json", method = { RequestMethod.POST })
	public MessageObject<Store> storeDetete(String id) {
		MessageObject<Store> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Store> stores = Lists.newArrayList();
				for (String string : ids) {
					Store store = storeService.get(string);
					store.setStatus(IsDelete.YES);
					stores.add(store);
				}
				storeService.saveBatch(stores);
				messageObject.openTip("删除产品出库成功");
			} else {
				messageObject.error("删除产品出库异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除Store异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品出库修改", orders = 3, level = 4, paraentAlias = STORE_MANAGE)
	@RequestMapping(name = "产品出库修改页面", value = "/productmgt/store/storeEdit.do", method = RequestMethod.GET)
	public String storeEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/store/storeEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "产品出库修改-获取数据详情", orders = 1, level = 5, paraentAlias = "storeEdit")
	@RequestMapping(name = "获取产品出库", value = "/productmgt/store/storeEditJson.json", method = RequestMethod.POST)
	public MessageObject<Store> storeEditJson(String id) {
		MessageObject<Store> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取产品出库成功", storeService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取产品出库异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品出库修改-保存修改数据", orders = 2, level = 5, paraentAlias = "storeEdit")
	@OperateLog(message = "修改产品出库", optType = DataType.OptType.UPDATE, service = StoreService.class)
	@RequestMapping(name = "修改保存Store",value = "/productmgt/store/storeEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Store> storeEditUpdate(Store store) {
		MessageObject<Store> messageObject = MessageObject.getDefaultInstance();
		try {
			store.setStatus(IsDelete.NO);
			storeService.saveEntity(store);
			messageObject.openTip("修改产品出库成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品出库列表", orders = 4, level = 4, paraentAlias = STORE_MANAGE)
	@RequestMapping(name = "产品出库列表页面", value = "/productmgt/store/storeList.do")
	public String storeList() {
		return "productmgt/store/storeList";
	}

	@ResponseBody
	@MenuMonitor(name = "产品出库列表-获取数据分页", orders = 1, level = 5, paraentAlias = "storeList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/store/storeList.json", method = { RequestMethod.POST })
	public MessageObject<Store> storePage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Store> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Store> pagerInfo = storeService.queryPageByMap(map, support);
			messageObject.ok("查询产品出库分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询产品出库分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
