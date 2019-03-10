package com.iss.sale.productmgt.productqulity.controller;

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
import com.iss.sale.productmgt.productqulity.entity.ProductQulity;
import com.iss.sale.productmgt.productqulity.service.ProductQulityService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ProductQulityController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductQulityController.class);

	public final static String PRODUCTQULITY_MANAGE = "productQulityManage";
	
	private final ProductQulityService productQulityService;

	@Autowired
	public ProductQulityController(ProductQulityService productQulityService) {
		this.productQulityService = productQulityService;
	}
	
	@MenuMonitor(name = "质量管理", orders = 2, level = 3, url = "/productmgt/productQulity/productQulityList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void productQulityManage() {
	}
	
	@MenuMonitor(name = "产品质量新增", orders = 1, level = 4, paraentAlias = PRODUCTQULITY_MANAGE)
	@RequestMapping(name = "产品质量新增页面", value = "/productmgt/productQulity/productQulityCreate.do", method = RequestMethod.GET)
	public String productQulityCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/productQulity/productQulityCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "产品质量新增-保存新增数据", orders = 2, level = 5, paraentAlias = "productQulityCreate")
	@OperateLog(message = "保存产品质量", optType = DataType.OptType.INSERT, service = ProductQulityService.class)
	@RequestMapping(name = "保存产品质量", value = "/productmgt/productQulity/productQulityCreateSave.json", method = RequestMethod.POST)
	public MessageObject<ProductQulity> productQulityCreateSave(ProductQulity productQulity) {
		MessageObject<ProductQulity> messageObject = MessageObject.getDefaultInstance();
		try {
			productQulity.setStatus(IsDelete.NO);
			productQulityService.saveEntity(productQulity);
			messageObject.openTip("新增产品质量成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品质量删除", orders = 2, level = 4, paraentAlias = PRODUCTQULITY_MANAGE)
	@OperateLog(message = "删除产品质量", optType = DataType.OptType.DELETE, service = ProductQulityService.class)
	@RequestMapping(name = "删除产品质量", value = "/productmgt/productQulity/productQulityDetete.json", method = { RequestMethod.POST })
	public MessageObject<ProductQulity> productQulityDetete(String id) {
		MessageObject<ProductQulity> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<ProductQulity> productQulitys = Lists.newArrayList();
				for (String string : ids) {
					ProductQulity productQulity = productQulityService.get(string);
					productQulity.setStatus(IsDelete.YES);
					productQulitys.add(productQulity);
				}
				productQulityService.saveBatch(productQulitys);
				messageObject.openTip("删除产品质量成功");
			} else {
				messageObject.error("删除产品质量异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除ProductQulity异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品质量修改", orders = 3, level = 4, paraentAlias = PRODUCTQULITY_MANAGE)
	@RequestMapping(name = "产品质量修改页面", value = "/productmgt/productQulity/productQulityEdit.do", method = RequestMethod.GET)
	public String productQulityEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/productQulity/productQulityEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "产品质量修改-获取数据详情", orders = 1, level = 5, paraentAlias = "productQulityEdit")
	@RequestMapping(name = "获取产品质量", value = "/productmgt/productQulity/productQulityEditJson.json", method = RequestMethod.POST)
	public MessageObject<ProductQulity> productQulityEditJson(String id) {
		MessageObject<ProductQulity> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取产品质量成功", productQulityService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取产品质量异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品质量修改-保存修改数据", orders = 2, level = 5, paraentAlias = "productQulityEdit")
	@OperateLog(message = "修改产品质量", optType = DataType.OptType.UPDATE, service = ProductQulityService.class)
	@RequestMapping(name = "修改保存ProductQulity",value = "/productmgt/productQulity/productQulityEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<ProductQulity> productQulityEditUpdate(ProductQulity productQulity) {
		MessageObject<ProductQulity> messageObject = MessageObject.getDefaultInstance();
		try {
			productQulity.setStatus(IsDelete.NO);
			productQulityService.saveEntity(productQulity);
			messageObject.openTip("修改产品质量成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品质量列表", orders = 4, level = 4, paraentAlias = PRODUCTQULITY_MANAGE)
	@RequestMapping(name = "产品质量列表页面", value = "/productmgt/productQulity/productQulityList.do")
	public String productQulityList() {
		return "productmgt/productQulity/productQulityList";
	}

	@ResponseBody
	@MenuMonitor(name = "产品质量列表-获取数据分页", orders = 1, level = 5, paraentAlias = "productQulityList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/productQulity/productQulityList.json", method = { RequestMethod.POST })
	public MessageObject<ProductQulity> productQulityPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<ProductQulity> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<ProductQulity> pagerInfo = productQulityService.queryPageByMap(map, support);
			messageObject.ok("查询产品质量分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询产品质量分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
