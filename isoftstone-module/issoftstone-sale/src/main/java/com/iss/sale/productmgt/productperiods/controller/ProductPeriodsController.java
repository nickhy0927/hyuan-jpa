package com.iss.sale.productmgt.productperiods.controller;

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
import com.iss.sale.productmgt.productperiods.entity.ProductPeriods;
import com.iss.sale.productmgt.productperiods.service.ProductPeriodsService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ProductPeriodsController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductPeriodsController.class);

	public final static String PRODUCTPERIODS_MANAGE = "productPeriodsManage";
	
	private final ProductPeriodsService productPeriodsService;

	@Autowired
	public ProductPeriodsController(ProductPeriodsService productPeriodsService) {
		this.productPeriodsService = productPeriodsService;
	}
	
	@MenuMonitor(name = "周期管理", orders = 3, level = 3, url = "/productmgt/productPeriods/productPeriodsList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void productPeriodsManage() {
	}
	
	@MenuMonitor(name = "生产周期新增", orders = 1, level = 4, paraentAlias = PRODUCTPERIODS_MANAGE)
	@RequestMapping(name = "生产周期新增页面", value = "/productmgt/productPeriods/productPeriodsCreate.do", method = RequestMethod.GET)
	public String productPeriodsCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/productPeriods/productPeriodsCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "生产周期新增-保存新增数据", orders = 2, level = 5, paraentAlias = "productPeriodsCreate")
	@OperateLog(message = "保存生产周期", optType = DataType.OptType.INSERT, service = ProductPeriodsService.class)
	@RequestMapping(name = "保存生产周期", value = "/productmgt/productPeriods/productPeriodsCreateSave.json", method = RequestMethod.POST)
	public MessageObject<ProductPeriods> productPeriodsCreateSave(ProductPeriods productPeriods) {
		MessageObject<ProductPeriods> messageObject = MessageObject.getDefaultInstance();
		try {
			productPeriods.setStatus(IsDelete.NO);
			productPeriodsService.saveEntity(productPeriods);
			messageObject.openTip("新增生产周期成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "生产周期删除", orders = 2, level = 4, paraentAlias = PRODUCTPERIODS_MANAGE)
	@OperateLog(message = "删除生产周期", optType = DataType.OptType.DELETE, service = ProductPeriodsService.class)
	@RequestMapping(name = "删除生产周期", value = "/productmgt/productPeriods/productPeriodsDetete.json", method = { RequestMethod.POST })
	public MessageObject<ProductPeriods> productPeriodsDetete(String id) {
		MessageObject<ProductPeriods> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<ProductPeriods> productPeriodss = Lists.newArrayList();
				for (String string : ids) {
					ProductPeriods productPeriods = productPeriodsService.get(string);
					productPeriods.setStatus(IsDelete.YES);
					productPeriodss.add(productPeriods);
				}
				productPeriodsService.saveBatch(productPeriodss);
				messageObject.openTip("删除生产周期成功");
			} else {
				messageObject.error("删除生产周期异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除ProductPeriods异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "生产周期修改", orders = 3, level = 4, paraentAlias = PRODUCTPERIODS_MANAGE)
	@RequestMapping(name = "生产周期修改页面", value = "/productmgt/productPeriods/productPeriodsEdit.do", method = RequestMethod.GET)
	public String productPeriodsEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/productPeriods/productPeriodsEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "生产周期修改-获取数据详情", orders = 1, level = 5, paraentAlias = "productPeriodsEdit")
	@RequestMapping(name = "获取生产周期", value = "/productmgt/productPeriods/productPeriodsEditJson.json", method = RequestMethod.POST)
	public MessageObject<ProductPeriods> productPeriodsEditJson(String id) {
		MessageObject<ProductPeriods> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取生产周期成功", productPeriodsService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取生产周期异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "生产周期修改-保存修改数据", orders = 2, level = 5, paraentAlias = "productPeriodsEdit")
	@OperateLog(message = "修改生产周期", optType = DataType.OptType.UPDATE, service = ProductPeriodsService.class)
	@RequestMapping(name = "修改保存ProductPeriods",value = "/productmgt/productPeriods/productPeriodsEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<ProductPeriods> productPeriodsEditUpdate(ProductPeriods productPeriods) {
		MessageObject<ProductPeriods> messageObject = MessageObject.getDefaultInstance();
		try {
			productPeriods.setStatus(IsDelete.NO);
			productPeriodsService.saveEntity(productPeriods);
			messageObject.openTip("修改生产周期成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "生产周期列表", orders = 4, level = 4, paraentAlias = PRODUCTPERIODS_MANAGE)
	@RequestMapping(name = "生产周期列表页面", value = "/productmgt/productPeriods/productPeriodsList.do")
	public String productPeriodsList() {
		return "productmgt/productPeriods/productPeriodsList";
	}

	@ResponseBody
	@MenuMonitor(name = "生产周期列表-获取数据分页", orders = 1, level = 5, paraentAlias = "productPeriodsList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/productPeriods/productPeriodsList.json", method = { RequestMethod.POST })
	public MessageObject<ProductPeriods> productPeriodsPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<ProductPeriods> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<ProductPeriods> pagerInfo = productPeriodsService.queryPageByMap(map, support);
			messageObject.ok("查询生产周期分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询生产周期分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
