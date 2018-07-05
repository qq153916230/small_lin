package com.zjgj.manager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect     //aop注解
@Component  //将该类放到spring容器中
public class httpAspect {

    //String 内置日志
    private final static Logger logger = LoggerFactory.getLogger(httpAspect.class);

    //JoinPoint 获取类方法
    @Pointcut("execution(public * com.zjgj.manager.controller.GirlController.*(..))")
    public void log(){}

    @Before("log()")
    public void before(JoinPoint joinPoint){
        logger.info("aop 拦截请求 前 操作");

        // 获取http请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // url
        logger.info("url={}",request.getRequestURL());

        //method GET/POST
        logger.info("method={}",request.getMethod());

        // ip
        logger.info("ip={}",request.getRemoteAddr());

        // 类方法
        //joinPoint.getSignature().getDeclaringTypeName() 获取类名
        //joinPoint.getSignature().getName() 获取类方法
        logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        // 获取类参数
        logger.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void after(){
        logger.info("aop 拦截请求 后 操作");
    }

    //获取请求返回数据
    @AfterReturning(returning = "object", pointcut = "log()")
    public void afterReturning(Object object){
        logger.info("response={}", object);
    }
}
