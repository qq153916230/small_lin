package com.app.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.app.entity.Loan;

public interface LoanDao {
	
	List<Loan> selectAllUser(); //查询所有
	
	List<Loan> selectNewRegister(); //查询新申请用户
	
	List<Loan> search(Map<String, Object> map); //搜索功能
	
	int searchCount(Map<String, Object> map); //搜索 获取总数
	
	void updateStatusByTids(Map<String, Object> map); //更新状态
	
	
	
	
	
	int updateByTidSelective(Loan loan);
	
    int deleteByPrimaryKey(Integer tid);

    int insert(Loan loan);

    int insertSelective(Loan loan);

    Loan selectByPrimaryKey(Integer tid);


    int updateByPrimaryKey(Loan loan);


}