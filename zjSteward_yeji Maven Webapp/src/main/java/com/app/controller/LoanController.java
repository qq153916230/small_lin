package com.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.entity.Loan;
import com.app.service.LoanService;
import com.app.util.DataGrid;
import com.app.util.DownloadFilePath;

@Controller
@RequestMapping
public class LoanController {
	@Resource
	LoanService loanService;
	
	
	//查询所有loan用户
	@ResponseBody
	@RequestMapping("/loan/selectNewRegister")
	public DataGrid selectAllUsers() {
		DataGrid dg = new DataGrid();
		dg.setTotal((long) this.loanService.selectNewRegister().size());/**/
		dg.setRows(this.loanService.selectNewRegister());
		return dg;
	}
	
	//更新状态
	@ResponseBody
	@RequestMapping("/loan/updateStatus")
	public JSON updateStatus(HttpServletRequest request){
		//获取json传过来的数据
		String tidString = request.getParameter("tids");//tids是一个逗号分隔的字符串 1,2,3
		String[] tids = tidString.split(",");
		int status = Integer.parseInt(request.getParameter("newStatus"));
		
		//执行更新操作
		this.loanService.updateByTidSelective(tids,status);
		
		//向页面返回数据
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ret", 1);
		jsonObject.put("msg", "更新成功");
		return jsonObject;
	}
	
	// 搜索-展示
	@ResponseBody
	@RequestMapping("/loan/search")
	public DataGrid search(HttpServletRequest request) {
		//获取参数
		String regTimeFrom = request.getParameter("regTimeFrom");
		String regTimeTo = request.getParameter("regTimeTo");
		String phone = request.getParameter("phone");
		String status = request.getParameter("searchStatus"); 
		String page = request.getParameter("page"); 
		String rows = request.getParameter("rows"); 
		
		
		//获取查询到的总数
		Long totle = (long)this.loanService.searchCount(regTimeFrom,regTimeTo,phone,status,page,rows); 
		//获取loan集合
		List<Loan> list = this.loanService.search(regTimeFrom,regTimeTo,phone,status,page,rows);
		//封装对象
		DataGrid dg = new DataGrid();
		dg.setTotal((long)totle);
		dg.setRows(list);
		return dg;
	}
	
	/*导出*/
	@ResponseBody
	@RequestMapping("/loan/export")
	public JSON export() throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String sdate = df.format(new Date());
		
		InputStream is = this.loanService.getInputStream();
		
		DownloadFilePath path = new DownloadFilePath();
		//服务器存放下载文件的路径
		File file = new File(path.getDownloadFilePath()+"hld"+sdate+".xls");/*D:/soft/apache-tomcat-8.5.12/webapps/zjSteward_yeji/download/*/
		File fileParent = file.getParentFile();  
		if(!fileParent.exists()){  
		    fileParent.mkdirs();  
		} 
		
		FileOutputStream outputStream = new FileOutputStream(file);

		IOUtils.copy(is, outputStream);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("file", "hld"+sdate+".xls");
		
		is.close();
		outputStream.flush();
		outputStream.close();

		return jsonObject;
	}
	
}
