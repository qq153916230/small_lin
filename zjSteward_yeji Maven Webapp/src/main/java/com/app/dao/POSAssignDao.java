package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.POSAssign;

public interface POSAssignDao {
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 

	/**批量更新状态*/
	void updateStatus(Map<String, Object> map); 
	
	
    int insert(POSAssign record);

    int insertSelective(POSAssign record);
}