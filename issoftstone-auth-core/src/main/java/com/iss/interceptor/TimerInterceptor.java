package com.iss.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.iss.common.anno.AccessAuthority;
import com.iss.common.utils.SysContants.IsDelete;
import com.iss.platform.system.performance.entity.Performance;
import com.iss.platform.system.performance.service.PerformanceService;

/**
 * 性能监控
 * 
 * @author Hyuan
 *
 */
public class TimerInterceptor implements HandlerInterceptor {

	@Autowired
	private PerformanceService performanceService;

	private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("WatchExecuteTime");

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		long beginTime = System.currentTimeMillis(); // 开始时间
		startTimeThreadLocal.set(beginTime);
		return true;
	}

	public void afterCompletion(HttpServletRequest req, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		long endTime = System.currentTimeMillis();
		long executeTime = endTime - startTimeThreadLocal.get();
		String reqUrl = req.getRequestURI().replaceAll(req.getContextPath(), "");
		System.out.println(String.format("请求：%s 执行的时间是： %d ms.", reqUrl, executeTime));
		StaticResources staticResources = StaticResources.getStaticResourcesInstance();
		List<String> urls = staticResources.getUrls();
		AntPathMatcher matcher = new AntPathMatcher();
		boolean bool = false;
		for (String url : urls) {
			if (matcher.match(url, reqUrl)) {
				bool = true;
			}
		}
		if (object instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            AccessAuthority accessAuthority = handlerMethod.getMethodAnnotation(AccessAuthority.class);
            if (!bool && accessAuthority != null) {
    			Performance entity = new Performance();
    			entity.setExecuteTime(executeTime);
    			entity.setAlias(accessAuthority.alias());
    			entity.setName(accessAuthority.name());
    			entity.setStatus(IsDelete.NO);
    			entity.setUrl(reqUrl);
    			performanceService.saveEntity(entity);
    		}
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}