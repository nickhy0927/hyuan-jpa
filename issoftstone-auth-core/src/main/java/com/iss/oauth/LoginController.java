package com.iss.oauth;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.iss.common.config.InitEnvironment;
import com.iss.common.exception.VerifyCodeException;
import com.iss.common.utils.JsonMapper;
import com.iss.common.utils.MessageObject;
import com.iss.orm.log.queue.LoggerQueue;
import com.iss.orm.log.websocket.WebsocketHandler;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.access.user.service.UserService;

/**
 * @author Administrator
 */
@Controller
public class LoginController {
	public static final String VALIDATE_CODE = "verifyCode";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(name = "用户登录", value = "/user/login.json", method = RequestMethod.POST)
	public MessageObject<UserDetails> login(String username, String password, HttpServletRequest request) {
		MessageObject<UserDetails> messageObject = MessageObject.getDefaultInstance();
		username = username.trim();
		try {
			String verification = request.getParameter("code");
			String captcha = request.getSession().getAttribute(VALIDATE_CODE).toString();
			if (verification == null || !captcha.contentEquals(verification)) {
				throw new VerifyCodeException("验证码不匹配");
			}
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);
			// 调用loadUserByUsername
			Authentication authentication = authenticationManager.authenticate(authRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = request.getSession();
			// 这个非常重要，否则验证后将无法登陆
			session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
			messageObject.ok("登陆成功", authentication.getName());
			InitEnvironment.setCurrentLoginName(authentication.getName());
			String name = authentication.getName();
			User user = userService.findUserByLoginName(name);
			user.setLastLoginTime(new Date());
			userService.updateUser(user);
			user.setPassword(null);
			LoggerQueue.getInstance().push(user);
			Map<String, Object> result = Maps.newConcurrentMap();
			result.put("code", 4000);
			result.put("msg", "你的账号在其他地方登录，非本人登录，请及时修改密码");
			WebsocketHandler.sendMessageToUser(name, new JsonMapper().toJson(result));
			return messageObject;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof LockedException) {
				messageObject.error("账户被锁定，请联系管理员");
			} else if (ex instanceof BadCredentialsException) {
				messageObject.error("用户名或密码错误！");
			} else if (ex instanceof VerifyCodeException) {
				messageObject.error("验证码输入错误，请重新输入");
			} else if (ex instanceof DisabledException) {
				messageObject.error("账户被禁用，请联系管理员");
			} else if (ex instanceof UsernameNotFoundException) {
				messageObject.error(ex.getMessage() + "，请重新输入");
			}
		}
		return messageObject;
	}

	@RequestMapping(name = "用户微信登录", value = "/three/user/wxLogin.do", method = RequestMethod.GET)
	public String wxLogin(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer("https://open.weixin.qq.com/connect/qrconnect?");
		try {
			buffer.append("appid=wxfd6965ab1fc6adb2");
			buffer.append("&redirect_uri=")
					.append(URLEncoder.encode("http://localhost:8081/issoftstone-deploy/wxIndex.do", "UTF-8"));
			buffer.append("&response_type=code");
			buffer.append("&scope=snsapi_userinfo");
			buffer.append("&state=" + request.getSession().getId());
			buffer.append("#wechat_redirect");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = buffer.toString();
		return "redirect:" + url;
	}

	@RequestMapping(name = "用户github登录", value = "/three/user/gitHubLogin.do", method = RequestMethod.GET)
	public String gitHubLogin(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer("https://github.com/login/oauth/authorize?");
		buffer.append("client_id=1d768b44da64bde676df");
		buffer.append("&state=").append(request.getSession().getId());
		buffer.append("&redirect_uri=").append("http://localhost:8081/issoftstone-deploy/wxIndex.do");
		String url = buffer.toString();
		return "redirect:" + url;
	}
}
