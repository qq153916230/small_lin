package com.app.util.zjgj.webSocket;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.java_websocket.WebSocketImpl;

/**
 * web项目，我们还需要在项目启动的时候开启websocket服务端线程，
 * 可以把启动的动作放在一个filter中，然后在web.xml里面配置这个filter，使它在项目启动时候运行
 * @author Administrator
 *
 */
public class StartFilter implements Filter {

    public void destroy() {

    }

    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain arg2) throws IOException, ServletException {

    }

    public void init(FilterConfig arg0) throws ServletException {
        this.startWebsocketInstantMsg();
    }

    /**
     * 启动即时聊天服务
     */
    public void startWebsocketInstantMsg() {
        WebSocketImpl.DEBUG = false;
        WsServer s;
        s = new WsServer(7898);
        s.start();
    }
}