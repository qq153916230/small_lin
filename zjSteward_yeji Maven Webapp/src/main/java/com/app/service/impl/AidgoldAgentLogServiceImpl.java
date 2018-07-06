package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AidgoldAgentLogDao;
import com.app.service.AidgoldAgentLogService;
import com.app.util.DataGrid;
@Service
public class AidgoldAgentLogServiceImpl implements AidgoldAgentLogService {
	
	@Autowired
	AidgoldAgentLogDao aidgoldAgentLogDao;
	
	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String mid = request.getParameter("mid");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (page != null && !"".equals(page)) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		map.put("mid", mid);
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.aidgoldAgentLogDao.selectTotle(map));
		dg.setRows(this.aidgoldAgentLogDao.selectList(map));
		return dg;
	}

}
