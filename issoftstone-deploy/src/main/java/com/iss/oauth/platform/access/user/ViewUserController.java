package com.iss.oauth.platform.access.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iss.common.anno.AccessAuthority;

/**
 * @author Hyuan
 */
@Controller
public class ViewUserController {

	@AccessAuthority(alias = "user-save", name = "用户新增页面")
	@RequestMapping(value = "/platform/access/user/create.do")
	public String create() {
		return "platform/access/user/create";
	}
	
	@AccessAuthority(alias = "user-save", name = "用户修改页面")
	@RequestMapping(value = "/platform/access/user/edit.do")
	public String edit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/user/edit";
	}

	@AccessAuthority(alias = "user-list", name = "用户修改页面")
	@RequestMapping(value = "/platform/access/user/list.do")
	public String list() {
		return "platform/access/user/list";
	}
}
