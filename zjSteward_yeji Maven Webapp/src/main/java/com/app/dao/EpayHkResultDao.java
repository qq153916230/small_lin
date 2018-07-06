package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.EpayHkResult;

public interface EpayHkResultDao {

	long selectCount(); //查询总数

	List selectList(Map<String, Object> map); //查询列表

	long selectCountCondition(Map<String, Object> map); //条件查询总数
	
	String selectCountMoney(Map<String, Object> map); //条件查询总金额
	
	
	
	/*autogenerate*/
	
    int deleteByPrimaryKey(Integer id);

    int insert(EpayHkResult record);

    int insertSelective(EpayHkResult record);

    EpayHkResult selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpayHkResult record);

    int updateByPrimaryKey(EpayHkResult record);
}