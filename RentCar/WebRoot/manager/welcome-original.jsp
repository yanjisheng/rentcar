<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<%
	ServerInterface si = new ServerImplement();
	si.init();
	ArrayList<Car> cars = si.showCars();
	ArrayList<Customer> customers = si.showCustomers();
	String rents = si.showRent();
	StringBuffer carsTemp = new StringBuffer();
	for(int i=0;i<cars.size();i++){
		byte carStatus = cars.get(i).getCarStatus();
		String statusString = null;
		switch(carStatus){
			case 1:
				statusString = "未支付，可租车";
				break;
			case 2:
				statusString = "已支付，不可租车";
				break;
			case 3:
				statusString = "已被租走，不可租车";
				break;
			case 4:
				statusString = "闲置，可租车";
				break;
		}
		carsTemp.append("<tr><td>"+cars.get(i).getCarId()
				+"</td><td>"+cars.get(i).getCarDetail()
				+"</td><td>"+cars.get(i).getCarCost()
				+"</td><td>"+statusString+"</td><td><a href='upload.jsp?carId="
				+cars.get(i).getCarId()+"'>上传图片</a> <a href='javascript:changeCarCost("
				+cars.get(i).getCarId()+",\""+cars.get(i).getCarDetail()+"\")'>修改</a> <a href='javascript:deleteCar("
				+cars.get(i).getCarId()+",\""+cars.get(i).getCarDetail()+"\")'>删除</a></td></tr>");
	}
	StringBuffer customersTemp = new StringBuffer();
	for(int j=0;j<customers.size();j++){
		customersTemp.append("<tr><td>"+customers.get(j).getCustomerId()
				+"</td><td>"+customers.get(j).getCustomerName()
				+"</td><td>"+customers.get(j).getCustomerAddress()
				+"</td><td><a href='../customer/customer.jsp?customerId="
				+customers.get(j).getCustomerId()+"'>租车</a> <a href='deleteCustomer.jsp?customerId="
				+customers.get(j).getCustomerId()+"'>删除</a></td></tr>");
	}
 %>
<!doctype html>
<html>
<head>
<meta charset="utf-8"/>
<title>租车系统-后台管理</title>
<script>
function checkAddCar(){
	if(document.getElementById('carType').value==''){
		alert('车辆型号不能为空');
		return false;
	}
	carCost = document.getElementById('carCost').value;
	if(isNaN(carCost)||carCost<=0||carCost>=100000){
		alert('车辆租金应在0到10万之间');
		return false;
	}
}
function changeCarCost(carId,carType){
	var newRent = prompt("您要修改租金的车辆为“"+carType+"”\n请输入修改后的租金：");
	if(isNaN(newRent)||newRent<=0||newRent>=100000){
		alert('车辆租金应在0到10万之间');
		return;
	}
	window.self.location="changeCarCost.jsp?carId="+carId+"&carCost="+newRent;
	//alert("changeCarCost.jsp?carId="+carId+"&carCost="+newRent);
}
function checkAddCustomer(){
	if(document.getElementById('customerName').value==''){
		alert('顾客姓名不能为空');
		return false;
	}
	customerAddress = document.getElementById('customerAddress').value;
	if(customerAddress==''){
		alert('顾客地址不能为空');
		return false;
	}
}
function deleteCar(carId,carType){
	var result = confirm('您真的要删除车辆“'+carType+'”吗？');
	if(result){
		window.self.location="deleteCar.jsp?carId="+carId;
		//alert("deleteCar?carId="+carId);
	}
}
</script>
</head>
<body>
<h1>租车系统-后台管理</h1>
<ul>
	<li><a href="../index.html">返回主页</a></li>
	<li><a href="#carMessage">车辆信息</a></li>
	<li><a href="#customerMessage">顾客信息</a></li>
	<li><a href="#rentMessage">订单信息</a></li>
	<li><a href="welcome.jsp">进入新版后台页面</a></li>
	<li><a href="../today.jsp">看看今天是什么日子</a></li>
</ul>
<h2 id="carMessage">车辆信息</h2>
<form method="get" action="addCar.jsp" onsubmit="return checkAddCar()">
	车辆型号：<input type="text" id="carType" name="type"/>
	租金：<input type="text" id="carCost" name="cost"/>
	<input type="submit" value="添加车辆"/>
</form>
<table id="carStatus">
	<tr><td>编号</td><td>车辆型号</td><td>租金</td><td>车辆状态</td><td>操作</td></tr>
	<%=carsTemp.toString() %>
</table>
<h2 id="customerMessage">用户信息</h2>
<form method="get" action="addCustomer.jsp" onsubmit="return checkAddCustomer()">
	顾客姓名：<input type="text" id="customerName" name="name"/>
	地址：<input type="text" id="customerAddress" name="address"/>
	<input type="submit" value="添加顾客"/>
</form>
<table id="customerStatus">
	<tr><td>编号</td><td>顾客姓名</td><td>顾客地址</td><td>操作</td></tr>
	<%=customersTemp.toString() %>
</table>
<h2 id="rentMessage">订单信息</h2>
<a href="deleteAllRents.jsp">删除所有已还车订单</a>
<table id="rentStatus">
	<tr><td>订单编号</td><td>车辆信息</td><td>顾客姓名</td><td>订单日期</td><td>租车天数</td><td>租金</td><td>订单状态</td><td>操作</td></tr>
	<%=rents %>
</table>
<div id="bottom">
	<br>中软国际培训生中级项目 燕计湦制作<br>
	Copyright&copy;2018
</div><!-- end of bottom -->
</body>
</html>