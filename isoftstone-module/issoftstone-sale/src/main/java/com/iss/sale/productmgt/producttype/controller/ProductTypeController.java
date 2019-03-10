package com.iss.sale.productmgt.producttype.controller;

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
import com.iss.sale.productmgt.producttype.entity.ProductType;
import com.iss.sale.productmgt.producttype.service.ProductTypeService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ProductTypeController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductTypeController.class);

	public final static String PRODUCTTYPE_MANAGE = "productTypeManage";
	
	private final ProductTypeService productTypeService;

	@Autowired
	public ProductTypeController(ProductTypeService productTypeService) {
		this.productTypeService = productTypeService;
	}
	
	@MenuMonitor(name = "类别管理", orders = 1, level = 3, url = "/productmgt/productType/productTypeList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void productTypeManage() {
	}
	
	@MenuMonitor(name = "产品类别新增", orders = 1, level = 4, paraentAlias = PRODUCTTYPE_MANAGE)
	@RequestMapping(name = "产品类别新增页面", value = "/productmgt/productType/productTypeCreate.do", method = RequestMethod.GET)
	public String productTypeCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/productType/productTypeCreate";
	}
	
	@ResponseBody
	@MenuMonitor(name = "产品类别新增-保存新增数据", orders = 2, level = 5, paraentAlias = "productTypeCreate")
	@OperateLog(message = "保存产品类别", optType = DataType.OptType.INSERT, service = ProductTypeService.class)
	@RequestMapping(name = "保存产品类别", value = "/productmgt/productType/productTypeCreateSave.json", method = RequestMethod.POST)
	public MessageObject<ProductType> productTypeCreateSave(ProductType productType) {
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			productType.setStatus(IsDelete.NO);
			productTypeService.saveEntity(productType);
			messageObject.openTip("新增产品类别成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品类别删除", orders = 2, level = 4, paraentAlias = PRODUCTTYPE_MANAGE)
	@OperateLog(message = "删除产品类别", optType = DataType.OptType.DELETE, service = ProductTypeService.class)
	@RequestMapping(name = "删除产品类别", value = "/productmgt/productType/productTypeDetete.json", method = { RequestMethod.POST })
	public MessageObject<ProductType> productTypeDetete(String id) {
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<ProductType> productTypes = Lists.newArrayList();
				for (String string : ids) {
					ProductType productType = productTypeService.get(string);
					productType.setStatus(IsDelete.YES);
					productTypes.add(productType);
				}
				productTypeService.saveBatch(productTypes);
				messageObject.openTip("删除产品类别成功");
			} else {
				messageObject.error("删除产品类别异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除ProductType异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品类别修改", orders = 3, level = 4, paraentAlias = PRODUCTTYPE_MANAGE)
	@RequestMapping(name = "产品类别修改页面", value = "/productmgt/productType/productTypeEdit.do", method = RequestMethod.GET)
	public String productTypeEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/productType/productTypeEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "产品类别修改-获取数据详情", orders = 1, level = 5, paraentAlias = "productTypeEdit")
	@RequestMapping(name = "获取产品类别", value = "/productmgt/productType/productTypeEditJson.json", method = RequestMethod.POST)
	public MessageObject<ProductType> productTypeEditJson(String id) {
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取产品类别成功", productTypeService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取产品类别异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品类别修改-保存修改数据", orders = 2, level = 5, paraentAlias = "productTypeEdit")
	@OperateLog(message = "修改产品类别", optType = DataType.OptType.UPDATE, service = ProductTypeService.class)
	@RequestMapping(name = "修改保存ProductType",value = "/productmgt/productType/productTypeEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<ProductType> productTypeEditUpdate(ProductType productType) {
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			productType.setStatus(IsDelete.NO);
			productTypeService.saveEntity(productType);
			messageObject.openTip("修改产品类别成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品类别列表", orders = 4, level = 4, paraentAlias = PRODUCTTYPE_MANAGE)
	@RequestMapping(name = "产品类别列表页面", value = "/productmgt/productType/productTypeList.do")
	public String productTypeList() {
		return "productmgt/productType/productTypeList";
	}

	@ResponseBody
	@MenuMonitor(name = "产品类别列表-获取数据分页", orders = 1, level = 5, paraentAlias = "productTypeList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/productType/productTypeList.json", method = { RequestMethod.POST })
	public MessageObject<ProductType> productTypePage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<ProductType> pagerInfo = productTypeService.queryPageByMap(map, support);
			messageObject.ok("查询产品类别分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询产品类别分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
