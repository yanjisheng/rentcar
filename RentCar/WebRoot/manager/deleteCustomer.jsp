<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	int customerId = Integer.parseInt(request.getParameter("customerId"));
	Customer customer = si.getCustomerById(customerId);
	String customerName = customer.getCustomerName();
	String message = "删除顾客“"+customerName+"”失败，请检查该顾客是否有尚未归还的车辆，如有问题请联系管理员";
	try{
		si.removeCustomer(customer);
		message = "删除顾客“"+customerName+"”成功";
	}catch(Exception e){
		e.printStackTrace();
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>删除顾客</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>