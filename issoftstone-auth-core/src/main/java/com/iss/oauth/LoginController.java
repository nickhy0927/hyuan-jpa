package com.iss.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iss.common.utils.MessageObject;

@Controller
@RequestMapping(value = "/user")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@ResponseBody
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	public MessageObject<UserDetails> login(String username, String password, HttpServletRequest request) {
		MessageObject<UserDetails> messageObject = MessageObject.getDefaultInstance();
		username = username.trim();
		try {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = authenticationManager.authenticate(authRequest); // 调用loadUserByUsername
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession();
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
			messageObject.ok("登陆成功", authentication.getName());
			return messageObject;
		} catch (LockedException e) {
			messageObject.error("账户被锁定，请联系管理员");
		} catch (AuthenticationException ex) {
			ex.printStackTrace();
			messageObject.error("用户名或密码错误！");
		}
		return messageObject;
	}
}
