package com.app.dao;

import java.util.Map;

import com.app.entity.MemberAddr;

public interface MemberAddrDao {
	
	/**查询邮政编码*/
	String selectRegionCode(String str);	
	/**根据父邮编 查询邮政编码*/
	String selectRegionCodeByParent(Map<String, String> addMap);

	/**根据mid查询*/
	MemberAddr selectByMid(int mid);
	
	/**根据mid查询最新一条记录*/
	MemberAddr selectByMidOnly(int mid);
	
	
    int deleteByPrimaryKey(Integer id);

    int insert(MemberAddr record);

    int insertSelective(MemberAddr record);

    MemberAddr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberAddr record);

    int updateByPrimaryKey(MemberAddr record);
	
}