<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	int carId = Integer.parseInt(request.getParameter("carId"));
	double carCost = Double.parseDouble(request.getParameter("carCost"));
	Car car = si.getCarById(carId);
	si.changeCarRent(car, carCost);
	String carDetail = car.getCarDetail();
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>修改车辆租金</title>
 <script>
 alert('修改车辆“<%=carDetail %>”租金成功');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>