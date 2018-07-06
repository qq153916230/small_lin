package com.app.service;

import java.util.List;

import com.app.entity.Tradedata;

public interface TradedataService {
	public Long selectMC(String mName); //从t_zjgj_member 表获取 商户号/手机号
	
	public void insertList(List<Tradedata> list); //批量插入数据
	
	public List<Tradedata> selectInsert(); //查询插入的数据
	
	public void updateBySidSelective(String[] sids,int status);//更新状态
}
