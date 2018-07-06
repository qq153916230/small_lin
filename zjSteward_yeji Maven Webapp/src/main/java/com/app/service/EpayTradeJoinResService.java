package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface EpayTradeJoinResService {

	DataGrid selectList(HttpServletRequest request); //查询列表

	JSON selectCountMoney(HttpServletRequest request); //条件查询总金额
}
