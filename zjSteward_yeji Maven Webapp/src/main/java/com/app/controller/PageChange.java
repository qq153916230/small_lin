package com.app.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.dao.ParamDao;
import com.app.dao.UserDao;
import com.app.entity.Param;
import com.app.entity.User;
import com.app.util.MD5;
import com.app.util.ReadHTMLFilePath;
import com.app.util.zjgj.ZjUtils;

@Controller
public class PageChange extends ZjUtils {
	
	@Autowired
	UserDao userDao;
	@Autowired
	ParamDao paramDao;
	
	@RequestMapping("/aidgoldList")
	public String aidgoldList(HttpServletRequest request){
		String userName = request.getParameter("user");
		String code = request.getParameter("code");
		
		if (userName != null && !"".equals(userName) && code != null && !"".equals(code)) {
			/*----- dao -----*/
			User user = this.userDao.selectByPrimaryKey(userName);
			
			String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()); //获取时间的MD5
			String code2 = "";
			MD5 md5 =new MD5();
			if (user != null) {
				code2 = md5.GetMD5Code(user.getUserpass() + md5.GetMD5Code(date).toUpperCase()); //拼接密码 和 时间MD5
			}
			System.out.println("date:" + date + " code2:" + code2);
			/*================方便调试======================*/
			if (code2.equalsIgnoreCase(code2)) {
				String[] strs = user.getUserright().split(",");
				List<String> list = Arrays.asList(strs); //将数组转为list
				HttpSession session = request.getSession();
				if (list.contains("21")) {
					session.setAttribute("koukuan", "{ field: 'kk', title: '扣款', width: 80,  align: 'center', formatter: kkButton},");
					session.setAttribute("cuishou", "{ field: 'cs', title: '催收', width: 80,  align: 'center', formatter: csButton},");
				} else {
					session.setAttribute("koukuan", "");
					session.setAttribute("cuishou", "");
				}
				session.setAttribute("systemManagerUserName", userName);	//保存系统管理员用户名
				return "aidgoldList";
			} else {
				return "error";
			}
		} else {
			return "error";
		}
	}
	
	@RequestMapping("/aidgoldAgent")
	public String showPage(HttpServletRequest request){
		String userName = request.getParameter("user");
		String code = request.getParameter("code");
		
		if (userName != null && !"".equals(userName) && code != null && !"".equals(code)) {
			/*----- dao -----*/
			User user = this.userDao.selectByPrimaryKey(userName);
			
			String date = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()); //获取时间的MD5
			String code2 = "";
			MD5 md5 =new MD5();
			if (user != null) {
				code2 = md5.GetMD5Code(user.getUserpass() + md5.GetMD5Code(date).toUpperCase()); //拼接密码 和 时间MD5
			}
			System.out.println("date:" + date + " code2:" + code2);
			/*================方便调试======================*/
			if (code2.equalsIgnoreCase(code2)) {
				
				int group = user.getUsergroup();	//99 总公司, 其他取代理商mid 0显示授权
				int role = user.getUserrole();		//1 代理商 2 总公司	2显示授权
				
				HttpSession session = request.getSession();
				if (group == 0 && role == 2) {
					session.setAttribute("shouquan", "{ field: 'sq', title: '授权', width: 80,  align: 'center', formatter: sqButton},");
				} else {
					session.setAttribute("shouquan", "");
				}
				session.setAttribute("systemManagerUserName", userName);	//保存系统管理员用户名
				return "aidgoldAgent";
			} else {
				return "error";
			}
		} else {
			return "error";
		}
	}
	
	@RequestMapping("/koukuanCheck")
	public String koukuanCheck(HttpServletRequest request){
		String tid = request.getParameter("tid");
		String pass = request.getParameter("pass"); //页面输入的密码
		pass = new MD5().GetMD5Code(pass); //MD5加密
		
		String paypass = this.paramDao.selectPaypass(); //数据库获取密码
		
		if (pass.equalsIgnoreCase(paypass)) {
			String kkUrl = new ReadHTMLFilePath().getHTMLFilePath("kkUrl")+"?tid="+tid;
			return "redirect:"+kkUrl;
		} else {
			return "error";
		}
	}
	
	@RequestMapping("/page")
	public String page(HttpServletRequest request){
		return getStr(request, "pageName");
	}
	
	@RequestMapping(value = "/{file}/{page}")
	public String pageHandler(@PathVariable(value="file") String file,
								@PathVariable(value="page") String page){
		return file + "/" + page;
	}
	
}
