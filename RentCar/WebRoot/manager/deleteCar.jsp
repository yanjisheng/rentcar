<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	int carId = Integer.parseInt(request.getParameter("carId"));
	String carDetail = si.getCarById(carId).getCarDetail();
	String message = "删除车辆“"+carDetail+"”失败，请检查车辆是否已被租出，如有问题请联系管理员";
	try{
		si.removeCar(carId);
		message = "删除车辆“"+carDetail+"”成功";
	}catch(Exception e){
		e.printStackTrace();
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>删除车辆</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>