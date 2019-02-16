package com.iss.aspect;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.iss.aspect.anno.ServiceMonitor;
import com.iss.platform.system.joblog.entity.JobLog;
import com.iss.platform.system.joblog.service.JobLogService;

@Component
@Aspect
public class JobLogAspect {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JobLogService jobLogService;

	@Pointcut(value = "@annotation(com.iss.aspect.anno.ServiceMonitor)")
	private void pointcut() {

	}

	@Around(value = "pointcut() && @annotation(serviceMonitor)")
	public Object around(ProceedingJoinPoint point, ServiceMonitor serviceMonitor) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JobLog log = new JobLog();
		String startTime = format.format(new Date());
		LOG.debug(String.format("方法执行开始时间：%s", startTime));
		log.setStartTime(startTime);
		long start = System.currentTimeMillis();
		// 当前拦截的类和方法：
		Class<? extends Object> clazz = point.getTarget().getClass();
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		String methodName = clazz.getSimpleName() + "_" + method.getName();
		log.setMethodName(methodName);
		log.setMethodDesc(serviceMonitor.desc());
		log.setParams(JSON.toJSONString(point.getArgs()));
		// 方法返回结果
		Object result = null;
		try {
			// 执行方法（可以在方法前后添加前置和后置通知）
			result = point.proceed();
			log.setExcuteStatus(Boolean.TRUE);
		} catch (Throwable e) {
			// 打印堆栈信息
			e.printStackTrace();
			// 设置返回信息
			result = "结果：抛了异常了。。-----------------------" + e.getMessage() + "，原因：<br/>" + e.getCause().getMessage();
			log.setExcuteStatus(Boolean.FALSE);
			LOG.debug(result.toString());
		}
		String endTime = format.format(new Date());
		log.setEndTime(endTime);
		log.setResult(JSON.toJSONString(result));
		log.setEndTime(endTime);
		LOG.debug(String.format("方法执行结束时间：%s", endTime));
		long end = System.currentTimeMillis();
		log.setLastTime(end - start);
		LOG.debug(String.format("方法执行持续时间：%d ms", (end - start)));
		jobLogService.saveEntity(log);
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
