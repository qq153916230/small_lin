package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.service.EpayPaymentService;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/epayPayment/*")
public class EpayPaymentController {
	
	@Autowired
	EpayPaymentService epayPaymentService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.epayPaymentService.selectList(request);
	}

}
