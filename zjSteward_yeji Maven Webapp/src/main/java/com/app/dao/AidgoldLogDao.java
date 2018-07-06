package com.app.dao;

import com.app.entity.AidgoldLog;

public interface AidgoldLogDao {
    int deleteByPrimaryKey(Integer tid);

    int insert(AidgoldLog record);

    int insertSelective(AidgoldLog record);

    AidgoldLog selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(AidgoldLog record);

    int updateByPrimaryKey(AidgoldLog record);
}