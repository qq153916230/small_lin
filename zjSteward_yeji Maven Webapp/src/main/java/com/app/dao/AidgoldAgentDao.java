package com.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.app.entity.AidgoldAgent;

public interface AidgoldAgentDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<AidgoldAgent> selectList(Map<String, Object> map); 
	
	/**修改t_zjgj_agent 的台数
	 * @param posNum2 */
	void updateAgentAccount(@Param("mid")int mid, @Param("posNum")int posNum);
	
	/**修改member表邮箱*/
	void updateupdateMemberEmail(@Param("mid")int mid, @Param("email")String email);

	/**根据手机查mid*/
	String selectMemberMidByMobile(String mobile);

	/** 根据mid 修改bridgegd */
	int updateAgentBridgegd(AidgoldAgent agentData);
	
	
	
    int deleteByPrimaryKey(Integer mid);

    int insert(AidgoldAgent record);

    int insertSelective(AidgoldAgent record);

    AidgoldAgent selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(AidgoldAgent record);

    int updateByPrimaryKey(AidgoldAgent record);

}