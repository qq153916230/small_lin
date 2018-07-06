package com.app.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 获取config.propertis文件
 */
public class ReadHTMLFilePath {
	
		//获取配置文件里面的文件路径（downloadPath）
		public String getHTMLFilePath(String pathName) {
			Properties prop = new Properties();
			Map<String, String> map = new HashMap<String, String>();
			
			//web的clsspath路径
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			try {
				// 读取属性文件config.properties
				InputStream in = new BufferedInputStream(new FileInputStream(path+"/config.properties"));
				prop.load(in); // /加载属性列表
				
				Iterator<String> it = prop.stringPropertyNames().iterator();
				while (it.hasNext()) {
					String key = it.next();
					map.put(key, prop.getProperty(key));
				}
				in.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			return map.get(pathName);
		}

}
