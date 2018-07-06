package com.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.app.controller.daystaticController;
import com.app.service.DaystaticService;
import com.app.service.impl.DaystaticServiceImpl;

/**
 * 定时器 任务类 主要的业务代码 这个类 主要是继承 TimerTask并实现它的run 方法
 * 
 * @author sxl
 * 
 */
@Controller
public class Timer extends TimerTask {

	private static boolean isRunning = false;

	private ServletContext context = null;
	
	HttpServletRequest request;
	HttpServletResponse response;
	
	public Timer() {
		super();
	}

	public Timer(ServletContext context) {
		this.context = context;
	}

	@Override
	public void run() {

		if (!isRunning) {

			context.log("开始更新日统计表");
			/**
			 * 业务代码,定时向日统计表更新数据
			 */
			isRunning = true;
			
			
			
			//每次请求会打开一个网页
			/*try {
			Properties properties = System.getProperties();
	        String osName = properties.getProperty("os.name");
	          
	        System.out.println (osName);
	          
	        if (osName.indexOf("Linux") != -1) {
	            Runtime.getRuntime().exec("htmlview");
	        } else if (osName.indexOf("Windows") != -1){
	            Runtime.getRuntime().exec("explorer http://localhost:8080/zjSteward_yeji/daystatic/update");
	        } else {
	            throw new RuntimeException("Unknown OS.");
	        }
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			isRunning = false;
			context.log("日统计表更新完毕");
		} else {
			context.log("上一次任务执行还未结束");
		}
	}

}
