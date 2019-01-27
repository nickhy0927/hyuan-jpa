package com.iss.oauth.platform.access.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iss.common.anno.AccessAuthority;
import com.iss.common.utils.JsonMapper;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

/**
 * @author Hyuan
 */
@Controller
public class ViewUserController {

	@Autowired
	private UserService userSerice;

	@AccessAuthority(alias = "user-role-list", name = "获取用户权限信息")
	@RequestMapping(value = "/platform/access/user/userRoleList.do", method = RequestMethod.GET)
	public String userRoleList(String userId, Model model) {
		model.addAttribute("userId", userId);
		User user = userSerice.get(userId);
		if (user != null)
			model.addAttribute("roles", new JsonMapper().toJson(user.getRoles()));
		return "platform/access/user/roleList";
	}

	@AccessAuthority(alias = "user-save", name = "用户新增页面")
	@RequestMapping(value = "/platform/access/user/userCreate.do")
	public String userCreate() {
		return "platform/access/user/userCreate";
	}

	@AccessAuthority(alias = "user-edit", name = "用户修改页面")
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
