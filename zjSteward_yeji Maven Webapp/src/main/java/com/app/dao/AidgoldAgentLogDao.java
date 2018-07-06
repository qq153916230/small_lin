package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.AidgoldAgent;
import com.app.entity.AidgoldAgentLog;

public interface AidgoldAgentLogDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<AidgoldAgentLog> selectList(Map<String, Object> map); 
	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(AidgoldAgentLog record);

    int insertSelective(AidgoldAgentLog record);

    AidgoldAgentLog selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(AidgoldAgentLog record);

    int updateByPrimaryKey(AidgoldAgentLog record);
}