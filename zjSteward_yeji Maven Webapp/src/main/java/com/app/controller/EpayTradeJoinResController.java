package com.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.EpayTradeJoinResService;
import com.app.service.EpayTradeService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/epayTradeJoinRes/*")
public class EpayTradeJoinResController {
	@Resource
	EpayTradeJoinResService epayTradeJoinResService;
	
	//查询列表
	@RequestMapping("selectList")
	@ResponseBody
	public DataGrid selectList(HttpServletRequest request){
		return this.epayTradeJoinResService.selectList(request);
	}

	//查询总金额
	@RequestMapping("selectCountMoney")
	@ResponseBody
	public JSON selectCountMoney(HttpServletRequest request){
		return this.epayTradeJoinResService.selectCountMoney(request);
	}
	

}
