package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.AidgoldFQHKService;

@Controller
@RequestMapping("/aidgoldFQHK/*")
public class AidgoldFQHKController {
	
	@Autowired
	AidgoldFQHKService aidgoldFQHKService;
	
	/**根据助理金id插入分期详情*/
	@ResponseBody
	@RequestMapping("showFQ")
	public JSON insert(HttpServletRequest request){
		return this.aidgoldFQHKService.insert(request);
	}
	/**修改状态*/
	@ResponseBody
	@RequestMapping("updateStatus")
	public JSON updateStatus(HttpServletRequest request){
		return this.aidgoldFQHKService.updateStatus(request);
	}
	
	/**删除分期列表*/
	@ResponseBody
	@RequestMapping("deleteFQList")
	public JSON deleteFQList(HttpServletRequest request){
		return this.aidgoldFQHKService.deleteFQList(request);
	}
	

}
