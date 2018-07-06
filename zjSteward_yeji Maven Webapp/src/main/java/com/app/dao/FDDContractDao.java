package com.app.dao;

import java.util.List;
import java.util.Map;

import com.app.entity.Aidgold;
import com.app.entity.FDDContract;

public interface FDDContractDao {
	
	/**查询总记录数*/
	Long selectTotle(Map<String, Object> map); 
	/**查询列表*/
	List<Aidgold> selectList(Map<String, Object> map); 
	
	/**根据交易号查询合同编号*/
	String queryCidByTid(String transaction_id);
	
	/**根据助力金修改*/
	void updateByAidgoldIdSelective(FDDContract fddContract);
	
	/**根据助力金id获取一条记录*/
	FDDContract selectByAidgoldId(int aidgoldId);

	/**根据合同编号获取合同对象*/
	FDDContract selectByContractID(String contractID);
	
	
	
	
    int deleteByPrimaryKey(Integer pid);

    int insert(FDDContract record);

    int insertSelective(FDDContract record);

    FDDContract selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(FDDContract record);

    int updateByPrimaryKey(FDDContract record);
}