<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,java.io.*,java.text.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	String customerIdString = request.getParameter("customerId");
	int customerId = -1;
	Customer customer = null;
	String customerName = null;
	if(customerIdString==null){
		Object object = session.getAttribute("customer");
		if(object==null){
			response.sendRedirect("../index.html");
		}else{
			customer = (Customer)object;
			customerId = customer.getCustomerId();
		}
	}else{
		customerId = Integer.parseInt(customerIdString);
		customer = si.getCustomerById(customerId);
	}
	customerName = customer.getCustomerName();
	session.setAttribute("customer", customer);
	ArrayList<Car> cars = si.showRentableCars();
	ClientInterface ci = new ClientImplement(customer);
	ci.init();
	ArrayList<Rent> orders = ci.showMyOrders();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 %>
<!doctype html>
<html>
<head>
<title>租车系统-顾客租车</title>
<script>
function rentDay(input){
	var days = document.getElementById("rentDays").value;
	if(isNaN(days)||days<=0){
		document.getElementById("rentDays").value='1';
		return;
	}
	if(input=='plus'){
		document.getElementById("rentDays").value=parseInt(days,10)+1;
	}else if(input=='minus'){
		if(days>1){
			document.getElementById("rentDays").value=parseInt(days,10)-1;
		}
	}
}
function checkRentCar(){
	var carRadios = document.getElementsByName("carId");
	var carChosen = false;
	for(i=0;i<carRadios.length;i++){
		if(carRadios[i].checked){
			carChosen = true;
			break;
		}
	}
	if(!carChosen){
		alert('请选择要租的车辆');
		return false;
	}
	var rentDays = document.getElementById("rentDays").value;
	if(isNaN(rentDays)||rentDays<=0){
		alert('请输入大于0的租车天数');
		return false;
	}
}
function changeAddress(){
	var input = prompt('请输入新地址');
	if(input==undefined||input==''){
		alert('地址不能为空');
	}else{
		window.self.location='changeAddress.jsp?address='+input;
	}
}
function changeName(){
	var input = prompt('请输入新的姓名');
	if(input==undefined||input==''){
		alert('姓名不能为空');
	}else if(input.length>10){
		alert('姓名不能长于10个字');
	}else{
		window.self.location='changeName.jsp?newName='+input;
	}
}
function resize(){
	var width = document.documentElement.clientWidth;
	if(width>=740){
		document.getElementById('rentcar-container').style.width='726px';
	}else if(width>=490){
		document.getElementById('rentcar-container').style.width='484px';
	}else{
		document.getElementById('rentcar-container').style.width='242px';
	}
}
</script>
<style>
h1{
	text-align:center;
	font-size:50px;
	line-height:70px;
	margin:0px;
	background-color:rgb(240,0,0);
	color:rgb(240,240,0);
}
h2{
	text-align:center;
	font-size:30px;
	line-height:40px;
	margin:0px;
	background-color:rgb(0,150,0);
	color:rgb(240,240,0);
}
#rentcar-container{
	display:block;
	float:left;
	width:726px;
	margin:10px;
}
#information-container{
	display:block;
	float:left;
	margin:10px;
	/*min-width:50px;*/
	width:300px;
}
#rent-container{
	float:left;
	clear:left;
	width:100%;
}
#rent-container table{
	width:100%;
	text-align:center;
	font-size:16px;
	border-collapse:collapse;
}
#rent-container table td{
	border-bottom:1px solid black;
	height:30px;
}
#rent-container table tr:nth-of-type(even) td{
	background-color:rgb(255,200,200);
}
#rent-container table tr:nth-of-type(odd) td{
	background-color:rgb(200,200,255);
}
#rent-container table tr:nth-of-type(1) td{
	background-color:rgb(0,90,0);
	color:white;
}
#logout{
	font-size:16px;
	line-height:25px;
	margin:10px 0px;
	text-align:center;
}
#bottom{
	clear:left;
	width:100%;
	height:150px;
	text-align:center;
	font-size:14px;
	line-height:25px;
	color:white;
	background-color:rgb(50,50,50);
}
form{
	line-height:40px;
	font-size:16px;
}
form input[type="text"]{
	width:60px;
	height:30px;
	border:1px solid black;
	font-size:16px;
	text-align:center;
}
form input[type="button"]{
	width:30px;
	height:34px;
	border:1px solid black;
	font-size:16px;
}
div.car{
	display:block;
	width:240px;
	height:201px;
	border:1px solid #cc0000;
	float:left;
	line-height:20px;
	font-size:16px;
}
div.car img{
	width:240px;
	height:180px;
}
#rentCarButton{
	display:block;
	width:200px;
	height:40px;
	background-color:rgb(15,180,0);
	color:white;
	font-size:30px;
	border:none;
}
</style>
</head>
<body onload="resize()">
<h1>欢迎您，<%=customerName %></h1>
<p id="logout"><a href="logout.jsp">退出</a></p>
<div id="rentcar-container">
<h2>租车</h2>
<form action="rentCar.jsp" onsubmit="return checkRentCar()">
	<div id="car-container">
