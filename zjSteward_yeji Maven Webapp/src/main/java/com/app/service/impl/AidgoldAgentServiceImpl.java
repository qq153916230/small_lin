package com.app.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.AidgoldAgentDao;
import com.app.dao.AidgoldAgentLogDao;
import com.app.entity.AidgoldAgent;
import com.app.entity.AidgoldAgentLog;
import com.app.service.AidgoldAgentService;
import com.app.util.DataGrid;
import com.app.util.zjgj.ZjUtils;
@Service
public class AidgoldAgentServiceImpl extends ZjUtils implements AidgoldAgentService  {
	
	@Autowired
	AidgoldAgentDao aidgoldAgentDao;
	@Autowired
	AidgoldAgentLogDao aidgoldAgentLogDao;

	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String mid = request.getParameter("mid");
		String nick = request.getParameter("nick");
		String mobile = request.getParameter("mobile");
		
		if (!mobile.equals("") && mobile != null) {
			mid = this.aidgoldAgentDao.selectMemberMidByMobile(mobile);	//根据手机查mid
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("mid", mid);
		map.put("nick", nick);
		
		if (page != null && !"".equals(page)) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.aidgoldAgentDao.selectTotle(map));
		dg.setRows(this.aidgoldAgentDao.selectList(map));
		return dg;
	}

	/**修改totalgd总额度额*/
	@Override
	@Transactional
	public JSON updateTotalgd(HttpServletRequest req) {
		int mid = getInt(req, "mid"); 	//获取mid
		String opeMoney = getStr(req, "opeMoney");	//修改的金额
		String opePosNum = getStr(req, "opePosNum");	//修改的pos台数
		String mobile = getStr(req, "mobile");
		
		String logMobile = "";	//记录手机
		if ( !"".equals(mobile) ) {
			logMobile = " 发配到：" + mobile;
		}
		
		BigDecimal money3;
		BigDecimal money = new  BigDecimal(opeMoney == ""?"0":opeMoney);	//获取要修改的金额
		int posNum = opeMoney == ""? 0:Integer.parseInt(opePosNum);	//修改的pos台数
		money3 = money;
		
		AidgoldAgent agent = this.aidgoldAgentDao.selectByPrimaryKey(mid);
		money = money.add(agent.getTotalgd());
		
		AidgoldAgent agentData = new AidgoldAgent();
		agentData.setMid(mid);
		agentData.setTotalgd(money);
		this.aidgoldAgentDao.updateByPrimaryKeySelective(agentData);	//修改总额度额
		this.aidgoldAgentDao.updateAgentAccount(mid, posNum);	//修改t_zjgj_agent 的台数

		//插入日志
		AidgoldAgentLog alData = new AidgoldAgentLog();
		alData.setManager("管理员");
		alData.setMid(mid+"");
		alData.setStype("修改总额度");
		alData.setCont("管理员修改总额度："+opeMoney + " 台数："+opePosNum + logMobile);
		this.aidgoldAgentLogDao.insertSelective(alData);
		
		if (money3.compareTo(new BigDecimal(0)) == -1 && posNum < 0 && !"".equals(mobile)) {
			String midStr = this.aidgoldAgentDao.selectMemberMidByMobile(mobile);
			int mid2 = Integer.parseInt(midStr);
			BigDecimal money2 = money3.multiply(new BigDecimal(-1));	//money转为正数 转到另一个人
			
			int posNum2 = posNum * -1;	//pos数量转为正，转到另一人上
			alData.setCont("自动从编号："+ mid+ " 分配额度：" + money2 + " 台数：" + posNum2);
			
			AidgoldAgent agent2 = this.aidgoldAgentDao.selectByPrimaryKey(mid2);
			money2 = money2.add(agent2.getTotalgd());
			
			agentData.setMid(mid2);
			agentData.setTotalgd(money2);
			this.aidgoldAgentDao.updateByPrimaryKeySelective(agentData);	//修改总额度额
			this.aidgoldAgentDao.updateAgentAccount(mid2, posNum2);		//修改t_zjgj_agent 的台数
			
			//插入日志
			alData.setMid(mid2+"");
			alData.setStype("修改总额度");
			this.aidgoldAgentLogDao.insertSelective(alData);
			
		}
		
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "操作成功");
		
		return json;
	}

	/**查询权限*/
	@Override
	public JSON selectUserRole(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**修改member表邮箱*/
	@Override
	@Transactional
	public JSON updateMemberEmail(HttpServletRequest request) {

		int mid = Integer.parseInt(request.getParameter("mid"));	//获取mid
		String email = request.getParameter("email");	//
		
		AidgoldAgent agent = this.aidgoldAgentDao.selectByPrimaryKey(mid);
		
		this.aidgoldAgentDao.updateupdateMemberEmail(mid, email);	//修改member表邮箱
		
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "操作成功");
		
		return json;
	
	}

	/**修改费率*/
	@Override
	public JSON updateAgentrate(HttpServletRequest request) {

		String sid = request.getParameter("mid");
		String rate = request.getParameter("agentrate");	//代理商扣率
		
		int mid = 0;
		int agentrate = 0;
		if (!"".equals(sid) && !"".equals(rate)) {
			mid = Integer.parseInt(sid);
			agentrate = Integer.parseInt(rate);
		}
		
		
		AidgoldAgent agentData = new AidgoldAgent();
		agentData.setMid(mid);
		agentData.setAgentrate(agentrate);
		
		this.aidgoldAgentDao.updateByPrimaryKeySelective(agentData);
		
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("msg", "操作成功");
		
		return json;
	}

	/**修改代理商过桥手续费*/
	@Override
	public JSON updateAgentBridgegd(HttpServletRequest request) {

		int mid = getInt("mid");
		String bridgegd = getStr("bridgegd");	//代理商过桥手续费
		
		AidgoldAgent agentData = new AidgoldAgent();
		agentData.setMid(mid);
		agentData.setBridgegd(new BigDecimal("".equals(bridgegd) ? "0" : bridgegd ));
		
		this.aidgoldAgentDao.updateAgentBridgegd(agentData);
		
		return statusMsgJson(1, "操作成功");
	}
	

}
