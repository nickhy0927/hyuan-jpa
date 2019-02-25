package com.iss.oauth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iss.common.anno.AccessAuthority;
import com.iss.platform.access.menu.entity.MenuTree;
import com.iss.platform.access.menu.service.MenuService;

@Controller
public class IndexController {

    private final MenuService menuService;

    @Autowired
    public IndexController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 进入首页
     * @return
     */
    @RequestMapping(name = "主页", value = "index.do", method = RequestMethod.GET)
    public String index(Model model) {
    	List<MenuTree> menuTrees = menuService.queryMenuTree();
    	model.addAttribute("menus", menuTrees);
        return "index";
    }
    
    @RequestMapping(name = "日志监控log", value = "/log.do", method = RequestMethod.GET)
    public String log() {
    	return "/log/log";
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
