package com.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AidgoldCSDao;
import com.app.entity.Aidgold;
import com.app.entity.AidgoldCS;
import com.app.service.AidgoldCSService;
import com.app.util.DataGrid;
@Service
public class AidgoldCSServiceImpl implements AidgoldCSService {
	
	@Autowired
	AidgoldCSDao aidgoldCSDao;

	@Override
	public void createData(HttpServletRequest request) {
		
		String smid = request.getParameter("mid");
		String said = request.getParameter("aid");
		String scsmodo = request.getParameter("csmodo");
		
		int mid = 0;
		int aid = 0;
		short csmodo = 0;
		
		if (!"".equals(smid)) {
			mid = Integer.parseInt(smid);
		}
		
		if (!"".equals(said)) {
			aid = Integer.parseInt(said);
		}
		
		if (!"".equals(scsmodo)) {
			csmodo = Short.valueOf(scsmodo);
		}
		
		
		
		//Date csdate = null;
		/*try {
			csdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(request.getParameter("csdate"));
		} catch (ParseException e) { e.printStackTrace(); }*/
		
		String cscont = request.getParameter("cscont");
		String remark = request.getParameter("remark");
		
		AidgoldCS aidgoldCS = new AidgoldCS();
		aidgoldCS.setMid(mid);
		aidgoldCS.setAid(aid);
		aidgoldCS.setCsmodo(csmodo);
		//aidgoldCS.setCsdate(csdate);
		aidgoldCS.setCscont(cscont);
		aidgoldCS.setRemark(remark);
		
		this.aidgoldCSDao.insertSelective(aidgoldCS);
	}
	
	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String aid = request.getParameter("aid");
		String skname = request.getParameter("skname");
		String jkcheck = request.getParameter("jkcheck");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("skname", skname);
		map.put("jkcheck", jkcheck);
		map.put("aid", aid);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.aidgoldCSDao.selectTotle(map));
		dg.setRows(this.aidgoldCSDao.selectList(map));
		return dg;
	}

}
