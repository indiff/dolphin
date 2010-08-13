<%@ page language="java" contentType= "text/html; charset=GB2312" pageEncoding="GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Please input your url</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="author" content="tan">
	<meta http-equiv="keywords" content="geturl,tan,showpage,geturl,search,includemain,capture,screen,escape,bookmark">
	<meta http-equiv="description" content="geturl for the website">
	<link rel="stylesheet" href="css/idx.css" >	
	<script type="text/javascript" src="jslib/idx.js"></script>
	<script type="text/javascript" src="jslib/sidemenu.js"></script>
  </head>
  <body > <a name='top'>
  <div id="sidemenu" class="top">
		<a href="./">Back</a><BR>
		<a href="#top">Top</a><BR>
		<a href="#bottom">Bottom</a><BR>
		<a href="./">Back</a><BR>
  </div>
  <% if(!"_tAn".equals(request.getAttribute("role"))){%><img src="Picture/tmp.jpg"></img><%}%>
  <div class="error">${exception}</div>
  <img src="Picture/tmp.jpg"></img>
  <a name='bottom'>
  </body>
</html>
