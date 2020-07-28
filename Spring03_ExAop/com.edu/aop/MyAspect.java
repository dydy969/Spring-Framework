package com.edu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.edu.entity.Rectangle;
import com.edu.entity.Triangle;


@Aspect
public class MyAspect {

	
	@Before("execution(public * viewSize(..))")
	public void before(JoinPoint join) {
		System.out.println("도형의 넓이를 구한다");
	}
	
	@After("execution(public * viewSize(..))")
	public void after(JoinPoint join) {
		System.out.println("도형의 넓이를 출력한다");

	}
	
	


	
	
	
	
	
}
