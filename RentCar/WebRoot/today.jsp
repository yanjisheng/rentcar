<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" --%>
<%
	Date date = new Date();
	int year = date.getYear()+1900;
	int month = date.getMonth()+1;
	int date1 = date.getDate();
	pageContext.setAttribute("year",year);
	pageContext.setAttribute("month",month);
	pageContext.setAttribute("date",date1);
 %>

<!DOCTYPE HTML>
<html>
<head>
<title>现在时间</title>
</head>
<body>
现在是${year}年${month}月${date}日<br>
当前时间：<span id="hour"></span>:<span id="minute"></span>:<span id="second"></span><br>
<%--
<c:if test="${month==3&&date==26}">今天是地球一小时纪念日</c:if>
<c:if test="${month==3&&date==27}">今天是世界戏剧日</c:if>
<c:if test="${month==3&&date==23}">今天是世界气象日</c:if>
<c:if test="${month==3&&date==28}">今天是西藏百万农奴解放纪念日</c:if>
--%>
<br><a href="index.html">返回主页</a>
<script>
setInterval(function(){
	var date = new Date();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
 	if(minute<10){
 		minute = '0'+minute;
 	}
 	if(second<10){
 		second = '0'+second;
 	}
	document.getElementById('hour').innerHTML=hour;
	document.getElementById('minute').innerHTML=minute;
	document.getElementById('second').innerHTML=second;
},1000);
</script>
</body>
</html>
