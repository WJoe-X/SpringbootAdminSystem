package com.lenovo.bootstrap.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Description:aop日志管理
 * 
 * @author WJoe
 * @time 下午2:40:09
 */
@Aspect
@Component
public class TimeLogAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeLogAspect.class);

	private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();

	/**
	 * 切点为自定义注解TimeLog
	 */
	@Pointcut("@annotation(com.lenovo.bootstrap.aspect.TimeLog)")
	public void controllerAspect() {
	}

	@Before(value = "controllerAspect()")
	public void before(JoinPoint joinPoint) {
		startTime.set(System.currentTimeMillis());
		LOGGER.debug("----进入  {}  类的   {} 方法 ", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName());
	}

	@After(value = "controllerAspect()")
	public void after(JoinPoint joinPoint) {
		LOGGER.info("----after  {} 类的   {} 方法", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName());
		LOGGER.info("after  耗时（毫秒） : {} ", (System.currentTimeMillis() - startTime.get()));
	}

	@AfterReturning(value = "controllerAspect()", returning = "result")
	public void afterReturning(JoinPoint joinPoint) {
		LOGGER.info("----return   {}  类的    {}  方法    耗时（毫秒） : {}  ", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName(), (System.currentTimeMillis() - startTime.get()));
	}

	@AfterThrowing(value = "controllerAspect()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		LOGGER.error("----throwing   {}  类的    {}  方法    耗时（毫秒） : {} ", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName(), (System.currentTimeMillis() - startTime.get()));
	}

}
