package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.WXRedpack;

public interface WXRedpackDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 

	/**条件查询总金额*/
	String selectCountMoney(Map<String, Object> map); 
	
	
	
    int insert(WXRedpack record);

    int insertSelective(WXRedpack record);
}