package com.app.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.inject.New;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.service.DaystaticService;
import com.app.util.DataGrid;
/*@Component*/
import com.app.util.DownloadFilePath;

@Controller
public class daystaticController {
	
	@Resource
	DaystaticService daystaticService;
	

	//定时任务  定时向日统计表插入数据(更新日统计表) 每隔5秒执行一次 0/5 * * * * ?  0 0 1 * * ? /*@Scheduled(cron = "0 0 1 * * ?")*/
	//每天1点执行更新操作
	@RequestMapping("/daystatic/update")
	public void updateDaystatic(){
		//daystaticService.insertBySelectJoin(); //取消使用
	}
	
	
	@ResponseBody
	@RequestMapping("/daystatic/list")
	public DataGrid listDataGrid(HttpServletRequest httpRequest){
		String paydate = httpRequest.getParameter("smonth");
		paydate = paydate + "%";
		
		//处理空指针异常,null 不能用equals 要用 == 判断
		if (paydate.equals("null%") || paydate.equals("%")) {
			paydate = null;
		}
		String merchantcode = httpRequest.getParameter("merchantcode");
		return this.daystaticService.listDataGrid(paydate,merchantcode);
	}
	
	/*导出*/
	@ResponseBody
	@RequestMapping("/daystatic/export")
	public JSON export() throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String string = df.format(new Date());
		
		InputStream is = this.daystaticService.getInputStream();
		
		DownloadFilePath path = new DownloadFilePath();
		//服务器存放下载文件的路径
		File file = new File(path.getDownloadFilePath()+"yj"+string+".xls");/**/

		FileOutputStream outputStream = new FileOutputStream(file);
		
		
		/*response.setContentType("application/vnd.ms-excel");
		response.setHeader("contentDisposition", "attachment;filename=AllUsers.xls");
		ServletOutputStream output = response.getOutputStream();*/
		IOUtils.copy(is, outputStream);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("file", "yj"+string+".xls");
		
		is.close();
		outputStream.flush();
		outputStream.close();
//		output.close();
//		request.getRequestDispatcher("http://localhost:8080/zjSteward_yeji/download/temp3.xls");
//		response.sendRedirect("http://localhost:8080/zjSteward_yeji/download/temp3.xls");
		return jsonObject;
	}
}
