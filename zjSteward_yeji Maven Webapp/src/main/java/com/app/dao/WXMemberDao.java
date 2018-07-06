package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.WXMember;

public interface WXMemberDao {

	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 
	
	/**插入归属地*/
	void updateByTel(Map<String, String> map);
	
	
	
	
    int deleteByPrimaryKey(Integer mId);

    int insert(WXMember record);

    int insertSelective(WXMember record);

    WXMember selectByPrimaryKey(Integer mId);

    int updateByPrimaryKeySelective(WXMember record);

    int updateByPrimaryKey(WXMember record);

}