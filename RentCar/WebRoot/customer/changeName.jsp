<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,implement.*,interfaces.*,objects.*" %>
<%
	Customer customer = (Customer)session.getAttribute("customer");
	String name = request.getParameter("newName");
	ClientImplement ci = new ClientImplement();
	ci.init();
	ci.setCustomer(customer);
	ci.changeName(name);
	session.setAttribute("customer", ci);
 %>
<!doctype html>
<html>
<head>
<title>修改姓名</title>
<script>
	alert('修改姓名成功');
	window.self.location='customer.jsp';
</script>
</head>
</html>