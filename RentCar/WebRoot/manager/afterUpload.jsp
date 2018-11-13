<%@ page language="java" pageEncoding="utf-8" %>
<%
	int carId = Integer.parseInt(request.getParameter("carId"));
	String success = request.getParameter("success");
	String message = "上传失败，请检查上传图片是否为jpg格式";
	if(success.equals("success")){
		message = "上传成功";
	}
 %>
<!doctype html>
<html>
<head>
<title>上传车辆图片</title>
<script>
	alert('<%=message %>');
	window.self.location='upload.jsp?carId=<%=carId %>';
</script>
</head>
</html>