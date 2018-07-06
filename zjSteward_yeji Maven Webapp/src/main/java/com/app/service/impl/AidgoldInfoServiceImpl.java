package com.app.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.dao.AidgoldInfoDao;
import com.app.entity.AidgoldInfo;
import com.app.service.AidgoldInfoService;
@Service
public class AidgoldInfoServiceImpl implements AidgoldInfoService {
	
	@Autowired
	AidgoldInfoDao aidgoldInfoDao;

	@Override
	public void insertSelective(HttpServletRequest req) {
		String tid = req.getParameter("tid");
		String mid = req.getParameter("mid");
		String sname = req.getParameter("sname");
		String mobile = req.getParameter("mobile");
		String weixin = req.getParameter("weixin");
		String email = req.getParameter("email");
		String contact_name1 = req.getParameter("contact_name1");
		String contact_tele1 = req.getParameter("contact_tele1");
		String contact_name2 = req.getParameter("contact_name2");
		String contact_tele2 = req.getParameter("contact_tele2");
		
		System.out.println("tid:"+tid);
		System.out.println("mid:"+mid);
		System.out.println("sname:"+sname);
		System.out.println("mobile:"+mobile);
		System.out.println("weixin:"+weixin);
		System.out.println("email:"+email);
		System.out.println("contact_name1:"+contact_name1);
		System.out.println("contact_tele1:"+contact_tele1);
		System.out.println("contact_name2:"+contact_name2);
		System.out.println("contact_tele2:"+contact_tele2);
		
		AidgoldInfo agi = new AidgoldInfo();
		agi.setTid(Integer.parseInt(tid));
		agi.setMid(Integer.parseInt(mid));
		agi.setSname(sname);
		agi.setMobile(mobile);
		agi.setWeixin(weixin);
		agi.setEmail(email);
		agi.setContactName1(contact_name1);
		agi.setContactTele1(contact_tele1);
		agi.setContactName2(contact_name2);
		agi.setContactTele2(contact_tele2);
		
		this.aidgoldInfoDao.insertSelective(agi);
	}

	@Override
	public JSON selectByMid(HttpServletRequest request) {
		int mid = Integer.parseInt(request.getParameter("mid"));
		AidgoldInfo aidgoldInfo = this.aidgoldInfoDao.selectByMid(mid);
		JSON json = (JSON) JSON.toJSON(aidgoldInfo);
		return json;
	}

}
