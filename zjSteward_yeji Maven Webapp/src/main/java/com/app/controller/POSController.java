package com.app.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.POSService;
import com.app.util.DataGrid;
@Controller
@RequestMapping("/pos/*")
public class POSController {
	
	@Autowired
	POSService posService;

	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.posService.selectList(request);
	}
	
	/**更新状态*/
	@RequestMapping("updateStatus")
	public void updateStatus(HttpServletRequest request, HttpServletResponse response){
		JSON json = this.posService.updateStatus(request);
		response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8"); 
	    response.setHeader("Access-Control-Allow-Origin","*");
	    PrintWriter out = null; 
	    try {
			out = response.getWriter();
			out.append(json.toString());  
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }
	}
	
	/** 根据代理商手机 导出 代理名下已激活的POS机 */
	@ResponseBody
	@RequestMapping(value = "export/agent/{mobile}")
	public JSON exportAgentPosByMobile(@PathVariable(value="mobile") String mobile){
		return this.posService.exportAgentPosByMobile(mobile);
	}

}
