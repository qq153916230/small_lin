package com.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.AidgoldInfoService;

@Controller
@RequestMapping("/aidgoldInfo/*")
public class AidgoldInfoController {
	
	@Autowired
	AidgoldInfoService aidgoldInfoService;
	
	/**根据mid查询*/
	@ResponseBody
	@RequestMapping("selectByMid")
	public JSON selectByMid(HttpServletRequest request){
		return this.aidgoldInfoService.selectByMid(request);
	}
	
	/**插入信息*/
	@RequestMapping("insert")
	public void get(HttpServletRequest req,HttpServletResponse res) {
		
		this.aidgoldInfoService.insertSelective(req);
		
		res.setContentType("text/plain");  
		String callbackFunName =req.getParameter("callbackparam");//得到js函数名称  
		
		try {
			res.getWriter().write(callbackFunName + "([{status:1,msg:\"success\"}])"); //返回jsonp数据 
		} catch (IOException e) {
			e.printStackTrace();  
		}  
	} 
	
}
