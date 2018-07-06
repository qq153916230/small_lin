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
import com.app.service.CommonService;
import com.app.util.DataGrid;
import com.app.util.zjgj.ZjUtils;
import com.app.util.zjgj.threads.HttpThread;
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
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		HttpThread thread = new HttpThread(req);
		
		ExecutorService cachedThreadPoor = Executors.newCachedThreadPool();
		cachedThreadPoor.execute(thread);
		
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
		
		return  (JSON) JSON.toJSON(list);
	}
	
}
