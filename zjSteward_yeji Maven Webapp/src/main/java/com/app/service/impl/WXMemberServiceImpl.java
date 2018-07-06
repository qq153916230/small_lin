package com.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.WXMemberDao;
import com.app.service.WXMemberService;
import com.app.util.CallUrlByGet;
import com.app.util.DataGrid;
@Service
public class WXMemberServiceImpl implements WXMemberService {
	
	@Autowired
	WXMemberDao wxMemberDao;

	/**列表展示*/
	@Override
	public DataGrid selectList(HttpServletRequest request) {
		//获取页面查询条件
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String tel = request.getParameter("tel");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (!"".equals(regTimeFrom) && !"".equals(regTimeTo)) {
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo + " 23:59:59");
		}
		map.put("tel", tel);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.wxMemberDao.selectTotle(map));
		dg.setRows(this.wxMemberDao.selectList(map));
		return dg;
	}

	@Override
	public JSON selectGuiShuDi(HttpServletRequest request) {
		//https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=
		final String urlAddres = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_name=guishudi&query=";
		
		String phone = request.getParameter("phone");
		String url = urlAddres+phone;    
		String result = CallUrlByGet.callUrlByGet(url, "GBK");

        if(result!=null && !"".equals(result)){
        	result = result.substring(result.indexOf("[")+1, result.lastIndexOf("]"));
        } 
		//request.setCharacterEncoding("UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		//PrintWriter out = response.getWriter();  
		//out.print(result);  
		//out.close(); 
        JSONObject jsonObject = JSONObject.parseObject(result);
        
        String gsd = "";
        if (jsonObject != null && !"".equals(jsonObject)) {
        	gsd = jsonObject.getString("prov") + jsonObject.getString("city");
		}
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("gsd", gsd);
        this.wxMemberDao.updateByTel(map);
        
		return jsonObject;
	}

}
