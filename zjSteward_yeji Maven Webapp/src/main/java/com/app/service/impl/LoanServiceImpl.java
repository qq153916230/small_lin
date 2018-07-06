package com.app.service.impl;

import java.awt.datatransfer.StringSelection;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.LoanDao;
import com.app.entity.Loan;
import com.app.entity.daystatic;
import com.app.service.LoanService;
import com.app.util.WriteExcel;

@Service
public class LoanServiceImpl implements LoanService {
	
	@Resource
	LoanDao loanDao;
	
	//查询所有
	@Override
	public List<Loan> selectAll_loans() {
		return this.loanDao.selectAllUser();
	}
	
	//查询新申请用户
	@Override
	public List<Loan> selectNewRegister() {
		return this.loanDao.selectNewRegister();
	}
	
	//	批量修改状态
	@Override
	public void updateByTidSelective(String[] tidArray,int status) {
		int[] tids = new int[tidArray.length];
		for (int i = 0; i < tidArray.length; i++) {
			tids[i] = Integer.parseInt(tidArray[i]);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("tids", tids);
		this.loanDao.updateStatusByTids(map);
	}
	
	//根据条件搜索
	@Override
	public List<Loan> search(String regTimeFrom, String regTimeTo, 
							String phone,String status,String page,String rows) {
		//防止参数没有值得时候，mybatis的if判断语句会出问题
		/*if (regTimeFrom.equals("")){
			regTimeFrom = null; 
		} else {
			regTimeFrom = formatDate(regTimeFrom);
		}
		if (regTimeTo.equals("")){
			regTimeTo = null; 
		} else {
			regTimeTo = formatDate(regTimeTo);
		}*/
		/*if (phone.equals("")){
			phone = null; 
		}
		if (status.equals("")){
			status = null; 
		}*/
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", regTimeFrom);
		map.put("to", regTimeTo);
		map.put("tel", phone);
		map.put("status", status);
		page += "%";
		if(!page.equals("null%")){
			page = page.substring(0, page.length()-1);
			int pag = (Integer.parseInt(page)-1)*100;
			map.put("page", pag);
			map.put("rows", Integer.parseInt(rows));
		} else {
			map.put("page", 0);
			map.put("rows", 100);
		}
		return this.loanDao.search(map);
	}
	
		//根据条件查询总数 用于datagrid总数的查询
		@Override
		public int searchCount(String regTimeFrom, String regTimeTo, 
								String phone,String status,String page,String rows) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("from", regTimeFrom);
			map.put("to", regTimeTo);
			map.put("tel", phone);
			map.put("status", status);
			page += "%";
			if(!page.equals("null%")){
				page = page.substring(0, page.length()-1);
				int pag = (Integer.parseInt(page)-1)*100;
				map.put("page", pag);
				map.put("rows", Integer.parseInt(rows));
			} else {
				map.put("page", 0);
				map.put("rows", 100);
			}
			return this.loanDao.searchCount(map);
		}
	
	// 把 "/" 分隔的时间转为 "-" 分隔
	public String formatDate(String date){
		String[] s = date.split("/");
		return s[2] + "-" +s[0] + "-" + s[1];
	}
	
	/*设置导出excel的内容*/
	@Override
	public InputStream getInputStream() throws Exception {
		String[] title = new String[]{"姓名","联系方式","工作性质","房产信息","状态","日期"};
		List<Loan> list =  this.loanDao.selectAllUser();
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); /*HH:mm:ss*/
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = new Object[title.length];
			
			obj[0] = list.get(i).getUname();
			obj[1] = list.get(i).getPhone(); 
			obj[2] = list.get(i).getWork(); 
			obj[3] = list.get(i).getHouse(); 
			
			//把状态由 数字 格式化成 汉字
			int num = list.get(i).getStatus();
			String string;
			switch (num) {
			case 1: string = "受理中"; break;
			case 2: string = "通过"; break;
			case 3: string = "未通过"; break;
			default: string = "新申请"; break; }
			
			obj[4] = string;
			obj[5] = df.format(list.get(i).getRegdate());
			
			dataList.add(obj);
		}
		WriteExcel excel = new WriteExcel(title, dataList);
		InputStream inputStream;
		inputStream = excel.export();
		
		return inputStream;
	}

	

}
