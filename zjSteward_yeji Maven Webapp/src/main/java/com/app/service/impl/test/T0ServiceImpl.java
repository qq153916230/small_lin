package com.app.service.impl.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.dao.test.T0Dao;
import com.app.entity.test.T0;
import com.app.service.test.T0Service;

@Service
public class T0ServiceImpl implements T0Service {
	@Autowired
	T0Dao t0Dao;

	@Override
	public JSON queryByPK(HttpServletRequest request) {
		int sid = Integer.parseInt(request.getParameter("sid"));
		T0 t = this.t0Dao.selectByPrimaryKey(sid);
		JSON json = (JSON) JSON.toJSON(t);
		return json;
	}

}
