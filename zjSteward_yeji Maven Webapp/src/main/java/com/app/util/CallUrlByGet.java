package com.app.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * 跨域调用url获取目标服务器返回结果
 * */
public class CallUrlByGet {
	
	public static String callUrlByGet(String callurl,String charset){  
        String result = "";  
        try {  
            URL url = new URL(callurl);  
            URLConnection connection = url.openConnection();  
          //设置通用的请求属性
          connection.setRequestProperty("accept", "*/*");
          connection.setRequestProperty("connection", "Keep-Alive");
          connection.setRequestProperty("user-agent",
                  "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();  
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));  
            
            String line;  
            while((line = reader.readLine())!= null){   
                result += line;  
                result += "\n";  
            }  
            if (reader != null) {
            	reader.close();
			}
        } catch (Exception e) {  
            //e.printStackTrace(); 
            return "";  
        }  //System.out.println("---！！"+result);
        return result;    
    }
	
	public static void main(String[] args) {
		String str = URLEncoder.encode("雷国洪");
		System.out.println(str);
		callUrlByGet("http://localhost:8080/zjgj2tj/app/test1?a="+str,"utf-8");
	}
}
