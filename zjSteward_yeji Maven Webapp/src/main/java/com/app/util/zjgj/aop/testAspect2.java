package com.app.util.zjgj.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 1、告诉ioc这个组件的存在
 * 2、告诉ioc这是一个切面使用@Aspect
 * @author syl
 *
 */
@Aspect
@Component
@Order(1)
public class testAspect2 {
	
	@Pointcut(value = "execution(* com.app.controller.CommonController.test1(..))")
	public void mypoint() {}

    /**
     * 最强大通知。 一般不常用
     * 
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value = "mypoint()")
    public Object vaildAround(ProceedingJoinPoint proceedingJoinPoint) {
    	// proceedingJoinPoint封装了连接点的详细信息
    	// proceed，执行目标方法 method.invoke
    	Object proceed = null;
    	Object[] args = proceedingJoinPoint.getArgs();
    	try {
    		// //传入目标执行时需要的参数列表
    		// 前置通知
    		System.out.println("proceed...之前");
     
     
    		// method.invoke
    		// 目标方法执行完成后会有返回值，这个返回值一定return出去
    		proceed = proceedingJoinPoint.proceed(args);
    		// 返回通知
    		System.out.println("proceed...之后");
    	} catch (Throwable e) {
    		// e.printStackTrace();
    		// 异常通知
    		System.out.println("proceed...异常");
    		// 1、注意：
    		// 一定将这个异常继续抛出去，以方便外界都能收到这个异常
    		throw new RuntimeException(e);
    	} finally {
    		// 后置通知
    		System.out.println("proceed...结束");
    	}
     
    	return proceed;
    }
}
