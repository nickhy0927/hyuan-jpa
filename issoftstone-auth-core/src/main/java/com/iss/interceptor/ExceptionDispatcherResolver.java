package com.iss.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.iss.anno.OperateLog;
import com.iss.common.utils.MessageObject;
import com.iss.oauth.user.UserPrincipal;
import com.iss.platform.access.user.entity.User;
import com.iss.platform.system.exceptionlog.ExceptionLog;

/**
 * 错误日志监控拦截器
 *
 * @author Hyuan
 */
@Component
public class ExceptionDispatcherResolver implements HandlerExceptionResolver {

    final private static Logger LOGGER = LoggerFactory.getLogger(ExceptionDispatcherResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) {
        ModelAndView view = null;
        MessageObject<ExceptionLog> messageObject = MessageObject.getDefaultInstance();
        String uri = request.getRequestURI().toLowerCase();
        String packageMethodName;
        if (object instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();
            String name = method.getDeclaringClass().getName();
            packageMethodName = name + "." + method.getName();
            LOGGER.info(packageMethodName);
            OperateLog log = method.getAnnotation(OperateLog.class);
            ExceptionLog exceptionLog = new ExceptionLog();
            User user = UserPrincipal.getContextUser();
            
        }
		return view;
    }
}
