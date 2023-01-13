package com.opencart.aspects;

import com.qualizeal.configurations.Reporter;
import com.qualizeal.pages.web.Page;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
public class LoggingAspect {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(LoggingAspect.class);
	private static final ThreadLocal<Page> currentContext = new ThreadLocal<>();

	@Pointcut("execution(* *(..))")
	public void componentLogInterceptor() {
		//intentionally left out
	}

	@Around("componentLogInterceptor()")
	public Object componentLogHandler(ProceedingJoinPoint pjp) throws Throwable {
		try {
			MDC.put("static", pjp.getThis().getClass().toString());
		} catch (Exception e) {
			//DO NOTHING
		}
		if (pjp.getThis() instanceof Page) {
			currentContext.set((Page) pjp.getThis());
		}
		String signature = String.format("%s(%s)", pjp.getSignature().getName(), Arrays.stream(pjp.getArgs()).map(Object::toString).collect(Collectors.joining(", ")));
		log.info(signature);
		return pjp.proceed();
	}

	@Around("execution(* *(..)) && @annotation(org.testng.annotations.AfterTest)")
	public Object afterTestHandler(ProceedingJoinPoint pjp) throws Throwable {
		currentContext.remove();
		return pjp.proceed();
	}

	@Around("execution(* *(..)) && @annotation(org.testng.annotations.BeforeSuite)")
	public Object beforeSuiteHandler(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}

	@Around("execution(* *(..)) && @annotation(org.testng.annotations.AfterSuite)")
	public Object afterSuiteHandler(ProceedingJoinPoint pjp) throws Throwable {
		return pjp.proceed();
	}

	@Around("execution(* *(..)) && @annotation(org.testng.annotations.Test)")
	public Object testHandler(ProceedingJoinPoint pjp) throws Throwable {
		MDC.put("test.name", pjp.getSignature().getName());
		return pjp.proceed();
	}

	@AfterThrowing(pointcut = "execution(* *(..)) && @annotation(org.testng.annotations.Test)", throwing = "ex")
	public void componentExceptionHandler(Throwable ex) {
		Reporter reporter = Reporter.getInstance();
		if (currentContext.get() != null) {
			reporter.attachScreenShot(currentContext.get().takeScreenshot());
		}
		reporter.error(ex);
		log.error(ex.getMessage());
	}
}