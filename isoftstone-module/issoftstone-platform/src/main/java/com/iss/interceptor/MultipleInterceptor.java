package com.iss.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.iss.aspect.anno.OperateLog;

public class MultipleInterceptor implements HandlerInterceptor {
	
	private String componentScan;

	public void setComponentScan(String componentScan) {
		this.componentScan = componentScan;
	}
	
	private String[] getPackages() {
		return componentScan.split(",");
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            OperateLog operateLog = handlerMethod.getMethodAnnotation(OperateLog.class);
            Class<?> service = operateLog.service();
            String[] packages = getPackages();
            String clazzName = service.getName();
			boolean flag = false;
			for (String p : packages) {
				if (clazzName.contains(p)) {
					flag = true;
					break;
				}
			}
			System.out.println("是否访问默认数据库：" + (flag));
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
