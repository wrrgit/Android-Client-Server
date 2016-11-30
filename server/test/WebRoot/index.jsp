<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  <h1>Get登录</h1>
  <form action="${pageContext.request.contextPath }/servlet/loginservlet" method="GET">
  用户名：<input type="text" name="userName"/>
  密   码：<input type="text" name="passWord"/>
  <input type="submit" value="Get登录"/>
   </form>
   <h1>Get注册</h1>
  <form action="${pageContext.request.contextPath }/servlet/registerservlet" method="GET">
  用户名：<input type="text" name="userName"/>
  密   码：<input type="text" name="passWord"/>
  <input type="submit" value="Get注册"/>
   </form>
     <h1>Post登录</h1>
  <form action="${pageContext.request.contextPath }/servlet/loginservlet" method="POST">
  用户名：<input type="text" name="userName"/>
  密   码：<input type="text" name="passWord"/>
  <input type="submit" value="Post登录"/>
   </form>
   
     <h1>Post注册</h1>
  <form action="${pageContext.request.contextPath }/servlet/registerservlet" method="POST">
  用户名：<input type="text" name="userName"/>
  密   码：<input type="text" name="passWord"/>
  <input type="submit" value="Post注册"/>
   </form>
  <%System.out.println("....jsp页面验--证服务器是否正常...."); %>
    This is my JSP page. <br>
  </body>
</html>
