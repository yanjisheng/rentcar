<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	String customerName = request.getParameter("name");
	String customerAddress = request.getParameter("address");
	String message = "添加顾客“"+customerName+"”失败，请检查顾客姓名是否重复，如有问题请联系管理员";
	try{
		si.addCustomer(customerName, customerAddress);
		message = "添加顾客“"+customerName+"”成功";
	}catch (Exception e){
		e.printStackTrace();
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>添加顾客</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>