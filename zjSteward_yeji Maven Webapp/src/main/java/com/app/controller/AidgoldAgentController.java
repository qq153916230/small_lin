package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.AidgoldAgentService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/aidgoldAgent/*")
public class AidgoldAgentController {
	
	@Autowired 
	AidgoldAgentService aidgoldAgentService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.aidgoldAgentService.selectList(request);
	}
	
	/**修改totalgd总额度额*/
	@ResponseBody
	@RequestMapping("updateTotalgd")
	public JSON updateTotalgd(HttpServletRequest request){
		return this.aidgoldAgentService.updateTotalgd(request);
	}
	/**查询权限*/
	@ResponseBody
	@RequestMapping("selectUserRole")
	public JSON selectUserRole(HttpServletRequest request){
		return this.aidgoldAgentService.selectUserRole(request);
	}
	
	/**修改member表邮箱*/
	@ResponseBody
	@RequestMapping("updateMemberEmail")
	public JSON updateMemberEmail(HttpServletRequest request){
		return this.aidgoldAgentService.updateMemberEmail(request);
	}
	
	/**修改代理商扣率*/
	@ResponseBody
	@RequestMapping("updateAgentrate")
	public JSON updateAgentrate(HttpServletRequest request){
		return this.aidgoldAgentService.updateAgentrate(request);
	}
	
	/**修改代理商过桥手续费*/
	@ResponseBody
	@RequestMapping("updateAgentBridgegd")
	public JSON updateAgentBridgegd(HttpServletRequest request){
		return this.aidgoldAgentService.updateAgentBridgegd(request);
	}

}
