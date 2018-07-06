package com.app.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.app.entity.subsidy;
import com.app.entity.subsidyKey;

public interface subsidyMapper {
	
	BigDecimal selectSubsidyTotleMoney(String date);	/*获取补贴总额*/
	
	BigDecimal selectSubsidyTotleMoney2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);
	
	
    int deleteByPrimaryKey(subsidyKey key);

    int insert(subsidy record);

    int insertSelective(subsidy record);

    subsidy selectByPrimaryKey(subsidyKey key);

    int updateByPrimaryKeySelective(subsidy record);

    int updateByPrimaryKey(subsidy record);
}