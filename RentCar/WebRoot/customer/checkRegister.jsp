<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ClientInterface ci = new ClientImplement();
	ci.init();
	request.setCharacterEncoding("utf-8");
	String username = request.getParameter("username");
	String address = request.getParameter("address");
	Customer customer = null;
	try{
		customer = ci.register(username,address);
	}catch (Exception e){
		e.printStackTrace();
	}
 %>
<!doctype html>
<html>
<head>
<title>租车系统-新用户注册</title>
<%
	if(customer==null){
 %>
<script>
	alert('注册失败，请检查以下要求，如有问题请与管理员联系：\n您的用户名不能超过10个字；\n您的用户名不能与他人的名字重复；\n您的地址不能超过40个字');
	window.self.location = '../index.html';
</script>
<%	
	}else{
 %>
<script>
	alert('注册成功');
	window.self.location = 'customer.jsp?customerId=<%=customer.getCustomerId() %>';
</script>
<%		
	}
 %>
</head>
<body>
</body>
</html>
