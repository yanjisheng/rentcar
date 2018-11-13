<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,implement.*,interfaces.*,objects.*" %>
<%
	Customer customer = (Customer)session.getAttribute("customer");
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	int rentId = Integer.parseInt(request.getParameter("rentId"));
	Rent rent = si.getRentById(rentId);
	byte originalRentStatus = rent.getRentStatus();
	si.changeRentStatus(rent, Rent.RETURNED);
	String message = "";
	if(originalRentStatus==Rent.PAID){
		message = "取消订单成功，您将收到退款"+rent.getRentPrice()+"元";
	}else if(originalRentStatus==Rent.NOT_PAID){
		message = "取消订单成功";
	}
 %>
<!doctype html>
<html>
<head>
<title>取消订单</title>
<script>
	alert('<%=message %>');
	window.self.location='customer.jsp';
</script>
</head>
</html>