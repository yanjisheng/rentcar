<%@ page language="java" pageEncoding="utf-8" %>
<%@ page import="java.util.*,interfaces.*,implement.*,objects.*" %>
<%
	session.removeAttribute("customer");
 %>
<!doctype html>
<html>
<head>
<title>退出</title>
<script>
	window.self.location='../index.html';
</script>
</head>
</html>