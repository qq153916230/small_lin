package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.app.util.DataGrid;

public interface AidgoldBackService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

}
