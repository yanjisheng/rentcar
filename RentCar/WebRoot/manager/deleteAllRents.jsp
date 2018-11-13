<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	boolean result = si.removeRent();
	String message = "历史订单为空";
	if(result){
		message = "删除订单成功";
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>删除所有订单</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>