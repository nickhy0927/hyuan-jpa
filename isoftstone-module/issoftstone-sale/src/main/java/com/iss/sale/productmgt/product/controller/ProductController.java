package com.iss.sale.productmgt.product.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.iss.aspect.anno.OperateLog;
import com.iss.common.utils.GroupOne;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.PageSupport;
import com.iss.common.utils.PagerInfo;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.common.valid.ValidationMessage;
import com.iss.common.utils.WebUtils;
import com.iss.constant.DataType;
import com.iss.constants.SaleManageMenu;
import com.iss.orm.anno.MenuMonitor;
import com.iss.sale.productmgt.product.entity.Product;
import com.iss.sale.productmgt.product.service.ProductService;
import com.iss.sale.productmgt.producttype.entity.ProductType;
import com.iss.sale.productmgt.producttype.service.ProductTypeService;

/**
 * Created by Hyuan on 2015/9/21.
 */
@Controller
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	public final static String PRODUCT_MANAGE = "productManage";

	private final ProductService productService;

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@MenuMonitor(name = "产品管理", orders = 1, level = 3, url = "/productmgt/product/productList.do", paraentAlias = SaleManageMenu.PRODUCT_LEVELS_MANAGE)
	public void productManage() {
	}

	@MenuMonitor(name = "产品新增", orders = 4, level = 4, paraentAlias = PRODUCT_MANAGE)
	@RequestMapping(name = "产品新增页面", value = "/productmgt/product/productCreate.do", method = RequestMethod.GET)
	public String productCreate(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "productmgt/product/productCreate";
	}

	@MenuMonitor(name = "产品新增", orders = 1, level = 5, paraentAlias = "productCreate")
	@RequestMapping(name = "产品新增页面", value = "/productmgt/product/createProductTypeList.do", method = RequestMethod.GET)
	public String createProductTypeList() {
		return "productmgt/product/productTypeList";
	}

	@ResponseBody
	@MenuMonitor(name = "产品新增-获取数据分页", orders = 2, level = 5, paraentAlias = "productCreate")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/product/createProductTypePage.json", method = {
			RequestMethod.POST })
	public MessageObject<ProductType> createProductTypePage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<ProductType> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<ProductType> pagerInfo = productTypeService.queryPageByMap(map, support);
			messageObject.ok("查询产品分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询产品分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品新增-保存新增数据", orders = 3, level = 5, paraentAlias = "productCreate")
	@OperateLog(message = "保存产品", optType = DataType.OptType.INSERT, service = ProductService.class)
	@RequestMapping(name = "保存产品", value = "/productmgt/product/productCreateSave.json", method = RequestMethod.POST)
	public MessageObject<Product> productCreateSave(@Valid Product product, BindingResult result) {
		MessageObject<Product> messageObject = MessageObject.getDefaultInstance();
		try {
			String productTypeId = product.getProductTypeId();
			product.setStatus(IsDelete.NO);
			String message = ValidationMessage.message(product, GroupOne.class);
			System.out.println(message);
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}
			product.setProductType(productTypeService.get(productTypeId));
			productService.saveEntity(product);
			messageObject.openTip("新增产品成功");
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("新增产品失败");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品删除", orders = 2, level = 4, paraentAlias = PRODUCT_MANAGE)
	@OperateLog(message = "删除产品", optType = DataType.OptType.DELETE, service = ProductService.class)
	@RequestMapping(name = "删除产品", value = "/productmgt/product/productDetete.json", method = { RequestMethod.POST })
	public MessageObject<Product> productDetete(String id) {
		MessageObject<Product> messageObject = MessageObject.getDefaultInstance();
		try {
			if (StringUtils.isNotEmpty(id)) {
				String[] ids = id.split(",");
				List<Product> products = Lists.newArrayList();
				for (String string : ids) {
					Product product = productService.get(string);
					product.setStatus(IsDelete.YES);
					products.add(product);
				}
				productService.saveBatch(products);
				messageObject.openTip("删除产品成功");
			} else {
				messageObject.error("删除产品异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			messageObject.error("删除Product异常");
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品修改", orders = 3, level = 4, paraentAlias = PRODUCT_MANAGE)
	@RequestMapping(name = "产品修改页面", value = "/productmgt/product/productEdit.do", method = RequestMethod.GET)
	public String productEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "productmgt/product/productEdit";
	}

	@ResponseBody
	@MenuMonitor(name = "产品修改-获取数据详情", orders = 1, level = 5, paraentAlias = "productEdit")
	@RequestMapping(name = "获取产品", value = "/productmgt/product/productEditJson.json", method = RequestMethod.POST)
	public MessageObject<Product> productEditJson(String id) {
		MessageObject<Product> messageObject = MessageObject.getDefaultInstance();
		try {
			messageObject.ok("获取产品成功", productService.get(id));
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("获取产品异常");
		}
		return messageObject;
	}

	@ResponseBody
	@MenuMonitor(name = "产品修改-保存修改数据", orders = 2, level = 5, paraentAlias = "productEdit")
	@OperateLog(message = "修改产品", optType = DataType.OptType.UPDATE, service = ProductService.class)
	@RequestMapping(name = "修改保存Product", value = "/productmgt/product/productEditUpdate.json", method = RequestMethod.POST)
	public MessageObject<Product> productEditUpdate(Product product) {
		MessageObject<Product> messageObject = MessageObject.getDefaultInstance();
		try {
			product.setStatus(IsDelete.NO);
			productService.saveEntity(product);
			messageObject.openTip("修改产品成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageObject;
	}

	@MenuMonitor(name = "产品列表", orders = 4, level = 4, paraentAlias = PRODUCT_MANAGE)
	@RequestMapping(name = "产品列表页面", value = "/productmgt/product/productList.do")
	public String productList() {
		return "productmgt/product/productList";
	}

	@ResponseBody
	@MenuMonitor(name = "产品列表-获取数据分页", orders = 1, level = 5, paraentAlias = "productList")
	@RequestMapping(name = "获取数据分页", value = "/productmgt/product/productList.json", method = { RequestMethod.POST })
	public MessageObject<Product> productPage(HttpServletRequest request, PageSupport support) {
		Map<String, Object> map = WebUtils.getRequestToMap(request);
		MessageObject<Product> messageObject = MessageObject.getDefaultInstance();
		try {
			map.put("status_eq", IsDelete.NO);
			PagerInfo<Product> pagerInfo = productService.queryPageByMap(map, support);
			messageObject.ok("查询产品分页成功", pagerInfo);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询产品分页异常");
			logger.error(e.getMessage());
		}
		return messageObject;
	}
}
