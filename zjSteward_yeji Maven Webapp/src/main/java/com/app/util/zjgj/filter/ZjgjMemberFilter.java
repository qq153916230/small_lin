package com.app.util.zjgj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.dao.MemberAuthorDao;
import com.app.util.zjgj.ZjUtils;

public class ZjgjMemberFilter implements Filter  {
	
	@Autowired
	MemberAuthorDao memberAuthorDao;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		String uname = req.getParameter("username");
		String username = session.getAttribute("username") == null?null:(String)session.getAttribute("username");
		
		
		if (uname != null && uname.equals(username)) {
			chain.doFilter(request, response);
		} else {
			response.getWriter().write(ZjUtils.REQUEST_LIMIT_MSG);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
