<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[天空之城]</title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/>
</head>
<body>
<%
	String[][] links = {
		{"http://3g.baidu.com", "baidu"}, 
		{"http://3g.baidu.com/news", "news"}, 
		{"http://m.google.com/", "google"}};
	StringBuilder linksBuilder = new StringBuilder();
	for (byte i = 0; i < links.length; i++) {
		linksBuilder.append("<A HREF=\"" + links[i][0] +  "\">" + links[i][1] + "</A>|");
	}
	out.println(linksBuilder.substring(0, linksBuilder.length() - 1));
	linksBuilder = null;
%>
<form action="wap" method="post"><input type="radio" name="site" id="s0" value="0" checked /><label for="s0">baidu</label><input type="radio" name="site" id="s1" value="1" /><label for="s1">wiki</label><input type="radio" name="site" value="2" id="s2" /><label for="s2">google</label><input type="text" name="word" value=""/><input type="submit" value="submit" /></form>
</body>
</html>
