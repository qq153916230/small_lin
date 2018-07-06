package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.AidgoldService;
import com.app.util.DataGrid;
@Controller
@RequestMapping("/aidgold/*")
public class AidgoldController {
	
	@Autowired
	AidgoldService aidgoldService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.aidgoldService.selectList(request);
	}
	
	/**查询card*/
	@ResponseBody
	@RequestMapping("selectCard")
	public JSON selectCard(HttpServletRequest request){
		return this.aidgoldService.selectCard(request);
	}
	
	/**统计jkmoney*/
	@ResponseBody
	@RequestMapping("countJKmoney")
	public JSON countJKmoney(HttpServletRequest request){
		return this.aidgoldService.countJKmoney(request);
	}
	
	/**催收次数 +1*/
	@ResponseBody
	@RequestMapping("addcs")
	public void addcs(HttpServletRequest request){
		this.aidgoldService.addcs(request);
	}
	/**根据主键查找*/
	@ResponseBody
	@RequestMapping("selectByPid")
	public JSON selectByPid(HttpServletRequest request){
		return this.aidgoldService.selectByPid(request);
	}
	
	/**更新状态*/
	@ResponseBody
	@RequestMapping("updateStatus")
	public JSON updateStatus(HttpServletRequest request){
		return this.aidgoldService.updateStatus(request);
	}
	/**更新状态2*/
	@ResponseBody
	@RequestMapping("updateStatus2")
	public JSON updateStatus2(HttpServletRequest request){
		return this.aidgoldService.updateStatus2(request);
	}
	/**u 31 to 21*/
	@ResponseBody
	@RequestMapping("u31to21")
	public JSON u31to21(HttpServletRequest request){
		return this.aidgoldService.u31to21(request);
	}
	/**跨域访问的地址*/
	@ResponseBody
	@RequestMapping("getApplyResult")
	public JSON getApplyResult(HttpServletRequest request) throws Exception{
		return this.aidgoldService.getApplyResult(request);
	}
	/**通用跨域访问*/
	@ResponseBody
	@RequestMapping("callUrl")
	public JSON callUrl(HttpServletRequest request) throws Exception{
		return this.aidgoldService.callUrl(request);
	}
	
	/**跨域上传图片*/
	@ResponseBody
	@RequestMapping("appUploadIdPic")
	public JSON appUploadIdPic(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//System.out.println("进入appUploadIdPic");
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json; charset=utf-8"); 
		response.setHeader("Access-Control-Allow-Origin","*");//http://mobile.rczjgj.com:7897
		
		JSON json = this.aidgoldService.appUploadIdPic(request, response);
		
//		PrintWriter writer = response.getWriter();
//		writer.print(json);
//		writer.close();
		
		return json;
	}
	/**查询用户刷卡，额度信息*/
	@ResponseBody
	@RequestMapping("selLogCon")
	public String selLogCon(HttpServletRequest request) throws Exception{
		return this.aidgoldService.selLogCon(request);
	}
	
	/**修改jkmoney*/
	@ResponseBody
	@RequestMapping("updateJKMoney")
	public JSON updateJKMoney(HttpServletRequest request){
		return this.aidgoldService.updateJKMoney(request);
	}
	
	/**添加备注*/
	@ResponseBody
	@RequestMapping("updateDeclare")
	public JSON updateDeclare(HttpServletRequest request){
		return this.aidgoldService.updateDeclare(request);
	}
	
	/**重置客户身份信息*/
	@ResponseBody
	@RequestMapping("resetIdentity")
	public JSON resetIdentity(HttpServletRequest request){
		return this.aidgoldService.resetIdentity(request);
	}
	
	/**查询是否满足体验贷要求*/
	@ResponseBody
	@RequestMapping("tyCheck")
	public JSON tyCheck(HttpServletRequest request) throws Exception{
		return this.aidgoldService.tyCheck(request);
	}
	
	/**助理金 待审核 查看的资料*/
	@ResponseBody
	@RequestMapping("selectDetailInfo")
	public JSON selectDetailInfo(HttpServletRequest request) throws Exception{
		return this.aidgoldService.selectDetailInfo(request);
	}
	
	/**根据tid更新 助力金表 agentid*/
	@ResponseBody
	@RequestMapping("setAgent")
	public JSON setAgent(HttpServletRequest request) throws Exception{
		return this.aidgoldService.setAgent(request);
	}
	
	/**提额*/
	@ResponseBody
	@RequestMapping("increaseAmount")
	public JSON increaseAmount(HttpServletRequest request){
		return this.aidgoldService.increaseAmount(request);
	}
	
	/** 新颜爬虫 提额 */
	@ResponseBody
	@RequestMapping("xy/update/balance")
	public JSON xyUpdateBalance(HttpServletRequest request){
		return this.aidgoldService.xyUpdateBalance(request);
	}
	
	/**添加黑名单*/
	@ResponseBody
	@RequestMapping("lahei")
	public JSON lahei(HttpServletRequest request){
		return this.aidgoldService.lahei(request);
	}
	
	/**黑名单列表*/
	@ResponseBody
	@RequestMapping("blacklist")
	public JSON blacklist(HttpServletRequest request){
		return this.aidgoldService.blacklist(request);
	}
	
	/**移除黑名单*/
	@ResponseBody
	@RequestMapping("removeBlacklist")
	public JSON removeBlacklist(HttpServletRequest request){
		return this.aidgoldService.removeBlacklist(request);
	}
	
	/**捷安*/
	@ResponseBody
	@RequestMapping("jiean")
	public JSON jiean(HttpServletRequest request){
		return this.aidgoldService.jiean(request);
	}
	
	/**处理助力金 agentid为0的数据*/
	@ResponseBody
	@RequestMapping("dealAgentid")
	public JSON dealAgentid(HttpServletRequest request){
		return this.aidgoldService.dealAgentid(request);
	}
	
	/**定时任务 清除黑名单*/
	//@Scheduled(cron = "0/10 * * * * ?")	//每10秒触发
	@Scheduled(cron = "0 0 5 * * ?")	//每天凌晨5点促发
	public void removeBlacklistTiming(){
		this.aidgoldService.removeBlacklistTiming();
	}
	
}
