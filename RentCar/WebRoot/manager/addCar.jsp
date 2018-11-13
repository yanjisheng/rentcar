<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	String carType = request.getParameter("type");
	double carCost = Double.parseDouble(request.getParameter("cost"));
	String message = "添加车辆“"+carType+"”失败，请检查车辆型号是否重复，如有问题请联系管理员";
	try{
		si.addCar(carType, carCost);
		message="添加车辆“"+carType+"”成功";
	}catch (Exception e){
		e.printStackTrace();		
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>添加车辆</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>