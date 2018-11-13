<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,java.io.*,interfaces.*,implement.*,objects.*" %>
<%
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	Rent rent = si.getRentById(Integer.parseInt(request.getParameter("rentId")));
	si.changeRentStatus(rent, Rent.PAID);
	String message = "支付成功，您刚才支付了"+rent.getRentPrice()+"元";
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>支付租金</title>
 <script>
 alert('<%=message %>');
 window.self.location='customer.jsp';
 </script>
 </head>
 </html>