package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.EpayPayment;

public interface EpayPaymentDao {

	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 
	
	
	
    int deleteByPrimaryKey(Long tid);

    int insert(EpayPayment record);

    int insertSelective(EpayPayment record);

    EpayPayment selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(EpayPayment record);

    int updateByPrimaryKey(EpayPayment record);
}