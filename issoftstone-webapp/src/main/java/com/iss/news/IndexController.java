package com.iss.news;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.iss.common.exception.ServiceException;
import com.iss.common.utils.MessageObject;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

@Controller
public class IndexController {

	private final UserService userService;

	@Autowired
	public IndexController(UserService userService) {
		this.userService = userService;
	}

	@ResponseBody
	@RequestMapping(value = "/queryUserInfo.json", method = RequestMethod.GET)
	public MessageObject<User> queryUserInfo() {
		Map<String, Object> paramMap = Maps.newConcurrentMap();
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		try {
			List<User> users = userService.queryByMap(paramMap);
			messageObject.ok("查询用户列表成功", users);
		} catch (ServiceException e) {
			e.printStackTrace();
			messageObject.error("查询用户列表失败");
		}
		return messageObject;
	}
	
	@ResponseBody
	@RequestMapping(value = "/platform/access/user/userlist.json", method = RequestMethod.GET)
	public MessageObject<User> userList() {
		MessageObject<User> messageObject = MessageObject.getDefaultInstance();
		Map<String, Object> paramMap = Maps.newConcurrentMap();
		paramMap.put("status_eq", IsDelete.NO);
		try {
			List<User> list = userService.queryByMap(paramMap);
			messageObject.ok("查询用户信息成功", list);
		} catch (Exception e) {
			e.printStackTrace();
			messageObject.error("查询用户信息失败");
		}
		return messageObject;
	}
}
