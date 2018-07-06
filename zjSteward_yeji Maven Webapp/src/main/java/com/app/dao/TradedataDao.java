package com.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.app.entity.Tradedata;

public interface TradedataDao {
	
	String selectMC(@Param("mName")String mName); //从t_zjgj_member 表获取 商户号/手机号
	
	void insertList(List<Tradedata> list); //批量插入数据
	
	List<Tradedata> selectInsert(); //查询插入的数据
	
	void updateStatusBySids(Map<String, Object> map); //更新状态
	
	
	
    int deleteByPrimaryKey(Integer sid);
    int insert(Tradedata record);
    int insertSelective(Tradedata record);
    Tradedata selectByPrimaryKey(Integer sid);
    int updateByPrimaryKeySelective(Tradedata record);
    int updateByPrimaryKey(Tradedata record);
}