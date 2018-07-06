package com.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.EpayBackService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/epayBack/*")
public class EpayBackController {
	@Resource
	EpayBackService epayBackService;
	
	//查询列表
	@RequestMapping("selectList")
	@ResponseBody
	public DataGrid selectList(HttpServletRequest request){
		return this.epayBackService.selectList(request);
	}

	//查询总金额
	@RequestMapping("selectCountMoney")
	@ResponseBody
	public String selectCountMoney(HttpServletRequest request){
		return this.epayBackService.selectCountMoney(request);
	}
	

}
