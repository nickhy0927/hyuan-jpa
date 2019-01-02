package com.iss.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.iss.common.spring.SpringContextHolder;
import com.iss.common.utils.JsonMapper;
import com.iss.common.utils.WebUtils;
import com.iss.oauth.user.UserPrincipal;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.system.optlog.entity.OptLog;
import com.iss.platform.system.optlog.service.OptLogService;

public class OperateLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            OperateLog operateLog = handlerMethod.getMethodAnnotation(OperateLog.class);
            if (operateLog != null) {
            	OptLog log = new OptLog();
            	Map<String, Object> parameterMap = WebUtils.getRequestToMap(request);
            	log.setClazz(handlerMethod.getMethod().getDeclaringClass().getName() + "." + handlerMethod.getMethod().getName());
            	log.setMessage(operateLog.message());
            	log.setOptType(operateLog.optType());
            	User user = UserPrincipal.getContextUser();
                log.setUser(user);
            	String methodName = handlerMethod.getMethod().getName();
            	log.setMethod(methodName);
            	Class<?> service = operateLog.service();
                Object bean = SpringContextHolder.getBean(service);
                if (handlerMethod.getMethod().getName().toLowerCase().contains("delete")) {
                    Method method = bean.getClass().getMethod("get", new Class[] { Serializable.class });
                    String[] ids = request.getParameter("id").split(",");
					List<Object> objects = new ArrayList<>();
					for (String id : ids) {
                        Object result = method.invoke(bean, new Object[] { id });
                        if (result != null) {
                        	objects.add(result);
						}
					}
					log.setData(new JsonMapper().toJson(objects));
				} else {
                    log.setData(new JsonMapper().toJson(parameterMap));
                }
            	OptLogService optLogService = SpringContextHolder.getBean(OptLogService.class);
            	optLogService.save(log);
            	if (handlerMethod.getMethod().getName().toLowerCase().contains("insert")) {
            		Method method = bean.getClass().getMethod("insert", new Class[] { Object.class });
            		Class<?>[] parameterTypes = method.getParameterTypes();
            		for (Class<?> class1 : parameterTypes) {
						System.out.println(class1.getName());
					}
				}
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}