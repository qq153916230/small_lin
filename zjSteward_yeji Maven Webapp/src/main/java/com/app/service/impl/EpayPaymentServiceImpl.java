package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.EpayPaymentDao;
import com.app.service.EpayPaymentService;
import com.app.util.DataGrid;
@Service
public class EpayPaymentServiceImpl implements EpayPaymentService {
	@Autowired
	EpayPaymentDao epayPaymentDao;

	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String acctname = request.getParameter("acctname");
		String mobile = request.getParameter("mobile");
		String status = request.getParameter("status");
		String tid = request.getParameter("tid");
		String ptype = request.getParameter("ptype");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("acctname", acctname);
		map.put("mobile", mobile);
		map.put("status", status);
		map.put("ptype", ptype);
		map.put("tid", tid);
		
		if (page != null && !"".equals(page)) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.epayPaymentDao.selectTotle(map));
		dg.setRows(this.epayPaymentDao.selectList(map));
		return dg;
	}
}
