package com.wqx.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wqx.test.config.ConfigLoader;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginServlet.class);
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("####LoginServlet doGet####");
		Boolean flag = false;
		String loginInfo = null;
		//获取http请求response的输入流，用于向客户端返回数据
		PrintWriter out = response.getWriter();
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		logger.debug("用户登录："+"  userName："+userName+"  passWord:"+passWord);
		System.out.println("用户登录："+"  userName："+userName+"+passWord:"+passWord+"+");
		flag = connectDataBase(userName,passWord);
		
		if(flag){
			loginInfo = "login_success";
			logger.debug(userName+":登录成功");
			System.out.println(userName+":登录成功");
		}else{
			logger.debug(userName+":登录失败");
			System.out.println(userName+":登录失败");
			loginInfo = "login_fail";//"login_fail"
		}
		out.write(loginInfo);
		out.flush();
		out.close();	
	}
	public boolean connectDataBase(String userName,String passWord){
		Connection con = null;
		Statement statement = null;
		String query = null;
		ResultSet result = null;
		boolean res = false;
		try {
			Class.forName(ConfigLoader.getPropertyByName("db_driver"));
			String url = ConfigLoader.getPropertyByName("url");
			String db_uname = ConfigLoader.getPropertyByName("db_uname");
			String db_pword = ConfigLoader.getPropertyByName("db_pword");
			con = (Connection) DriverManager.getConnection(url,db_uname,db_pword);
			
			statement = (Statement) con.createStatement();
			query = "SELECT * FROM user where userName ='"+userName+"'and passWord ='"+passWord+"'";
			result = statement.executeQuery(query);
			if(result.next()){
                res =  true;
			}else{
				System.out.println("没有此用户");
				res = false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		try {
			con.close();
			statement.close();
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.debug("####LoginServlet doGet####");
		this.doGet(request, response);
	}

}
