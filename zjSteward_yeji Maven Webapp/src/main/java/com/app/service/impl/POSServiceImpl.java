package com.app.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.POSDao;
import com.app.service.POSService;
import com.app.util.DataGrid;
import com.app.util.WriteExcel;
import com.app.util.zjgj.ZjUtils;
@Service
public class POSServiceImpl extends ZjUtils implements POSService {
	
	@Autowired
	POSDao posDao;

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
		map.put("possn", possn);
		map.put("merchantname", merchantname);
		map.put("merchantcode", merchantcode);
		
		int pag = (Integer.parseInt(page)-1)*100;
		map.put("page", pag);
		map.put("rows", Integer.parseInt(rows));
		
		DataGrid dg = new DataGrid();
		dg.setTotal(this.posDao.selectTotle(map));
		dg.setRows(this.posDao.selectList(map));
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
		this.posDao.updateStatus(map);
		
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "更新成功");
		return jsonObject;
	}

	/** 根据代理商手机 导出 代理名下已激活的POS机 */
	@Override
	public JSON exportAgentPosByMobile(String mobile) {

		List<Map<String, Object>> data = this.posDao.selectExportAgentPosByMobile(mobile);
		
		InputStream is = null;
		OutputStream os = null;
		String fileName = System.currentTimeMillis()+"";
		String filePath = getContextRealPath(getRequest())+"\\download\\txreward\\"+fileName+".xls";
		String serverPath = getContextPath(getRequest()) + "/download/txreward/"+fileName+".xls";
		
		try {
			is = getExportAgentInputStream(data);
			
			os = new FileOutputStream(new File(filePath));
			IOUtils.copy(is, os);
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusMsgJson(1, serverPath);
	}
	
	public InputStream getExportAgentInputStream(List<Map<String, Object>> list) throws Exception {
		//merchantname, merchantcode, possn, vip_mobile, vip_nick
		String[] title = new String[]{"序号","商户名","商户号","机身号","代理商","代理商手机"};//,"激活时间"
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); /*HH:mm:ss*/
		//System.out.println(list.toString()); 
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Object[] obj = new Object[title.length];
			
			obj[0] = i+1;
			obj[1] = dealNullExcel(map.get("merchantname"));
			obj[2] = dealNullExcel(map.get("merchantcode"));
			obj[3] = dealNullExcel(map.get("possn"));
			obj[4] = dealNullExcel(map.get("vip_nick"));
			obj[5] = dealNullExcel(map.get("vip_mobile"));
			
			dataList.add(obj);
		}
		
		WriteExcel excel = new WriteExcel(title, dataList);
		InputStream inputStream;
		inputStream = excel.export();
		
		return inputStream;
	}
	
	

}
