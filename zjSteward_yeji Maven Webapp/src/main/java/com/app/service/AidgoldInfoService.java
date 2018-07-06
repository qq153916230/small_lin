package com.app.service;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

public interface AidgoldInfoService {

	/**插入信息*/
	void insertSelective(HttpServletRequest req);

	/**根据mid查询*/
	JSON selectByMid(HttpServletRequest request);

}
