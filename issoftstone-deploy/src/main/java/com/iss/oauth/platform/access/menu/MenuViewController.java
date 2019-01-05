package com.iss.oauth.platform.access.menu;

import com.iss.common.anno.AccessAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MenuViewController {
	
	@AccessAuthority(alias = "menu-create-do")
	@RequestMapping(value = "/platform/access/menu/create.do", method = RequestMethod.GET)
	public String create() {
		return "platform/access/menu/create";
	}
	
	@AccessAuthority(alias = "menu-edit-do")
	@RequestMapping(value = "/platform/access/menu/edit.do", method = RequestMethod.GET)
	public String edit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/menu/edit";
	}

	@AccessAuthority(alias = "menu-list-do")
	@RequestMapping(value = "/platform/access/menu/list.do", method = RequestMethod.GET)
	public String list() {
		return "platform/access/menu/list";
	}
}
