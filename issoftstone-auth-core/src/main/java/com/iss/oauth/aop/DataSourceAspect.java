package com.iss.oauth.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iss.aspect.anno.ServiceMonitor;
import com.iss.common.utils.JsonMapper;
import com.iss.oauth.init.DataSourceHolder;
import com.iss.oauth.user.UserPrincipal;
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

	/**
	 * 当Sql执行时间超过该值时，则进行log warn级别题型，否则记录INFO日志。
	 */
	private long warnWhenOverTime = 2 * 60 * 1000L;

	private static String arrayToString(Object[] a) {
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append("[ ");
		for (int i = 0;; i++) {
			if (a[i] instanceof Object[]) {
				b.append(arrayToString((Object[]) a[i]));
			} else {
				b.append(new JsonMapper().toJson(a[i]));
			}
			if (i == iMax)
				return b.append(" ]").toString();
			b.append(", ");
		}
	}

	@Around(value = "pointcut()")
	public Object around(ProceedingJoinPoint point) {
		long startTime = System.currentTimeMillis();
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
				User contextUser = UserPrincipal.getContextUser();
				if (contextUser != null) {
					dataSourceId = contextUser.getDataSourceId();
					DataSourceHolder.DATASOURCEID = dataSourceId;
				}

			}
			LOG.debug("是否使用默认数据源：" + (flag));
			// 执行方法（可以在方法前后添加前置和后置通知）
			result = point.proceed();
			Signature signature = point.getSignature();
		    MethodSignature ms = (MethodSignature)signature;
		    Method m = ms.getMethod();
			long costTime = System.currentTimeMillis() - startTime;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (costTime > warnWhenOverTime) {
				sb.append("开始时间：").append(format.format(new Date())).append("<br>\n");
				sb.append("执行方法：").append(clazzName).append("_").append(m.getName()).append("<br>\n");
				sb.append("持续时间： ").append(costTime).append("ms.").append("<br>\n");
				sb.append("结束时间：").append(format.format(new Date())).append("<br>\n");
				sb.append("-------------------------------------------------------------------\n");
				LOG.warn(sb.toString());
			} else if (LOG.isInfoEnabled()) {
				sb.append("开始时间：").append(format.format(new Date())).append("<br>\n");
				sb.append("执行方法：").append(clazzName).append("_").append(m.getName()).append("<br>\n");
				sb.append("执行参数： ").append(arrayToString(point.getArgs())).append("<br>\n");
				sb.append("持续时间： ").append(costTime).append("ms.").append("<br>\n");
				sb.append("结束时间：").append(format.format(new Date())).append("<br>\n");
				sb.append("-------------------------------------------------------------------\n");
				LOG.info(sb.toString());
			}
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
