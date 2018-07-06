package com.app.dao;

import com.app.entity.Param;

public interface ParamDao {

	/**获取扣款密码*/
	String selectPaypass();
	
	/**根据pname获取pvalue*/
	String selectByPname(String pname);
	
	
	
    int deleteByPrimaryKey(Integer tid);

    int insert(Param record);

    int insertSelective(Param record);

    Param selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(Param record);

    int updateByPrimaryKey(Param record);
}