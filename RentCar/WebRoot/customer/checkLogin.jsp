<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	String username = request.getParameter("username");
	Customer customer = si.getCustomerByName(username);
 %>
<!doctype html>
<html>
<head>
<title>租车系统-顾客登录</title>
<%
	if(customer==null){
 %>
<script>
	alert('登录失败，请检查用户名是否正确');
	window.self.location = '../index.html';
</script>
<%	
	}else{
 %>
<script>
	window.self.location = 'customer.jsp?customerId=<%=customer.getCustomerId() %>';
</script>
<%		
	}
 %>
</head>
<body>
</body>
</html>
