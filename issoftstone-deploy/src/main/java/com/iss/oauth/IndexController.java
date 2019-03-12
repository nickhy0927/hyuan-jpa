package com.iss.oauth;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iss.common.anno.AccessAuthority;
import com.iss.common.config.InitEnvironment;
import com.iss.common.utils.JsonMapper;
import com.iss.common.utils.WebUtils;
import com.iss.constant.PlatformManageMenu;
import com.iss.job.spider.OschainService;
import com.iss.orm.anno.MenuMonitor;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.service.MenuService;

/**
 * @author Hyuan
 */
@Controller
public class IndexController {

    private final MenuService menuService;
    
    @Autowired
    private OschainService oschainService;

    @Autowired
    public IndexController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping
    @MenuMonitor(name = "首页", orders = 0, level = 1, paraentAlias = PlatformManageMenu.TOP, shows = false)
	public void homeManage() {

	}
    /**
     * 进入首页
     * @return
     */
    @MenuMonitor(name = "首页", orders = 1, level = 2, paraentAlias = "homeManage", shows = false)
    @RequestMapping(name = "主页", value = "/index.do", method = RequestMethod.GET)
    public String index(Model model) {
    	List<MenuTree> menuTrees = menuService.queryIndexMenuList();
    	model.addAttribute("menus", menuTrees);
    	model.addAttribute("username", InitEnvironment.getCurrentLoginName());
    	oschainService.init();
        return "index";
    }

    @RequestMapping(name = "微信认证首页", value = "/wxIndex.do", method = RequestMethod.GET)
    public String wxIndex(HttpServletRequest request) {
    	Map<String, Object> requestToMap = WebUtils.getRequestParamterToMap(request);
    	request.setAttribute("params", new JsonMapper().toJson(requestToMap));
    	return "wxIndex";
    }
    
    @RequestMapping(name = "日志监控", value = "/tomcatLog.do", method = RequestMethod.GET)
    public String tomcatLog() {
    	return "/log/tomcatLog";
    }
    
    @AccessAuthority(alias = "ztree", name = "树结构")
    @RequestMapping(value = "ztree.do", method = RequestMethod.GET)
    public String ztree() {
    	return "ztree";
    }
}
