package com.iss.oauth.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.iss.aspect.anno.ServiceMonitor;
import com.iss.interceptor.StaticResources;
import com.iss.oauth.init.DataSourceHolder;
import com.iss.orm.log.websocket.WebsocketHandler;
import com.iss.platform.access.user.entity.User;

@Aspect
@Component
public class DataSourceAspect {

	private static String componentScan;

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public String[] getPackages() {
		return componentScan.split(",");
	}

	@Pointcut(value = "execution(* com.iss.*.service.*.*(..))")
	private void pointcut() {

	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint point) {
		StringBuilder sb = new StringBuilder();
		String dataSourceId = "dataSource";
		// 方法返回结果
		Object result = null;
		try {
			String classType = point.getTarget().getClass().getName();
			Class<?> clazz = Class.forName(classType);
			String clazzName = clazz.getName();
			String[] packages = getPackages();
			boolean flag = false;
			for (String p : packages) {
				if (clazzName.contains(p)) {
					flag = true;
					break;
				}
			}
			LOG.debug("接口实现类是：" + clazzName);
			if (!flag) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication != null) {
					Object principal = authentication.getPrincipal();
					if (principal instanceof UserDetails) {
						UserDetails userDetails = (UserDetails) principal;
						User contextUser = StaticResources.userListSet.get(userDetails.getUsername());
						if (contextUser != null) {
							dataSourceId = contextUser.getDataSourceId();
						}
					}
				}
			}
			DataSourceHolder.putDataSource(dataSourceId);
			LOG.debug("是否使用默认数据源：" + (flag));
			// 执行方法（可以在方法前后添加前置和后置通知）
			result = point.proceed();
			try {
				WebsocketHandler.broadcastLog(sb.toString());
			} catch (Exception e1) {
				System.out.println("出现异常了");
			}
		} catch (Throwable e) {
			// 打印堆栈信息
			e.printStackTrace();
			// 设置返回信息
			result = "结果：抛了异常了。。-----------------------" + e.getMessage() + "，原因：<br/>" + e.getCause().getMessage();
			LOG.debug(result.toString());
		}
		// 返回通知
		return result;
	}

	/**
	 * 方法执行后
	 * 
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(value = "pointcut() && @annotation(serviceMonitor)", returning = "result")
	public Object afterReturning(JoinPoint joinPoint, ServiceMonitor serviceMonitor, Object result) {
		String methodName = joinPoint.getSignature().getName();
		LOG.debug("The method " + methodName + " return with " + result);
		if (result instanceof Boolean) {
			if (!((Boolean) result)) {
				result = "error----result is false";
			}
		} else {
			if (result == null) {
				result = "error----result is null";
			}
		}
		return result;
	}

	public void setComponentScan(String componentScan) {
		DataSourceAspect.componentScan = componentScan;
	}

}
