package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.EpayTrade;

public interface EpayTradeJoinResDao {

	long selectCount(); //查询总数

	List selectList(Map<String, Object> map); //查询列表

	long selectCountCondition(Map<String, Object> map); //条件查询总数
	
	Map<String, String> selectCountMoney(Map<String, Object> map); //条件查询总金额
}