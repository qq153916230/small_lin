package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.app.entity.Aidgold;
import com.app.entity.AidgoldCS;
import com.app.util.DataGrid;

public interface AidgoldCSService {
	/**列表展示*/
	DataGrid selectList(HttpServletRequest request); 
	/**增加一条数据*/
	void createData(HttpServletRequest request);

}
