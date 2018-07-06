package com.app.util.zjgj.webSocket;

import org.java_websocket.WebSocketImpl;

public class TestServerSocket {
	public static void main(String args[]){
		
		/**
		 * WebSocket 在线测试:http://www.blue-zero.com/WebSocket/
		 */
        WebSocketImpl.DEBUG = false;
        int port = 7898; // 端口
        WsServer s = new WsServer(port);
        s.start();
    }

}
