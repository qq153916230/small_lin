package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.util.Log4j;

@Controller
public class TestController {
	
	public static  Logger logger = Logger.getLogger(Log4j.class);
	
	@ResponseBody
	@RequestMapping("filePathTest")
	public JSON receiveFilePath(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8"); 
	    response.setHeader("Access-Control-Allow-Origin","file:///D:/");//:///D:/test.html
	    
	    String str = "{\"name\":\"小明\",\"age\":18}";
	    
	    JSONObject jsonObject = new JSONObject().parseObject(str);
		
		logger.info("测试完成！");
		return jsonObject;
	}
}
