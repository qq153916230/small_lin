package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.EpayBack;

public interface EpayBackDao {

	long selectCount(); //查询总数

	List selectList(Map<String, Object> map); //查询列表

	long selectCountCondition(Map<String, Object> map); //条件查询总数
	
	String selectCountMoney(Map<String, Object> map); //条件查询总金额
	
	
	
	/*autogenerate*/
	
    int deleteByPrimaryKey(Integer id);

    int insert(EpayBack record);

    int insertSelective(EpayBack record);

    EpayBack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpayBack record);

    int updateByPrimaryKey(EpayBack record);
}