package com.app.util.zjgj.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.app.util.zjgj.ZjUtils;


/**
 * 1、告诉ioc这个组件的存在
 * 2、告诉ioc这是一个切面使用@Aspect
 * @author syl
 *
 */
@Aspect
@Component
@Order(1)
public class testAspect {
	
	@Pointcut(value = "execution(* com.app.controller.CommonController.test1(..))")
	public void mypoint() {}

    
    /**
     * try{
     *      @Before前置通知
     *      method.invoke();
     *      @AfterRunning返回通知
     * }catch(e){
     *      @AfterThrowing：异常通知，
     * }
     * @After
     *
     * 告诉Spring这些放在都在那个方法的哪个位置执行
     * 1）、告诉位置
     [1]@Before：前置通知，在方法执行之前执行
     [2]@After：后置通知，在方法执行最终结束之后执行。
	    如果没异常
     [3]@AfterRunning：返回通知，在方法返回结果之后执行
     [4]@AfterThrowing：异常通知，在方法抛出异常之后执行
	1、编写切入点表达式，来告诉spring是切入哪个方法的这个位置
     */
    @Before(value="mypoint()")
    public void logStart(){
    	System.out.println("AOP日志，方法开始");
    }
    
    @After(value="mypoint()")
    public void logEnd(){
    	System.out.println("AOP日志，方法最终结束");
    }
    
    @AfterThrowing(value="mypoint()", throwing = "e")
    public JSON logException(JoinPoint joinPoint, Throwable e){
    	try {
    		// 获取方法名
    		String name = joinPoint.getSignature().getName();
    		String method = joinPoint.getSignature().getDeclaringTypeName();
    		System.out.println("AOP日志，【" + method+ "." + name + "】方法出现异常：异常对象：" + e);
    		System.out.println("===============");
			
		} catch (Exception e2) {
			return ZjUtils.statusMsgJson(233, "啊哈哈");
		}
    	return ZjUtils.statusMsgJson(233, "啊哈哈");
    }
    
    @AfterReturning(value="mypoint()",returning="res")
    public void logReturn(JoinPoint joinPoint, Object res){
    	Signature signature = joinPoint.getSignature();
    	String className = signature.getDeclaringTypeName();
    	String methodName = signature.getName();
    	System.out.println("AOP参数验证，【" + className + "."+ methodName + "】方法正常返回,返回值为：" + res);
    	//System.out.println(joinPoint.toString());
    	System.out.println("AOP日志，方法正常执行");
    }
    
    
}
