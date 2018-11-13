<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,java.io.*,interfaces.*,implement.*,objects.*" %>
<%
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	Car car = si.getCarById(Integer.parseInt(request.getParameter("carId")));
	Customer customer = (Customer)session.getAttribute("customer");
	int rentDays = 0;
	Rent rent = null;
	try{
		rentDays = Integer.parseInt(request.getParameter("days"));
		rent = si.addRent(car, customer, rentDays, Rent.NOT_PAID);
	}catch(NumberFormatException e){
		rent = car.getRent();
		rentDays = rent.getRentDays();
	}
 %>
<!doctype html>
<html>
<head>
<title>租车系统-支付</title>
<style>
h1{
	text-align:center;
	font-size:50px;
	margin:0px;
	background-color:rgb(240,0,0);
	color:rgb(240,240,0);
}
#payRent{
	display:block;
	float:left;
	height:40px;
	width:120px;
	background-color:rgb(60,120,0);
	color:white;
	font-size:25px;
	line-height:40px;
	text-align:center;
	text-decoration:none;
	margin:0px 10px;
}
#cancelRent{
	display:block;
	float:left;
	height:40px;
	width:60px;
	background-color:rgb(30,30,30);
	color:white;
	font-size:25px;
	line-height:40px;
	text-align:center;
	text-decoration:none;
	margin:0px 10px;
}
#carPicture{
	display:block;
	float:left;
	width:240px;
	height:180px;
	margin-right:10px;
}
p{
	margin:10px 5px;
	font-size:16px;
}
#qrcode{
	display:none;
	position:fixed;
	top:80px;
	left:200px;
	z-index:9;
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
	margin-top:150px;
}
</style>
<script>
function showQrcode(){
	document.getElementById('qrcode').style.display='block';
}
function hideQrcode(){
	document.getElementById('qrcode').style.display='none';
}
</script>
</head>
<body>
<h1>欢迎您，<%=customer.getCustomerName() %></h1>
<p><%=customer.getCustomerName() %>先生/女士，您的租赁订单信息如下：</p>
<%
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
<img id="carPicture" src="<%=carImagePath %>">
<p>车辆型号：<%=car.getCarDetail() %></p>
<p>租赁天数：<%=rentDays %>天</p>
<p>总租金：<%=rent.getRentPrice() %></p>
<p>您可以现在支付租金，也可以选择到店后支付租金。</p>
<p>如果您选择到店后支付租金，您预订的车辆可能会被其他顾客租走。</p>
<p>为了避免这种情况发生，建议您现在支付租金。</p>
<br/>
<a id="payRent" href="payRent.jsp?rentId=<%=rent.getRentId() %>" onmouseover="showQrcode()" onmouseout="hideQrcode()">支付租金</a>
<a id="cancelRent" href="customer.jsp">返回</a>
<div id="qrcode"><img src="../img/pay-zhifubao.jpg"><img src="../img/pay-weixin.jpg"></div>
<div id="bottom">
	<br>中软国际培训生中级项目 燕计湦制作<br>
	Copyright&copy;2018
</div><!-- end of bottom -->
</body>
</html>