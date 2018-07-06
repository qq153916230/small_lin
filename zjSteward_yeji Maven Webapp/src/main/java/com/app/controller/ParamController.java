package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.ParamService;

@Controller
@RequestMapping("/param/*")
public class ParamController {
	
	@Autowired
	ParamService paramService;
	
	@ResponseBody
	@RequestMapping("getMP")
	public JSON getMP(HttpServletRequest request){
		return this.paramService.selectByPname();
	}
	

}
