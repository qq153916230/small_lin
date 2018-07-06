package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.app.dao.EpayHkResultDao;
import com.app.service.EpayHkResultService;
import com.app.util.DataGrid;
@Service
public class EpayHkResultServiceImpl implements EpayHkResultService {
	@Resource
	EpayHkResultDao epayHkResultDao;

	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取参数
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String mobile = request.getParameter("mobile");
		String searchStatus = request.getParameter("searchStatus");
		
		//保存查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", regTimeFrom);
		if(regTimeTo != ""){ map.put("to", regTimeTo + " 23:59:59"); }
		map.put("mobile", mobile);
		map.put("searchStatus", searchStatus);
		//分页处理
		if (page != null && rows != null) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		//处理返回页面数据
		DataGrid dataGrid = new DataGrid();
		dataGrid.setTotal((long) this.epayHkResultDao.selectCountCondition(map));
		dataGrid.setRows(this.epayHkResultDao.selectList(map));
		return dataGrid;
	}

	//查询总金额
	@Override
	public String selectCountMoney(HttpServletRequest request) {

		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String mobile = request.getParameter("mobile");
		String searchStatus = request.getParameter("searchStatus");
		
		//保存查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", regTimeFrom);
		if(regTimeTo != ""){ map.put("to", regTimeTo + " 23:59:59"); }
		map.put("mobile", mobile);
		map.put("searchStatus", searchStatus);
				
		return this.epayHkResultDao.selectCountMoney(map);
	}

}
