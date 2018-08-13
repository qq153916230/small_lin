package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.POS;

public interface POSDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 

	/**批量更新状态*/
	void updateStatus(Map<String, Object> map); 

	/** 根据手机号查询 代理名下已激活的POS机 */
	List<Map<String, Object>> selectExportAgentPosByMobile(String mobile);
	
	
    int deleteByPrimaryKey(String possn);

    int insert(POS record);

    int insertSelective(POS record);

    POS selectByPrimaryKey(String possn);

    int updateByPrimaryKeySelective(POS record);

    int updateByPrimaryKey(POS record);

}