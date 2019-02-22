package com.iss.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.iss.common.exception.ServiceException;
import com.iss.constant.DataType;
import com.iss.oauth.user.UserPrincipal;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.system.exceptionlog.entity.ExceptionLog;
import com.iss.platform.system.exceptionlog.service.ExceptionLogService;

/**
 * 错误日志监控拦截器
 *
 * @author Hyuan
 */
@Component
public class ExceptionDispatcherResolver implements HandlerExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionDispatcherResolver.class);
    
    @Autowired
    private ExceptionLogService exceptionLogService;
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        ModelAndView view = null;
        String contextPath = request.getContextPath();
        String url = request.getRequestURI().toLowerCase().replaceAll(contextPath, "");
        String packageMethodName;
        if (object instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();
            String name = method.getDeclaringClass().getName();
            packageMethodName = name + "." + method.getName();
            LOGGER.info(packageMethodName);
            ExceptionLog exceptionLog = new ExceptionLog();
            User user = UserPrincipal.getContextUser();
            System.out.println("当前登录人是：" + user.getLoginName());
            exceptionLog.setMessage(exception.getLocalizedMessage());
            exceptionLog.setMethodName(packageMethodName);
            exceptionLog.setUrl(url);
            exceptionLog.setExceptionType(DataType.ExceptionType.getCode(exception));
            try {
				exceptionLogService.saveEntity(exceptionLog);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
        }
		return view;
    }
}
