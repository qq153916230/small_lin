package com.app.util.zjgj.threads;

import javax.servlet.http.HttpServletRequest;

public class TestThread implements Runnable {
	
	HttpServletRequest req;
	

	public TestThread(HttpServletRequest req) {
		this.req = req;
	}
	@Override
	public void run() {
		String str = req.getParameter("a");
		String totle = str; System.out.println(totle);
		totle += str; System.out.println(totle);
		totle += str; System.out.println(totle);
		totle += str; System.out.println(totle);
		totle += str; System.out.println(totle);
		
	}

}
