package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.EpayTrade;

public interface EpayTradeDao {

	long selectCount(); //查询总数
	
	List selectList(Map<String, Object> map); //查询列表

	long selectCountCondition(Map<String, Object> map); //条件查询总数
	
	String selectCountMoney(Map<String, Object> map); //条件查询总金额
	
	
	
	
	/*autogenerate*/
	
    int deleteByPrimaryKey(Long tid);

    int insert(EpayTrade record);

    int insertSelective(EpayTrade record);

    EpayTrade selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(EpayTrade record);

    int updateByPrimaryKey(EpayTrade record);
}