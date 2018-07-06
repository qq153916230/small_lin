package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.MemberAuthor;

public interface MemberAuthorDao {

	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 

	/** 查询匹配数据 */
	MemberAuthor selectMatch(Map<String, String> map);

	
	
    int deleteByPrimaryKey(Integer mid);

    int insert(MemberAuthor record);

    int insertSelective(MemberAuthor record);

    MemberAuthor selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(MemberAuthor record);

    int updateByPrimaryKey(MemberAuthor record);
}