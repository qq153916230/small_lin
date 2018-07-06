package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.dao.FDDContractDao;
import com.app.entity.FDDContract;
import com.app.service.FDDContractService;
import com.app.util.DataGrid;
@Service
public class FDDContractServiceImpl implements FDDContractService {
	
	@Autowired
	FDDContractDao fddContractDao;

	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String userId = request.getParameter("user_id");
		String aidgoldId = request.getParameter("aidgold_id");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("userId", userId);
		map.put("aidgoldId", aidgoldId);
		
		if (page != null && !"".equals(page)) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.fddContractDao.selectTotle(map));
		dg.setRows(this.fddContractDao.selectList(map));
		return dg;
	}

	/**根据助力金id获取一条记录*/
	@Override
	public JSON selectByAidgoldId(HttpServletRequest request) {
		
		int aidgoldId = Integer.parseInt(request.getParameter("tid"));
		FDDContract fddContract = this.fddContractDao.selectByAidgoldId(aidgoldId);
		
		JSON json = (JSON) JSON.toJSON(fddContract);
		
		return json;
	}
	

}
