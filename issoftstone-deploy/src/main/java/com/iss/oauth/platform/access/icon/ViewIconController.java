package com.iss.oauth.platform.access.icon;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iss.common.anno.AccessAuthority;

@Controller
public class ViewIconController {

	public ViewIconController() {
	}

	@AccessAuthority(alias = "icon-save", name = "图标新增页面")
	@RequestMapping(value = "/platform/access/icon/create.do", method = RequestMethod.GET)
	public String create() {
		return "platform/access/icon/create";
	}
	
	@AccessAuthority(alias = "icon-edit", name = "图标修改页面")
	@RequestMapping(value = "/platform/access/icon/edit.do", method = RequestMethod.GET)
	public String edit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/icon/edit";
	}

	@AccessAuthority(alias = "icon-list", name = "图标列表页面")
	@RequestMapping(value = "/platform/access/icon/list.do", method = RequestMethod.GET)
	public String list() {
		return "platform/access/icon/list";
	}
}
