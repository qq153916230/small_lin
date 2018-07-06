package com.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.FadadaService;
import com.app.util.ReadHTMLFilePath;
import com.fadada.sdk.client.FddClientBase;

@Controller
@RequestMapping("/fadada/*")
public class Fadada {
	
	@Autowired
	FadadaService fadadaService;
	
	/**2.调用个人CA注册接口*/
	@RequestMapping("syncPersonAuto")
	public String syncPersonAuto(HttpServletRequest request, HttpServletResponse response){
		return this.fadadaService.syncPersonAuto(request, response);
	}
	
	/**10.调用文档传输接口*/
	@RequestMapping("uploadDocs")
	public String uploadDocs(HttpServletRequest request, HttpServletResponse response){
		return this.fadadaService.uploadDocs(request, response);
	}
	
	/**调用模板传输接口*/
	@RequestMapping("uploadTemplate")
	public String uploadTemplate(HttpServletRequest request, HttpServletResponse response){
		return this.fadadaService.uploadTemplate(request, response);
	}
	
	/**调用合同生成接口*/
	@RequestMapping("generateContract")
	public String generateContract(HttpServletRequest request, HttpServletResponse response){
		return this.fadadaService.generateContract(request, response);
	}
	
	/**14.调用手动签署接口*/
	@RequestMapping("ExtSign")
	public String ExtSign(HttpServletRequest request, HttpServletResponse response){
		return "redirect:"+this.fadadaService.ExtSign(request, response);
	}
	
	/**24.调用归档接口*/
	@RequestMapping("contractFilling")
	public void contractFilling(HttpServletRequest request){
		this.fadadaService.contractFilling(request);
	}
	
	/**跳转地址*/
	@RequestMapping("returnUrl")
	public String returnUrl(HttpServletRequest request){
		return this.fadadaService.returnUrl(request);
	}
	/**异步通知地址*/
	@ResponseBody
	@RequestMapping("notifyUrl")
	public String notifyUrl(HttpServletRequest request){
		return this.fadadaService.notifyUrl(request);
	}
	
	/**2.调用个人CA注册接口-公司*/
	@RequestMapping("syncPersonAutoCom")
	public String syncPersonAutoCom(HttpServletRequest request, HttpServletResponse response){
		return this.fadadaService.syncPersonAutoCom(request, response);
	}
	
	/**14.调用手动签署接口*/
	@RequestMapping("ExtSignCom")
	public String ExtSignCom(HttpServletRequest request, HttpServletResponse response){
		return "redirect:"+this.fadadaService.ExtSignCom(request, response);
	}
	
	/**异步通知地址-公司调用*/
	@ResponseBody
	@RequestMapping("notifyUrlCom")
	public String notifyUrlCom(HttpServletRequest request){
		return this.fadadaService.notifyUrlCom(request);
	}
	
	/**根据mid查询 member_addr表*/
	@ResponseBody
	@RequestMapping("selectMemberAddr")
	public JSON selectMemberAddr(HttpServletRequest request){
		return this.fadadaService.selectMemberAddr(request);
	}
	
}
