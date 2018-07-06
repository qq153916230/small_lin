package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface MemberAuthorService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);

	/**根据主键查找*/
	JSON selectByMid(HttpServletRequest request);
	
}
