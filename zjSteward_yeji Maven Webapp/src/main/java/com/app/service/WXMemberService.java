package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.app.util.DataGrid;

public interface WXMemberService {

	/**列表展示*/
	DataGrid selectList(HttpServletRequest request);
	
	/**归宿地操作*/
	JSON selectGuiShuDi(HttpServletRequest request);
}
