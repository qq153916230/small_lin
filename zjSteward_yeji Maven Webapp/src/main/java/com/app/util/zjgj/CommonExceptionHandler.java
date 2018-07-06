package com.app.util.zjgj;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CommonExceptionHandler extends ZjUtils {
	
	@ResponseBody
	@ExceptionHandler(value=Exception.class)
	private String commonExceptionHandler(HttpServletRequest request, Exception e){
		System.out.println("got it");
		System.out.println(e.getMessage());
		
		return REQUEST_LIMIT_MSG;
	}

}
