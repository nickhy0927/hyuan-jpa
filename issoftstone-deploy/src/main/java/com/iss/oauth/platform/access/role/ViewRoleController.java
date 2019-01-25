package com.iss.oauth.platform.access.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iss.common.anno.AccessAuthority;

/**
 * @author Hyuan
 */
@Controller
public class ViewRoleController {

	@AccessAuthority(alias = "role-create", name = "角色新增页面")
	@RequestMapping(value = "/platform/access/role/roleCreate.do")
	public String create(Model model) {
		model.addAttribute("code", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		return "platform/access/role/roleCreate";
	}
	@AccessAuthority(alias = "role-edit", name = "角色新增页面")
	@RequestMapping(value = "/platform/access/role/roleEdit.do")
	public String roleEdit(String id, Model model) {
		model.addAttribute("id", id);
		return "platform/access/role/roleEdit";
	}

	@AccessAuthority(alias = "role-list", name = "角色修改页面")
	@RequestMapping(value = "/platform/access/role/list.do")
	public String list() {
		return "platform/access/role/roleList";
	}
}
