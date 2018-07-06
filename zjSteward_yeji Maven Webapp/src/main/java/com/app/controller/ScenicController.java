package com.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.ScenicService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/scenic/*")
public class ScenicController {
	
	@Autowired
	ScenicService scenicService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.scenicService.selectList(request);
	}
	
	/**新增景区*/
	@ResponseBody
	@RequestMapping("scenicInsert")
	public JSON scenicInsert(HttpServletRequest request){
		 return this.scenicService.scenicInsert(request);
	}
	
	/**删除景区*/
	@ResponseBody
	@RequestMapping("scenicDelete")
	public JSON scenicDelete(HttpServletRequest request){
		 return this.scenicService.scenicDelete(request);
	}
	
	/**jsonp方式买票
	 * @throws IOException */
	@RequestMapping("buyTicket")
	public void buyTicket(HttpServletRequest req,HttpServletResponse res) throws IOException {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.buyTicket(req));
		out.close();
	}
	
	/**jsonp方式获取额度*/
	@RequestMapping("getBalance")
	public void getBalance(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.getBalance(req));
		out.close();
	}
	
	/**jsonp方式获取景区列表*/
	@RequestMapping("getScenicList")
	public void getScenicList(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.getScenicList(req));
		out.close();
	}
	
	/**jsonp方式根据sid获取景区*/
	@RequestMapping("getScenicBySid")
	public void getScenicBySid(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.getScenicBySid(req));
		out.close();
	}
	
	/**jsonp方式获取票列表*/
	@RequestMapping("getTicketList")
	public void getTicketList(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.getTicketList(req));
		out.close();
	}
	
	/** 根据mid 获取票列表  */
	@ResponseBody
	@RequestMapping("selectTicketByMid")
	public JSON selectTicketByMid(){
		 return this.scenicService.selectTicketByMid();
	}
	
	/**jsonp方式验票*/
	@RequestMapping("checkTicket")
	public void checkTicket(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.checkTicket(req));
		out.close();
	}
	
	/**jsonp方式获取亲属*/
	@RequestMapping("selectRelative")
	public void selectRelative(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.selectRelative(req));
		out.close();
	}
	
	/**jsonp方式添加亲属*/
	@RequestMapping("addRelative")
	public void addRelative(HttpServletRequest req,HttpServletResponse res) throws IOException  {
		res.setContentType("text/plain");
		res.setCharacterEncoding("UTF-8");
		PrintWriter out = res.getWriter();
		out.write(this.scenicService.addRelative(req));
		out.close();
	}
	
	/** 充1万 */
	@ResponseBody
	@RequestMapping(value="resetBalance", method=RequestMethod.POST)
	public JSON resetBalance(HttpServletRequest request){
		 return this.scenicService.resetBalance(request);
	}
	
	/** 景区人员登入 */
	@ResponseBody
	@RequestMapping("login")
	public JSON login(HttpServletRequest request){
		 return this.scenicService.login(request);
	}
	
	/** 景区人员获取门票 */
	@ResponseBody
	@RequestMapping("selectTicketByManager.do")
	public JSON selectTicketByManager(HttpServletRequest request){
		 return this.scenicService.selectTicketByManager(request);
	}
	
	/** 景区人员验票 */
	@ResponseBody
	@RequestMapping("checkTicketByManager.do")
	public JSON checkTicketByManager(HttpServletRequest request){
		 return this.scenicService.checkTicketByManager(request);
	}
	
	/** 景区人员获取所有的票 条件查询 */
	@ResponseBody
	@RequestMapping("selectAllTicketByManager.do")
	public JSON selectAllTicketByManager(HttpServletRequest request){
		 return this.scenicService.selectAllTicketByManager(request);
	}
	
	/** 1元购票预订单创建 (未使用) */
	@RequestMapping("preOrder")
	@ResponseBody
	public JSON preOrder (){
		return null;//this.scenicService.preOrder();
	}
	
	/** 1元购票活动 */
	@RequestMapping("buyTicketOne")
	@ResponseBody
	public JSON buyTicketOne (){
		return this.scenicService.buyTicketOne();
	}
	
	
	
	/***/
	@ResponseBody
	@RequestMapping("prepare")
	public JSON prepare(HttpServletRequest request){
		 return JSON.parseArray("");
	}

}
