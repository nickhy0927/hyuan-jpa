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
	@RequestMapping(value = "/platform/access/user/userCreate.do")
	public String userCreate() {
		return "platform/access/user/userCreate";
	}
	
	@AccessAuthority(alias = "user-save", name = "用户修改页面")
	@RequestMapping(value = "/platform/access/user/userEdit.do")
	public String userEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/user/userEdit";
	}

	@AccessAuthority(alias = "user-list", name = "用户修改页面")
	@RequestMapping(value = "/platform/access/user/list.do")
	public String userList() {
		return "platform/access/user/userList";
	}
}
