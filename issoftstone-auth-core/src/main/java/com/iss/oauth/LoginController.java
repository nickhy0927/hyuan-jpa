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

import com.iss.common.exception.VerifyCodeException;
import com.iss.common.utils.MessageObject;

/**
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/user")
public class LoginController {
	public static final String VALIDATE_CODE = "verifyCode";

	@Autowired
	private AuthenticationManager authenticationManager;

	@ResponseBody
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	public MessageObject<UserDetails> login(String username, String password, HttpServletRequest request) {
		MessageObject<UserDetails> messageObject = MessageObject.getDefaultInstance();
		username = username.trim();
		try {
			String verification = request.getParameter("code");
			String captcha = request.getSession().getAttribute(VALIDATE_CODE).toString();
			if (verification == null || !captcha.contentEquals(verification)) {
				throw new VerifyCodeException("验证码不匹配");
			}
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
			// 调用loadUserByUsername
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession();
			// 这个非常重要，否则验证后将无法登陆
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			messageObject.ok("登陆成功", authentication.getName());
			return messageObject;
		} catch (Exception ex) {
			if (ex instanceof LockedException) {
				messageObject.error("账户被锁定，请联系管理员");
			}
			if (ex instanceof AuthenticationException) {
				messageObject.error("用户名或密码错误！");
			}
			if (ex instanceof VerifyCodeException) {
				messageObject.error("验证码输入错误，请重新输入");
			}
		} 
		return messageObject;
	}
}
