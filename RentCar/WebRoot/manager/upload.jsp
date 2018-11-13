<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,java.io.*,interfaces.*,implement.*,objects.*" %>
<% 
	ServerInterface si = new ServerImplement();
	si.init();
	request.setCharacterEncoding("utf-8");
	int carId = Integer.parseInt(request.getParameter("carId"));
	Car car = si.getCarById(carId);
	session.setAttribute("car", car);
	String carImagePath = "../img/cars/"+car.getCarId()+".jpg";
	String path = this.getClass().getClassLoader().getResource("/").getPath();
	while(path.indexOf("%20")>=0){
		path = path.replaceAll("%20", " ");
	}
	File carImage = new File(path+"../",carImagePath);
	//System.out.println("carImagePath="+carImage.getAbsolutePath());
// 	Date startTime = new Date();
// 	while(!carImage.exists()){
// 		Date endTime = new Date();
// 		if((endTime.getTime()-startTime.getTime())>=3000){
// 			carImagePath = "../img/no-photo.png";
// 			break;
// 		}		
// 	}
	if(!carImage.exists()){
		carImagePath = "../img/no-photo.png";
	}else{
		carImagePath += "?random="+Math.random();//这里添加随机数确保每次加载最新图片而不会从缓存中加载
	}
 %>
 <!doctype html>
 <html>
 <head>
 <meta charset="utf-8">
 <title>上传车辆图片</title>
 </head>
 <body>
 <h1>上传车辆图片</h1>
 <p>车辆编号：<%=carId %></p>
 <p>车辆型号：<%=car.getCarDetail() %></p>
 <p>车辆租金：<%=car.getCarCost() %></p>
 <p>车辆图片：</p>
 <img src="<%=carImagePath %>"/ style="width:240px"/>
 <form action="uploadPicture" enctype="multipart/form-data" method="post">
 	<input type="file" name="picture" accept="image/jpg"/>
 	<input type="submit" value="上传图片"/>
 </form>
 <a href="welcome.jsp">返回</a>
 <div id="bottom">
	<br>中软国际培训生中级项目 燕计湦制作<br>
	Copyright&copy;2018
 </div><!-- end of bottom -->
 </body>
 </html>