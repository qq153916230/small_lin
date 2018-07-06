package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.app.dao.MemberAuthorDao;
import com.app.entity.Aidgold;
import com.app.entity.MemberAuthor;
import com.app.service.MemberAuthorService;
import com.app.util.DataGrid;
@Service
public class MemberAuthorServiceImpl implements MemberAuthorService {
	
	@Autowired
	MemberAuthorDao memberAuthorDao;
	
	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String sname = request.getParameter("sname");
		String mobile = request.getParameter("mobile");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("sname", sname);
		map.put("mobile", mobile);
		
		if (page != null && !"".equals(page)) {
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		}
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.memberAuthorDao.selectTotle(map));
		dg.setRows(this.memberAuthorDao.selectList(map));
		return dg;
	}

	/**根据主键查找*/
	@Override
	public JSON selectByMid(HttpServletRequest request) {
		String rid = request.getParameter("mid");
		int mid = 0;
		if (rid != null && !"".equals(rid) && !"null".equals(rid)) {
			mid = Integer.parseInt(rid);
		}
		MemberAuthor memberAuthor = this.memberAuthorDao.selectByPrimaryKey(mid) ;
		JSON json = (JSON) JSON.toJSON(memberAuthor);
		return json;
	}
}
