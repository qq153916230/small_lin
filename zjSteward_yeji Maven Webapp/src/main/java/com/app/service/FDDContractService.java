package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface FDDContractService {
	
	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

	/**根据助力金id获取一条记录*/
	JSON selectByAidgoldId(HttpServletRequest request);

}
