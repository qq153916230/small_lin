package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.WXRedpackService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/wxRedpack/*")
public class WXRedpackController {
	
	@Autowired
	WXRedpackService wxRedpackService;

	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.wxRedpackService.selectList(request);
	}
	
	/**条件查询总金额*/
	@ResponseBody
	@RequestMapping("selectCountMoney")
	public String selectCountMoney(HttpServletRequest request){
		return this.wxRedpackService.selectCountMoney(request);
	}


}
