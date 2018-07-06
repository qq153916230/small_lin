package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.POSAssignDao;
import com.app.service.POSAssignService;
import com.app.util.DataGrid;
@Service
public class POSAssignServiceImpl implements POSAssignService {
	
	@Autowired
	POSAssignDao posAssignDao;
	
	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String isreward = request.getParameter("isreward");
		String bluetype = request.getParameter("bluetype");
		String mobile = request.getParameter("mobile");
		String possn = request.getParameter("possn");
		String merchantname = request.getParameter("merchantname");
		String merchantcode = request.getParameter("merchantcode");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("isreward", isreward);
		map.put("bluetype", bluetype);
		map.put("mobile", mobile);
		map.put("possn", possn);
		map.put("merchantname", merchantname);
		map.put("merchantcode", merchantcode);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.posAssignDao.selectTotle(map));
		dg.setRows(this.posAssignDao.selectList(map));
		return dg;
	}
	
	/**批量修改状态*/
	@Override
	public JSON updateStatus(HttpServletRequest request) {
		
		//获取json传过来的数据
		String idString = request.getParameter("ids");//ids是一个逗号分隔的字符串
		String[] idArray = idString.split(",");
		int status = Integer.parseInt(request.getParameter("newStatus"));
		
		/*int[] ids = new int[idArray.length];
		for (int i = 0; i < idArray.length; i++) {
			ids[i] = Integer.parseInt(idArray[i]);
		}*/
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("ids", idArray);
		this.posAssignDao.updateStatus(map);
		
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "更新成功");
		return jsonObject;
	}

}
