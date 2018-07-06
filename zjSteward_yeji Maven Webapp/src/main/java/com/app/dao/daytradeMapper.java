package com.app.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.app.entity.daytrade;

public interface daytradeMapper {
	
	BigDecimal selectT0DealTotleMoney(String date);	/*获取T0交易总额*/
	
	BigDecimal selectT1DealTotleMoney(String date);	/*获取T1交易总额*/
	
	int selectT0DealTotleNum(String date);	/*获取T0交易笔数*/
	
	int selectT1DealTotleNum(String date);	/*获取T1交易笔数*/
	
	BigDecimal selectPayTotleMoney(String date);	/*获取交易总金额*/
	
	BigDecimal selectT0DealTotleMoney2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取T0交易总额*/
	
	BigDecimal selectT1DealTotleMoney2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取T1交易总额*/
	
	int selectT0DealTotleNum2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取T0交易笔数*/
	
	int selectT1DealTotleNum2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取T1交易笔数*/
	
	BigDecimal selectPayTotleMoney2(@Param("paydate")String paydate,@Param("merchantcode")String merchantcode);	/*获取交易总金额*/
	
	
	
	
    int deleteByPrimaryKey(Long tid);

    int insert(daytrade record);

    int insertSelective(daytrade record);

    daytrade selectByPrimaryKey(Long tid);

    int updateByPrimaryKeySelective(daytrade record);

    int updateByPrimaryKey(daytrade record);
}