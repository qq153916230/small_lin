package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

public interface AidgoldFQHKService {

	/**根据助理金id插入分期详情*/
	JSON insert(HttpServletRequest request);

	/**修改状态*/
	JSON updateStatus(HttpServletRequest request);

	/**删除分期列表*/
	JSON deleteFQList(HttpServletRequest request);

}
