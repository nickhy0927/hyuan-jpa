package com.iss.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iss.aspect.anno.ServiceMonitor;
import com.iss.oauth.user.UserPrincipal;
import com.iss.platform.init.DataSourceHolder;

@Component
@Aspect
public class DataSourceAspect {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Pointcut(value = "execution(* com.iss.*.service.*.*(..))")
	private void pointcut() {

	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint point) {
		// 方法返回结果
		Object result = null;
		try {
			// 执行方法（可以在方法前后添加前置和后置通知）
			result = point.proceed();
			String dataSourceId = "dataSource";
			if (UserPrincipal.getContextUser() != null) {
				dataSourceId = UserPrincipal.getContextUser().getDataSourceId();
				DataSourceHolder.setDataSource(dataSourceId);
			}
			System.out.println(dataSourceId);
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
		System.out.println("----> afterReturning");
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The method " + methodName + " return with " + result);
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

}
