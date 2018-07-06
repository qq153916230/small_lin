package com.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.entity.daystatic;

public interface daystaticMapper {
	
	int selectDaystaticCount();	/*查询业绩总条数*/
	
	List<daystatic> selectDaystatic(@Param("gdate")String gdate);/*查询业绩*/
	
	void insertBySelectJoin(); //通过left join 查询，插入数据库
	
	//daystatic selectColTotle(@Param("date")String date); //查询每个字段总和
	
	
    int insert(daystatic record);

    int insertSelective(daystatic record);
}