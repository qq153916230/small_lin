package com.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.service.CommonService;
import com.app.util.DataGrid;
import com.app.util.zjgj.ZjUtils;
import com.app.util.zjgj.threads.TestThread;

@Controller
@RequestMapping(value="/common/*", method={RequestMethod.GET, RequestMethod.POST})
public class CommonController {
	
	@Autowired
	CommonService commonService;
	
	/** 根据ordercode查询 */
	@ResponseBody
	@RequestMapping(value = "tx/reward/detail")
	public DataGrid txRewardDetail(){
		return this.commonService.txRewardDetail();
	}
	
	/** 导出 `t_zjgj_tx_reward` */
	@ResponseBody
	@RequestMapping(value = "tx/reward/export")
	public JSON txRewardExport(){
		return this.commonService.txRewardExport();
	}
	
	/** 财富通提现申请列表 */
	@ResponseBody
	@RequestMapping(value = "tx/cft/apply")
	public DataGrid txApply_cft(){
		return this.commonService.txApply_cft();
	}
	/** 财富通提现申请处理 */
	@ResponseBody
	@RequestMapping(value = "tx/cft/deal")
	public JSON txApply_cft_deal(){
		return this.commonService.txApply_cft_deal();
	}
	
	/** 扣费贴息激活 机具列表 */
	@ResponseBody
	@RequestMapping(value = "pos/subsidy/list")
	public DataGrid subsidyPosList(){
		return this.commonService.subsidyPosList();
	}
	
	/** 保存 代理商报名参与活动 */
	@ResponseBody
	@RequestMapping(value = "pos/activity/save")
	public JSON posActivitySave(HttpServletRequest req){
		return this.commonService.posActivitySave(req);
	}
	
	/** 查看机具类型 未实现 */
	@ResponseBody
	@RequestMapping(value = "pos/saletype")
	public JSON posSaleType(HttpServletRequest req){
		return this.commonService.posSaleType(req);
	}
	
	/** `t_zjgj_member_bank` 列表 */
	@ResponseBody
	@RequestMapping(value = "member/bank/list")
	public JSON memberBankList(HttpServletRequest req){
		List<Map<String, Object>> data = this.commonService.memberBankList(req);
		Integer dataSize = 2453;
		JSONObject json = new JSONObject();
		json.put("total", dataSize);
		json.put("rows", JSON.toJSON(data));
		return json;
	}
	
	/** `t_zjgj_member_bank` 列表2 */
	@ResponseBody
	@RequestMapping(value = "member/bank/list/pro")
	public DataGrid memberBankList2(HttpServletRequest req){
		return this.commonService.memberBankListPro(req);
	}
	
	/** `t_zjgj_member_bank` 保存 */
	@ResponseBody
	@RequestMapping(value = "member/bank/save")
	public JSON memberBankSave(HttpServletRequest req){
		return this.commonService.memberBankSave(req);
	}
	
	/** 查看是否满足贷款条件 */
	@ResponseBody
	@RequestMapping(value = "aidgold/precheck")
	public JSON aidgoldPrecheck(HttpServletRequest req){
		return this.commonService.aidgoldPrecheck(req);
	}
	
	/** 查看服务器是否存在照片 */
	@ResponseBody
	@RequestMapping(value = "aidgold/check/pic")
	public JSON aidgoldCheckPic(HttpServletRequest req){
		return this.commonService.aidgoldCheckPic(req);
	}
	
	/** 查询公告，新闻文章 */
	@ResponseBody
	@RequestMapping(value = "news/list")
	public JSON newsList(HttpServletRequest req){
		return this.commonService.newsList(req);
	}
	
	/** `t_ship_member` 列表 */
	@ResponseBody
	@RequestMapping(value = "ship/member/list")
	public DataGrid shipMemberList(){
		return this.commonService.shipMemberList();
	}
	
	/** 设置股东 */
	@ResponseBody
	@RequestMapping(value = "ship/be_shareholder")
	public JSON setShareholder(HttpServletRequest req){
		return this.commonService.setShareholder(req);
	}
	
	/** `t_ship_option` 列表 */
	@ResponseBody
	@RequestMapping(value = "ship/option/list")
	public DataGrid shipOptionList(){
		return this.commonService.shipOptionList();
	}
	
	/** t_ship_option 财务确认到款 */
	@ResponseBody
	@RequestMapping(value = "ship/confirm")
	public JSON shipConfirm(){
		return this.commonService.shipConfirm();
	}
	
	/** `t_ship_tixian` 列表 */
	@ResponseBody
	@RequestMapping(value = "ship/tixian/list")
	public DataGrid shipTixianList(){
		return this.commonService.shipTixianList();
	}
	
	/** t_ship_tixian 提现审核 */
	@ResponseBody
	@RequestMapping(value = "ship/tixian/update_status")
	public JSON shipTixianUpdateStatus(){
		return this.commonService.shipTixianUpdateStatus();
	}
	
	/** 导出 `t_ship_tixian` */
	@ResponseBody
	@RequestMapping(value = "ship/tixian/export")
	public JSON shipTixianExport(){
		return this.commonService.shipTixianExport();
	}
	
	
	
	
	
	
	
	
	
	ExecutorService cachedThreadPoor = Executors.newCachedThreadPool();
	/** 测试 
	 * @throws IOException */
	@ResponseBody
	@RequestMapping(value="test")
	public void test(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		System.out.println("======= " +  req.getRequestURI() + "?" + req.getQueryString()+ " " + req.getParameter("a") + " " +  req.getQueryString() + " =========");
		TestThread thread = new TestThread(req);
		
		cachedThreadPoor.execute(thread);
		
//		Thread thread2 = new Thread(thread);
//		thread2.start();
		
		resp.getWriter().write(ZjUtils.statusMsgJson(1, "success").toJSONString());
	}
	
	@ResponseBody
	@RequestMapping(value="test1",method={RequestMethod.GET})
	public JSON test1(HttpServletRequest req){
		
		try {
			
			
		/*HttpThread thread = new HttpThread(req);
		
		ExecutorService cachedThreadPoor = Executors.newCachedThreadPool();
		cachedThreadPoor.execute(thread);*/
		
		//thread.start();
		
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String c = req.getParameter("c");
		String d = req.getParameter("d");
		System.out.println("a:" + a + " b:" + b + " c:" + c + " d:" + d);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a", a);
		map.put("b", b);
		
		list.add(map);
		map.put("a", a);
		map.put("b", b);
		
		list.add(map);
		System.out.println("a:" + a + " b:" + b + " c:" + c + " d:" + d);
		
		//int i = 1/0;
		return  (JSON) JSON.toJSON(list);
		} catch (Exception e) {
			throw new RuntimeException("人造异常");
		}
	}
	
	@ResponseBody
	@RequestMapping(value="test2",method={RequestMethod.GET})
	public JSON test2(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String res = ZjUtils.getStr("aa");
		System.out.println(res);
		return ZjUtils.statusMsgJson(1, res);
	}
	
}
