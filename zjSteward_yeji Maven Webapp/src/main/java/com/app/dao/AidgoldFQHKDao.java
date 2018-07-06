package com.app.dao;

import java.util.List;

import com.app.entity.AidgoldFQHK;
import com.app.entity.AidgoldFQHKKey;

public interface AidgoldFQHKDao {
	
	/**批量插入*/
	void insertList(List<AidgoldFQHK> list);

	/**根据助力金查找*/
	List<AidgoldFQHK> selectByTid(int tid);
	
	/**根据tid修改*/
	void updateByRidSelective(AidgoldFQHK record);
	
	/**删除分期列表*/
	void deleteByTid(int tid);
	
	

	
    int deleteByPrimaryKey(AidgoldFQHKKey key);

    int insert(AidgoldFQHK record);

    int insertSelective(AidgoldFQHK record);

    AidgoldFQHK selectByPrimaryKey(AidgoldFQHKKey key);

    int updateByPrimaryKeySelective(AidgoldFQHK record);

    int updateByPrimaryKey(AidgoldFQHK record);
}