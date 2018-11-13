<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,implement.*,interfaces.*,objects.*" %>
<%
	Customer customer = (Customer)session.getAttribute("customer");
	String address = request.getParameter("address");
	ClientImplement ci = new ClientImplement();
	ci.init();
	ci.setCustomer(customer);
	ci.changeAddress(address);
	session.setAttribute("customer", ci);
 %>
<!doctype html>
<html>
<head>
<title>修改地址</title>
<script>
	alert('修改地址成功');
	window.self.location='customer.jsp';
</script>
</head>
</html>