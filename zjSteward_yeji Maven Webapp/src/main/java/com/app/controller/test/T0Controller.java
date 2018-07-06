package com.app.controller.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import test.TestGetRequest;

import com.alibaba.fastjson.JSON;
import com.app.service.test.T0Service;

@Controller
@RequestMapping("/test/*")
public class T0Controller {
	@Autowired
	T0Service t0Service;
	
	
	@ResponseBody
	@RequestMapping("queryByPK")
	public JSON queryByPK(HttpServletRequest request){
		return this.t0Service.queryByPK(request);
	}
	
	@ResponseBody
	@RequestMapping("getRequest")
	public String getRequest(){
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		HttpServletRequest request = TestGetRequest.getReq();
		
		String str = request.getParameter("test1");
		return str;
	}
	

}
