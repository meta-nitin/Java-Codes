package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before(value = "execution(* com.example.demo.Controller.*(..))")
	public void convertCsvToXlsxBeforeAspect(JoinPoint joinPoint) {
		logger.info(joinPoint.getSignature() + " called");
	}
	
	@Around(value = "execution(* com.example.demo.Controller.*(..))")
	public void convertCsvToXlsxAroundAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		logger.info("Total time taken for the execution of " + joinPoint.getSignature() + " method = " +totalTime);
	}
}
