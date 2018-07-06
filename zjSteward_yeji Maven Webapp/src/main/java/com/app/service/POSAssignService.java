package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface POSAssignService {
	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);
	
	/**更新状态*/
	JSON updateStatus(HttpServletRequest request);

}
