package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.WXRedpackDao;
import com.app.service.WXRedpackService;
import com.app.util.DataGrid;
@Service
public class WXRedpackServiceImpl implements WXRedpackService {
	@Autowired
	WXRedpackDao wxRedpackDao;

	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String openid = request.getParameter("openid");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("openid", openid);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.wxRedpackDao.selectTotle(map));
		dg.setRows(this.wxRedpackDao.selectList(map));
		return dg;
	}
	

	/**查询总金额*/
	@Override
	public String selectCountMoney(HttpServletRequest request) {
		
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String openid = request.getParameter("openid");
		
		//保存查询条件
		Map<String, Object> map = new HashMap<String, Object>();

		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		
		map.put("openid", openid);
		
		return this.wxRedpackDao.selectCountMoney(map);
	}


}
