package com.app.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.app.util.DataGrid;

public interface EpayTradeService {
	
	DataGrid selectList(HttpServletRequest request); //查询列表
	
	String selectCountMoney(HttpServletRequest request); //条件查询总金额

}
