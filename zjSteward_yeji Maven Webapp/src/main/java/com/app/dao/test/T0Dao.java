package com.app.dao.test;

import com.app.entity.test.T0;

public interface T0Dao {
    int deleteByPrimaryKey(Integer sid);

    int insert(T0 record);

    int insertSelective(T0 record);

    T0 selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(T0 record);

    int updateByPrimaryKey(T0 record);
}