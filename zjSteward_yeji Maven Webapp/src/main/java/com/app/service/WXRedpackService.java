package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.app.util.DataGrid;

public interface WXRedpackService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

	/**条件查询总金额*/
	String selectCountMoney(HttpServletRequest request); 

}
