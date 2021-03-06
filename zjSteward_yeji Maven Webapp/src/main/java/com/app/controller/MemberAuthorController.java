package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.MemberAuthorService;
import com.app.util.DataGrid;
import com.app.util.zjgj.ZjUtils;

@Controller
@RequestMapping("memberAuthor")
public class MemberAuthorController {
	
	@Autowired
	MemberAuthorService memberAuthorService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.memberAuthorService.selectList(request);
	}
	
	/**根据主键查找*/
	@ResponseBody
	@RequestMapping("selectByMid")
	public JSON selectByMid(HttpServletRequest request){
		return this.memberAuthorService.selectByMid(request);
	}
	
	/** 查找实名信息 */
	@ResponseBody
	@RequestMapping("detail")
	public JSON detail(HttpServletRequest request){
		JSON json = this.memberAuthorService.selectByMid(request);
		int status = 1;
		if (json == null) 
			status = 0;
		
		return ZjUtils.statusMsgJsonObj(status, this.memberAuthorService.selectByMid(request));
	}
	
	

}
