package com.app.util.zjgj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.app.util.zjgj.ZjUtils;

public class ZjgjCommonFilter implements Filter  {
	
	static Logger login_log = Logger.getLogger("zjgjCommonFilter");	//
	
	/** 拦截器开关  */
	private static boolean openFilter = false;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String openFilterStr =  filterConfig.getInitParameter("openFilter");
		if (openFilterStr != null && ("true".equals(openFilterStr) || "false".equals(openFilterStr)) ) {
			openFilter = Boolean.parseBoolean(openFilterStr);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//System.err.println("doFilter:" + req.getParameter("a"));
		
		//login_log.info("ip:" + request.getRemoteAddr() + " 真实ip:" + ZjUtils.getIp(req) + " " + req.getRequestURI() + "?" + req.getQueryString());
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		resp.setContentType("text/plain; charset=utf-8"); 
		resp.setHeader("Access-Control-Allow-Origin","*");
		
		// openFilter == true 打开拦截器
		if (openFilter) {
			String url = req.getRequestURI();
			// 不拦截登入请求
			if (url != null && url.indexOf("login") != -1) {
				chain.doFilter(request, response);
			} else {
				
				HttpSession session = req.getSession();
				String username =  (String) session.getAttribute("username");

				if (username != null) {	//session 中有用户名表示已登入 不拦截
					chain.doFilter(request, response);
				} else {	//拦截
					resp.getWriter().write(ZjUtils.REQUEST_LIMIT_MSG);
				}
			}
			
		} else {
			chain.doFilter(request, response);
		}
		
		
	}


	@Override
	public void destroy() {
		System.out.println("do destroy()");
	}

}
