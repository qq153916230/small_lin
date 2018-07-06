package com.app.dao;

import com.app.entity.AidgoldInfo;

public interface AidgoldInfoDao {

	AidgoldInfo selectByMid(int mid);

	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(AidgoldInfo record);

    int insertSelective(AidgoldInfo record);

    AidgoldInfo selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(AidgoldInfo record);

    int updateByPrimaryKey(AidgoldInfo record);
}