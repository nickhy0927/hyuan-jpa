package com.iss.oauth.platform.access.menu;

import com.iss.common.anno.AccessAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ViewMenuController {
	
	@AccessAuthority(alias = "menu-save", name = "菜单新增页面")
	@RequestMapping(value = "/platform/access/menu/create.do", method = RequestMethod.GET)
	public String create() {
		return "platform/access/menu/create";
	}
	
	@AccessAuthority(alias = "menu-edit", name = "菜单修改页面")
	@RequestMapping(value = "/platform/access/menu/edit.do", method = RequestMethod.GET)
	public String edit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/menu/edit";
	}

	@AccessAuthority(alias = "menu-list", name = "菜单列表页面")
	@RequestMapping(value = "/platform/access/menu/list.do", method = RequestMethod.GET)
	public String list() {
		return "platform/access/menu/list";
	}
}
