package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.ParamDao;
import com.app.service.ParamService;
@Service
public class ParamServiceImpl implements ParamService {
	
	@Autowired
	ParamDao paramDao;

	/**根据pname获取pvalue*/
	@Override
	public JSON selectByPname() {
		String managerPass = this.paramDao.selectByPname("manager_password");
		
		JSONObject json = new JSONObject();
		json.put("status", 1);
		json.put("mp", managerPass);
		return json;
	}

}
