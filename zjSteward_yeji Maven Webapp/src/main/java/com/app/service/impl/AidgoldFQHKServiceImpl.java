package com.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.AidgoldBackDao;
import com.app.dao.AidgoldDao;
import com.app.dao.AidgoldFQHKDao;
import com.app.dao.ParamDao;
import com.app.entity.Aidgold;
import com.app.entity.AidgoldBack;
import com.app.entity.AidgoldFQHK;
import com.app.entity.AidgoldFQHKKey;
import com.app.service.AidgoldFQHKService;
import com.app.util.DataGrid;
@Service
public class AidgoldFQHKServiceImpl implements AidgoldFQHKService {
	
	@Autowired
	AidgoldFQHKDao aidgoldFQHKDao;
	@Autowired
	AidgoldDao aidgoldDao;
	@Autowired
	ParamDao paramDao;
	@Autowired
	AidgoldBackDao aidgoldBackDao;

	/**根据助理金id插入分期详情*/
	@Override
	@Transactional
	public JSON insert(HttpServletRequest request) {
		String pid = request.getParameter("tid");	//获取助力金id
		int tid = 0;
		if (pid != null && !"".equals(pid) && !"null".equals(pid)) {
			tid = Integer.parseInt(pid);
		} else {
			return null;
		}
		
		//查询数据库是否已经存在分期记录
		AidgoldFQHKKey key = new AidgoldFQHKKey();
		key.setRid(tid);	//助理金id
		key.setSno(10);		//期数 查询最后一期
		AidgoldFQHK fqDB = this.aidgoldFQHKDao.selectByPrimaryKey(key);
		
		//如果表中没有分期记录，则插入分期记录
		if (fqDB == null) {
			
			//查询数据库助理金对象
			Aidgold aidgoldDB = this.aidgoldDao.selectByPrimaryKey(tid);
			if (aidgoldDB == null) {
				return null;
			}
			int jkMoney = aidgoldDB.getJkmoney();	//借款金额
			Date jkDate = aidgoldDB.getJkdate();	//借款日期
			//Date endDate = aidgoldDB.getEnddate();	//结束借款日期
			//BigDecimal jkrate = aidgoldDB.getJkrate();	//利息费率
			
			//参数表查询利息费率
			String aidgold_fq_rate = this.paramDao.selectByPname("aidgold_fq_rate");
			BigDecimal jkrate = new BigDecimal(aidgold_fq_rate);	//利息费率
			
			//设置还款结束日期
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(jkDate);
			
			int everyMoney = (int) Math.ceil(jkMoney * 0.1);	//每期还款金额(不含手续费)为借款金额的 10% (0.1)
			int period = 15;	//还款周期
			Date ctime = new Date(); 	//当前时间

			List<AidgoldFQHK> fqhkList = new ArrayList<>();	//保存每一期的分期记录
			
			//固定分10期还款
			for (int i = 1; i <= 10; i++) {
				AidgoldFQHK fd = new AidgoldFQHK();	//分期还款数据对象
				
				int balance = jkMoney - (i * everyMoney);	//每期还款剩余金额
				BigDecimal fee = new BigDecimal(balance).multiply(jkrate);	//每期利息
				BigDecimal hkmoney = new BigDecimal(everyMoney).add(fee);	//每期需还款金额(加上利息)
				if (i == 1) {
					calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + (period-1)); //第一期为14天  借款当日算1天
				} else {
					calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + period); //每一期为15天
				}
				
				fd.setRid(tid);	//助理金id
				fd.setSno(i); 	//分期期数
				fd.setBalance(balance + everyMoney);	//剩余未还金额
				fd.setRate(jkrate);	//利息费率
				fd.setFee(fee);		//每期利息
				fd.setEverymoney(everyMoney);	//每期本金（不算手续费）
				fd.setHkmoney(hkmoney);		//实际还款金额（加手续费）
				fd.setHkdate(calendar.getTime());	//最后还款日
				fd.setStatus((short)0);	// 0 表示未还
				fd.setGdate(ctime); 	//当前时间
				
				fqhkList.add(fd);	//每期数据保存到list
			}
			this.aidgoldFQHKDao.insertList(fqhkList);	//批量插入
			fqhkList.clear(); 	//清空list
		}
		List<AidgoldFQHK> listDB = this.aidgoldFQHKDao.selectByTid(tid);	//根据助力金查找
		JSON json = (JSON) JSON.toJSON(listDB);
		return json;
	}

	/**修改状态*/
	@Override
	@Transactional
	public JSON updateStatus(HttpServletRequest request) {
		String tid = request.getParameter("tid");	//助理金id
		String sno = request.getParameter("sno");	//分期期数
		String hkType = request.getParameter("hkType");	//还款类型
		
		int rid = 0;
		int snoData = 0;
		if (tid != null && !tid.equals("")) {
			rid = Integer.parseInt(tid);
		}
		if (sno != null && !sno.equals("")) {
			snoData = Integer.parseInt(sno);
		}
		//分期表复合主键
		AidgoldFQHKKey key = new AidgoldFQHKKey();
		key.setRid(rid);
		key.setSno(snoData);
		
		//获取分期数据
		AidgoldFQHK fqhkData = new AidgoldFQHK();
		AidgoldFQHK fqhkDB = this.aidgoldFQHKDao.selectByPrimaryKey(key);
		
		// 修改助理金状态/插入还款记录
		Aidgold aData = new Aidgold();
		AidgoldBack abData = new AidgoldBack();
		
		Aidgold aidgoldDB = this.aidgoldDao.selectByPrimaryKey(rid);
		
		abData.setAid(aidgoldDB.getTid());
		abData.setMid(aidgoldDB.getMid());
		abData.setStatus((short)1);
		abData.setPoundage(new BigDecimal(0));
		
		
		if (hkType.equals("1")) {	//全额还款
			fqhkData.setRid(rid);
			fqhkData.setStatus((short)1);
			this.aidgoldFQHKDao.updateByRidSelective(fqhkData);
			
			aData.setTid(rid);
			aData.setJkcheck((short)99);
			this.aidgoldDao.updateByPrimaryKeySelective(aData);
			
			abData.setPeriod(1);
			abData.setHkmoney(new BigDecimal(aidgoldDB.getHkmoney()));
			abData.setModo((short)1);
			abData.setPrincipal(new BigDecimal(aidgoldDB.getJkcheck()));
			abData.setInterest(aidgoldDB.getJkrate());
		} else {
			fqhkData.setRid(rid);
			fqhkData.setSno(snoData);
			fqhkData.setStatus((short)1);
			this.aidgoldFQHKDao.updateByPrimaryKeySelective(fqhkData);
			
			aData.setTid(rid);
			if (snoData == 10) {
				aData.setJkcheck((short)99);
			} else {
				aData.setJkcheck((short)51);
			}
			this.aidgoldDao.updateByPrimaryKeySelective(aData);
			
			abData.setPeriod(10);
			abData.setHkmoney(fqhkDB.getHkmoney());
			abData.setModo((short)2);
			abData.setPrincipal(new BigDecimal(fqhkDB.getEverymoney()));
			abData.setInterest(fqhkDB.getFee());
		}
		
		this.aidgoldBackDao.insertSelective(abData);
		
		
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "更新成功");
		return jsonObject;
	}

	/**删除分期列表*/
	@Override
	public JSON deleteFQList(HttpServletRequest request) {
		
		String stid = request.getParameter("tid");
		
		int tid = 0;
		
		if (!"".equals(stid)) {
			tid = Integer.parseInt(stid);
		}
		
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", 0);
		
		//查询是否有还款记录，有还款记录则不能删除
		AidgoldFQHKKey key = new AidgoldFQHKKey();
		key.setRid(tid);
		key.setSno(1);
		AidgoldFQHK fqDB = this.aidgoldFQHKDao.selectByPrimaryKey(key);
		
		if (fqDB == null) {
			jsonObject.put("msg", "请先生成分期数据");
			return jsonObject;
		} else if (fqDB.getStatus() != 0) {
			jsonObject.put("msg", "无法重置已经还款的分期记录");
			return jsonObject;
		}
		
		this.aidgoldFQHKDao.deleteByTid(tid);
		
		jsonObject.put("status", 11);
		jsonObject.put("msg", "重置成功");
		return jsonObject;
	}

}
