package com.app.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.app.entity.tx_result;

public interface tx_resultMapper {
	
	BigDecimal selectTXTotleMoney(String date);	/*获取提现总额*/
	
	int selectTXTotleNum(String date);	/*获取提现总次数*/
	
	BigDecimal selectTXTotleMoney2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取提现总额*/
	
	int selectTXTotleNum2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取提现总次数*/
	
	
	
    int deleteByPrimaryKey(Integer id);

    int insert(tx_result record);

    int insertSelective(tx_result record);

    tx_result selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(tx_result record);

    int updateByPrimaryKey(tx_result record);
}