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
				+cars.get(i).getCarId()+"' class='btn btn-xs btn-success'>上传图片</a> <a href='javascript:changeCarCost("
				+cars.get(i).getCarId()+",\""+cars.get(i).getCarDetail()+"\")' class='btn btn-xs btn-warning'>修改</a> <a href='javascript:deleteCar("
				+cars.get(i).getCarId()+",\""+cars.get(i).getCarDetail()+"\")' class='btn btn-xs btn-danger'>删除</a></td></tr>");
	}
	StringBuffer customersTemp = new StringBuffer();
	for(int j=0;j<customers.size();j++){
		customersTemp.append("<tr><td>"+customers.get(j).getCustomerId()
				+"</td><td>"+customers.get(j).getCustomerName()
				+"</td><td>"+customers.get(j).getCustomerAddress()
				+"</td><td><a href='../customer/customer.jsp?customerId="
				+customers.get(j).getCustomerId()+"' class='btn btn-xs btn-success'>租车</a> <a href='deleteCustomer.jsp?customerId="
				+customers.get(j).getCustomerId()+"' class='btn btn-xs btn-danger'>删除</a></td></tr>");
	}
 %>
<!doctype html>
<html lang="zh-cn">
<head>
<meta charset="utf-8"/>
<meta name="viewpoint" content="width=device-width,initial-scale=1">
<title>租车系统-后台管理</title>
<link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
<style>
h2{
	padding-top:60px;
}
</style>
<script src="../bootstrap/js/jquery.min.js"></script>
<script src="../bootstrap/js/bootstrap.min.js"></script>
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
<nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
<div class="container">
<div class="navbar-header"><div class="navbar-brand" style="color:white">租车系统-后台管理</div></div>
<div class="collapse navbar-collapse">
<ul class="nav navbar-nav">
	<li><a href="../index.html">返回主页</a></li>
	<li><a href="#carMessage">车辆信息</a></li>
	<li><a href="#customerMessage">顾客信息</a></li>
	<li><a href="#rentMessage">订单信息</a></li>
	<li><a href="welcome-original.jsp">返回旧版后台页面</a></li>
</ul>
</div>
</div>
</nav>
<div class="container">
<h2 id="carMessage">车辆信息</h2>
<form method="get" action="addCar.jsp" onsubmit="return checkAddCar()" class="form-inline">
	<div class="form-group"><label>车辆型号：</label><input type="text" id="carType" name="type" class="form-control"/></div>
	<div class="form-group"><label>租金：</label><input type="text" id="carCost" name="cost" class="form-control"/></div>
	<input type="submit" value="添加车辆" class="btn btn-small btn-success"/>
</form>
<table id="carStatus" class="table table-striped">
	<tr><td>编号</td><td>车辆型号</td><td>租金</td><td>车辆状态</td><td>操作</td></tr>
	<%=carsTemp.toString() %>
</table>
<h2 id="customerMessage">用户信息</h2>
<form method="get" action="addCustomer.jsp" onsubmit="return checkAddCustomer()" class="form-inline">
	<div class="form-group"><label>顾客姓名：</label><input type="text" id="customerName" name="name" class="form-control"/></div>
	<div class="form-group"><label>地址：</label><input type="text" id="customerAddress" name="address" class="form-control"/></div>
	<input type="submit" value="添加顾客" class="btn btn-small btn-success"/>
</form>
<table id="customerStatus" class="table table-striped">
	<tr><td>编号</td><td>顾客姓名</td><td>顾客地址</td><td>操作</td></tr>
	<%=customersTemp.toString() %>
</table>
<h2 id="rentMessage">订单信息</h2>
<a href="deleteAllRents.jsp">删除所有已还车订单</a>
<table id="rentStatus" class="table table-striped">
	<tr><td>订单编号</td><td>车辆信息</td><td>顾客姓名</td><td>订单日期</td><td>租车天数</td><td>租金</td><td>订单状态</td><td>操作</td></tr>
	<%=rents %>
</table>
<div style="height:60px"></div>
</div><!-- end of container -->
<nav class="navbar navbar-default navbar-fixed-bottom navbar-inverse">
<div class="container text-center" style="color:white">
	中软国际培训生中级项目 燕计湦制作<br>
	Copyright&copy;2018<br>
</div>
</nav>
</body>
</html>