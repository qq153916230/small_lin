package com.app.dao;

import com.app.entity.Card;

public interface CardDao {
    int deleteByPrimaryKey(Integer cid);

    int insert(Card record);

    int insertSelective(Card record);

    Card selectByPrimaryKey(Integer cid);

    int updateByPrimaryKeySelective(Card record);

    int updateByPrimaryKey(Card record);
}