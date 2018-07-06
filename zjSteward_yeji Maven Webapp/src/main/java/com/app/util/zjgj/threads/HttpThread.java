package com.app.util.zjgj.threads;

import javax.servlet.http.HttpServletRequest;

public class HttpThread extends Thread {
	
	HttpServletRequest req;
	
	public HttpThread() {}

	public HttpThread(HttpServletRequest req) {
		this.req = req;
	}

	@Override
	public void run() {
		
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String c = req.getParameter("c");
		System.out.println("HttpThread get a:" + a + " b:" + b + " c:" + c);
	}
	
}
