package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.POSAssignService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/posAssign/*")
public class POSAssignController {
	
	@Autowired
	POSAssignService posAssignService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.posAssignService.selectList(request);
	}
	
	/**更新状态*/
	@ResponseBody
	@RequestMapping("updateStatus")
	public JSON updateStatus(HttpServletRequest request){
		return this.posAssignService.updateStatus(request);
	}

}
