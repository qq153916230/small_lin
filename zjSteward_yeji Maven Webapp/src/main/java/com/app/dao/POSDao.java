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
	
	
	
    int deleteByPrimaryKey(String possn);

    int insert(POS record);

    int insertSelective(POS record);

    POS selectByPrimaryKey(String possn);

    int updateByPrimaryKeySelective(POS record);

    int updateByPrimaryKey(POS record);
}