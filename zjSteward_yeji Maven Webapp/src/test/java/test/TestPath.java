package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class TestPath {
	public static void main(String[] args) throws IOException {
		
		
		/*File directory = new File("");//参数为空
		String courseFile = directory.getCanonicalPath() ;
		System.out.println(courseFile); */
		System.out.println(System.getProperty("user.dir"));

		Properties prop = new Properties();
		try {
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"/downloadPath.properties"));
			prop.load(in); // /加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				System.out.println(key + ":" + prop.getProperty(key));
			}
			in.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		
	
	}
	
	
}
