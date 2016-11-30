package com.wqx.test.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

	public static final String CONFIGPATH = "config.properties";
	public static final Properties prop = new Properties(); 
	public static String getPropertyByName(String propertyName){
		String propertyValue = null;
		FileInputStream fis = null;    
		String path = null;
		try {
			// 获取当前类加载的根目录，如：/C:/Program Files/Apache/Tomcat 7.0/webapps/fee/WEB-INF/classes/
			path = ConfigLoader.class.getClassLoader().getResource("").toURI().getPath();
			// 把文件读入文件输入流，存入内存中    
			fis = new FileInputStream(new File(path + CONFIGPATH));       
			//加载文件流的属性       
			prop.load(fis); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		propertyValue = prop.getProperty(propertyName);
		return propertyValue;
	}
	
}
