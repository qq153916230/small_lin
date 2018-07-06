package com.app.util.zjgj;

import javax.servlet.http.HttpServletRequest;

public class ZjUrl {
	
	private static HttpServletRequest req = ZjUtils.getRequest();
	
	/** 协议 */
	//public static final String SERVER_PROTOCOL_AUTO = req.getProtocol(); 
	
	/** 本地域名 */
	
	
	/** 域名 	localhost */
	public static final String SERVER_NAME_AUTO = req.getServerName();
	
	/** 端口 8080 */
	public static final int SERVER_PORT_AUTO = req.getServerPort();
	
	/** 服务器地址		http://localhost:8080 */
	public static final String BASE_SERVER_URL_AUTO = "http://" + SERVER_NAME_AUTO + ":" + SERVER_PORT_AUTO;
	
	/** 本地域名  + 端口号	http://localhost:8080 */
	public static final String BASE_SERVER_URL_LOCAL = "http://localhost:" + SERVER_PORT_AUTO;
	
	/** 新颜地址 http://server_name:server_port/xinYanAPI/ */
	public static final String XY_URL_AUTO = BASE_SERVER_URL_AUTO + "/xinYanAPI/";
	
	
	
	public static void main(String[] args) {
		
	}
		
}
