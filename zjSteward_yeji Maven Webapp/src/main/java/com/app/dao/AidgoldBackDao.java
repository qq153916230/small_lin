package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.AidgoldBack;

public interface AidgoldBackDao {

	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	/**查询列表*/
	List<AidgoldBack> selectList(Map<String, Object> map); 
	
	
	
	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(AidgoldBack record);

    int insertSelective(AidgoldBack record);

    AidgoldBack selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(AidgoldBack record);

    int updateByPrimaryKey(AidgoldBack record);
}