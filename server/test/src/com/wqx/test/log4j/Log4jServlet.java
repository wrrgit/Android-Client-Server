package com.wqx.test.log4j;


import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.jsp.jstl.core.Config;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class Log4jServlet extends HttpServlet {

	private boolean Debug = false;
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init(ServletConfig config) throws ServletException {
		// Put your code here
		System.out.println("Log4jServlet 正在初始化log4j日志设置信息");
		String log4jLocation = config.getInitParameter("log4j-properties-location");
		ServletContext sc = config.getServletContext();
		if(log4jLocation == null){
			System.out.println("** 没有log4j-properties-location 初始化文件，所以使用BasicConfiguratorc初始化 **");
			BasicConfigurator.configure();
		}else{
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File log4File = new File(log4jProp);
			if(log4File.exists()){
				System.out.println("使用："+ log4jProp + "初始化日志设置信息");
				PropertyConfigurator.configure(log4jProp);
			}else{
				System.out.println("*****" + log4jProp + "文件没有找到，所以使用BasicConfigurator初始化*****");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
		System.out.println("---------log4jServlet  初始化log4j日志设置信息完成--------");
			
	}

}




















