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

public class RegisterServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(RegisterServlet.class);
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

		logger.debug("####RegisterServlet doGet####");
		
		//获取http请求response的输入流，用于向客户端返回数据
		PrintWriter out = response.getWriter();
		int result = -1;//初始化注册的状态码
		String userName = request.getParameter("userName");//从http请求request中，获取用户端传来的值
		String passWord = request.getParameter("passWord");
		logger.debug("用户注册："+"  userName："+userName+"  passWord:"+passWord);
		System.out.println("用户注册："+"  userName："+userName+"  passWord:"+passWord);
		result = connectDataBase(userName,passWord);
		String registerInfo = null;
		if(result > 0){
			registerInfo = "register_success";//用来标示注册是否成功，此标示将传回客户端
			logger.debug(userName+":注册成功");
			System.out.println(userName+":注册成功");
		}else if(result == -1){
			registerInfo = "userName_exist";
			logger.debug(userName+":已存在");
			System.out.println(userName+":已存在，请直接登录");
		}else{
			registerInfo = "register_fail";
			logger.debug(userName+":注册失败");
			System.out.println(userName+":注册失败");
		}
		out.write(registerInfo);
		out.flush();
		out.close();
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

		logger.debug("####RegisterServlet doPost####");
		this.doGet(request, response);
	}
	public int connectDataBase(String userName,String passWord){
		Connection con = null;
		Statement statement = null;
		String query = null;
		ResultSet result = null;
		String insert = null;
		int rows = -1;
		try {
			Class.forName(ConfigLoader.getPropertyByName("db_driver"));
			String url = ConfigLoader.getPropertyByName("url");
			String db_uname = ConfigLoader.getPropertyByName("db_uname");
			String db_pword = ConfigLoader.getPropertyByName("db_pword");
			con = (Connection) DriverManager.getConnection(url,db_uname,db_pword);
			
			statement = (Statement) con.createStatement();
			
			query = "SELECT * FROM user where userName ='"+userName+"'and passWord ='"+passWord+"'";
			insert = "INSERT INTO user(userName,passWord) values ('"+userName+"','"+passWord+"')";
			result = statement.executeQuery(query);
			if(result.next()){
                return -1;
			}else{
				rows = statement.executeUpdate(insert);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
		try {
			con.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
}
