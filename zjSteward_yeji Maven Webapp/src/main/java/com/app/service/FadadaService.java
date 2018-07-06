package com.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public interface FadadaService {
	
	/**2.调用个人CA注册接口*/
	String syncPersonAuto(HttpServletRequest request, HttpServletResponse response);
	
	/**10.调用文档传输接口*/
	String uploadDocs(HttpServletRequest request, HttpServletResponse response);

	/**调用模板传输接口*/
	String uploadTemplate(HttpServletRequest request, HttpServletResponse response);
	
	/**调用合同生成接口*/
	String generateContract(HttpServletRequest request, HttpServletResponse response);

	/**14.调用手动签署接口*/
	String ExtSign(HttpServletRequest request, HttpServletResponse response);

	/**24.调用归档接口*/
	void contractFilling(HttpServletRequest request);

	/**跳转地址*/
	String returnUrl(HttpServletRequest request);
	
	/**异步通知地址*/
	String notifyUrl(HttpServletRequest request);

	/**2.调用个人CA注册接口-公司*/
	String syncPersonAutoCom(HttpServletRequest request, HttpServletResponse response);

	/**14.调用手动签署接口*/
	String ExtSignCom(HttpServletRequest request, HttpServletResponse response);

	/**异步通知地址-公司调用*/
	String notifyUrlCom(HttpServletRequest request);

	/**根据mid查询 member_addr表*/
	JSON selectMemberAddr(HttpServletRequest request);

	
}
