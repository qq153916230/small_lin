package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.AidgoldCS;

public interface AidgoldCSDao {
	
	Long selectTotle(Map<String, Object> map); //查询总记录数

	List<Aidgold> selectList(Map<String, Object> map); //查询列表
	
	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(AidgoldCS record);

    int insertSelective(AidgoldCS record);

    AidgoldCS selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(AidgoldCS record);

    int updateByPrimaryKey(AidgoldCS record);
}