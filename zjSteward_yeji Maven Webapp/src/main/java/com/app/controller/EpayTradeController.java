package com.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.EpayTradeService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/epayTrade/*")
public class EpayTradeController {
	@Resource
	EpayTradeService epayTradeService;
	
	//查询列表
	@RequestMapping("selectList")
	@ResponseBody
	public DataGrid selectList(HttpServletRequest request){
		return this.epayTradeService.selectList(request);
	}
	
	//查询总金额
	@ResponseBody
	@RequestMapping("selectCountMoney")
	public String selectCountMoney(HttpServletRequest request){
		return this.epayTradeService.selectCountMoney(request);
	}
	

}
