package com.app.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.app.service.WXMemberService;
import com.app.util.CallUrlByGet;
import com.app.util.DataGrid;

@Controller
@RequestMapping("/wxMember/*")
public class WXMemberController {
	@Autowired
	WXMemberService wxMemberService;
	
	/**列表展示*/
	@ResponseBody
	@RequestMapping("selectList")
	public DataGrid selectList(HttpServletRequest request){
		return this.wxMemberService.selectList(request);
	}
	
	/**查询归属地*/
	@ResponseBody
	@RequestMapping("selectGuiShuDi")
	public JSON selectGuiShuDi(HttpServletRequest request) throws Exception{
		return this.wxMemberService.selectGuiShuDi(request);
	}

}
