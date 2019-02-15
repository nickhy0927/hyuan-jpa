package com.iss.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Sets;
import com.iss.common.config.InitEnvironment;
import com.iss.common.utils.MessageObject;
import com.iss.oauth.user.UserPrincipal;

public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UserPrincipal.setRequest(request);
		MessageObject<Object> messageObject = MessageObject.getDefaultInstance();
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			String[] menus = {};
			Set<String> aliasList = Sets.newHashSet();
			for (String alias : menus) {
				aliasList.add(alias);
			}
			RequestMapping access = method.getAnnotation(RequestMapping.class);
			String uri = request.getRequestURI();
			StaticResources staticResources = StaticResources.getStaticResourcesInstance();
			List<String> urls = staticResources.getUrls();
			for (String url : urls) {
				if (uri.contains(url)) {
					return true;
				}
			}
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object principal = authentication.getPrincipal();
			if (principal == null || principal.toString().equals("anonymousUser")) {
				response.sendRedirect("/login.jsp");
				return false;
			}
			UserDetails userDetails = (UserDetails) principal;
			if (InitEnvironment.getInitUsername().equals(userDetails.getUsername())) {
				return true;
			}
			String alias = method.getName();
			if (aliasList.contains(alias)) {
				return true;
			}

//			if (uri.lastIndexOf(".json") > 0) {
//				messageObject.error("你没有操作权限访问【" + access.name() + "】");
//				messageObject.returnData(response, messageObject);
//				return false;
//			} else {
//				request.setAttribute("url", uri);
//				request.getRequestDispatcher("/unauth.do").forward(request, response);
//				return false;
//			}
			return true;
		}
		return true;
	}
}
