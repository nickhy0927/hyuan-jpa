package com.iss.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Sets;
import com.iss.common.anno.AccessAuthority;
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
			AccessAuthority access = method.getAnnotation(AccessAuthority.class);
			String uri = request.getRequestURI();
			StaticResources staticResources = StaticResources.getStaticResourcesInstance();
			List<String> urls = staticResources.getUrls();
			for (String url : urls) {
				if (uri.contains(url)) {
					return true;
				}
			}
			HttpSession session = request.getSession();
			Object object = session.getAttribute("SPRING_SECURITY_CONTEXT");
			SecurityContextImpl securityContextImpl = (SecurityContextImpl) object;
			String username = securityContextImpl.getAuthentication().getName();
			if (InitEnvironment.getInitUsername().equals(username)) {
				return true;
			}
			if (access != null) {
//				String alias = access.alias();
//				if (aliasList.contains(alias)) {
//					return true;
//				}
				return true;
			} else {
				String msg = method.getDeclaringClass().getName() + "." + method.getName();
				messageObject.error(msg + " 方法没有添加AccessAuthority权限注解");
				messageObject.returnData(response, messageObject);
				return false;
			}
//			if (uri.lastIndexOf(".json") > 0) {
//				messageObject.error("你没有操作权限访问，如需操作，请联系管理员");
//				messageObject.returnData(response, messageObject);
//				return false;
//			} else {
//				request.setAttribute("url", uri);
//				request.getRequestDispatcher("/unauth.do").forward(request, response);
//				return false;
//			}
		}
		return true;
	}
}