<%
	for(int i=0;i<cars.size();i++){
		Car car = cars.get(i);
		String carImagePath = "../img/cars/"+car.getCarId()+".jpg";
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		while(path.indexOf("%20")>=0){
			path = path.replaceAll("%20", " ");
		}
		File carImage = new File(path+"../",carImagePath);
		//System.out.println("carImagePath="+carImage.getAbsolutePath());
		if(!carImage.exists()){
			carImagePath = "../img/no-photo.png";
		}
 %>
 	<div class="car">
 		<input type="radio" name="carId" value="<%=car.getCarId() %>">
 		<span><%=car.getCarDetail() %></span>
 		<span>&nbsp;&nbsp;&nbsp;<%=car.getCarCost() %>元/天</span>
 		<img src="<%=carImagePath %>"/>
 	</div>
<%
	}
 %>
	<div style="clear:both"></div>
	</div><!-- end of car-container -->
	租车天数：<input type="button" value="-" onclick="rentDay('minus')"><input type="text" id="rentDays" name="days" value="1"><input type="button" value="+" onclick="rentDay('plus')">
	<input type="submit" id="rentCarButton" value="租车"/>
</form>
</div><!-- end of rentcar-container -->
<div id="information-container">
	<h2>我的信息</h2>
	<p>姓名：<%=customerName %> <a href="javascript:changeName()">修改姓名</a></p>
	<p>地址：<%=customer.getCustomerAddress() %> <a href="javascript:changeAddress()">修改地址</a></p>
</div><!-- end of information-container -->
<div id="rent-container">
	<h2>我的订单</h2>
	<table>
		<tr><td>订单编号</td><td>车辆信息</td><td>订单日期</td><td>租车天数</td><td>订单状态</td><td>操作</td></tr>
<%
		for(int i=0;i<orders.size();i++){
			Rent rent = orders.get(i);
			byte rentStatus = rent.getRentStatus();
			String statusString = null;
			String operation = "";
			switch(rentStatus){
			case 1:
				statusString = "未支付";
				operation = "<a href='rentCar.jsp?carId="+rent.getRentCar()
						+"'>支付租金</a> <a href='cancelRent.jsp?rentId="+rent.getRentId()+"'>取消订单</a>";
				break;
			case 2:
				statusString = "未取车";
				operation = "<a href='cancelRent.jsp?rentId="+rent.getRentId()+"'>取消订单</a>";
				break;
			case 3:
				statusString = "已取车";
				break;
			case 4:
				statusString = "已还车";
				break;
			}
 %>
		<tr><td><%=rent.getRentId() %></td><td><%=si.getCarById(rent.getRentCar()).getCarDetail() %></td><td><%=format.format(rent.getRentDate()) %></td><td><%=rent.getRentDays() %></td><td><%=statusString %></td><td><%=operation %></td></tr>
<%
		}
 %>		
	</table>
</div><!-- end of rent-container -->
<div id="bottom">
	<br>中软国际培训生中级项目 燕计湦制作<br>
	Copyright&copy;2018
</div><!-- end of bottom -->
<script>
	window.onresize = resize;
</script>
</body>
</html>
