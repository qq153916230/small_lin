package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.FDDContractService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/fddContract/*")
public class FDDContractController {
	
	@Autowired
	FDDContractService fddContractService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.fddContractService.selectList(request);
	}
	
	/**根据助力金id获取一条记录*/
	@ResponseBody
	@RequestMapping("selectByAidgoldId")
	public JSON selectByAidgoldId(HttpServletRequest request){
		return this.fddContractService.selectByAidgoldId(request);
	}

}
