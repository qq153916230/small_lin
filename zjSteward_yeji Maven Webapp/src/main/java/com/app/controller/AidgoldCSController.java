package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.entity.Aidgold;
import com.app.entity.AidgoldCS;
import com.app.service.AidgoldCSService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/aidgoldCS/*")
public class AidgoldCSController {
	@Autowired
	AidgoldCSService aidgoldCSService;
	
	/**增加一条数据*/
	@ResponseBody
	@RequestMapping("createData")
	public void createData(HttpServletRequest request){
		this.aidgoldCSService.createData(request);
	}
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.aidgoldCSService.selectList(request);
	}
}
