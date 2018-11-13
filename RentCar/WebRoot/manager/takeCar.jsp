<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	Integer rentId = Integer.parseInt(request.getParameter("rentId"));
	Byte rentStatus = Byte.parseByte(request.getParameter("rentStatus"));
	Rent rent = si.getRentById(rentId);
	byte originalRentStatus = rent.getRentStatus();
	si.changeRentStatus(rent, rentStatus);
	String message = "aaa";
	if(rentStatus==3){
		if(originalRentStatus==1){
			message = "取车成功，请收取顾客租金"+rent.getRentPrice()+"元";
		}else if(originalRentStatus==2){
			message = "取车成功";	
		}
	}else if(rentStatus==4){
		if(originalRentStatus==3){
			message = "还车成功";	
		}else if(originalRentStatus==2){
			message = "取消订单成功，已退还顾客租金"+rent.getRentPrice()+"元";
		}else if(originalRentStatus==1){
			message = "取消订单成功";
		}
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>取车和还车</title>
 <script>
 alert('<%=message %>');
 window.self.location='welcome.jsp';
 </script>
 </head>
 </html>