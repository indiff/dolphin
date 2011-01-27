<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.tan.util.StringUtil,java.net.*" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[天空之城]</title>
<meta http-equiv="Content-Type" content="application/xhtml+xml;charset=UTF-8"/>
<meta http-equiv="Cache-control" content="max-age=3600" />
<style>
body{background-color:#040;color:#cfc;text-align:center;}
h2,h3,form{padding: 0px;margin:0px;}
h3{color: #cf0;}
a {color: white;}
</style>
<script>
	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/gi, '');
	};
	function random() {
		var time = new Date().getTime().toString();
		var c;
		for (var i = time.length - 1; i >= 0; i--) {
			c = parseInt(time.charAt(i));
			if (c != 0) {
				return c;
			}
		}
		return 9;
	}
	var value;
	var c;
	function check() {
		var word = document.getElementById('word');
		var code = document.getElementById('code');
		var enc = document.getElementById('enc');
		value = word.value;
		var len = value.trim().length;
		if (len == 0) {alert('输入的内容为空!');return;}
		else {
			var arr = [];
			c = random();
			for (var i = 0; i < len; i++) {
				arr.push(value.charCodeAt(i) ^ c);
			}
			if (arr.length > 0) {
				enc.value = arr;
				word.value = '☭跟着党走!';
			}
			code.value = c;
		}
	}
</script>
</head>
<body>
<%
	String now = StringUtil.now();
	String u = request.getParameter("u");
	String p = request.getParameter("p");
	StringBuilder b = new StringBuilder();
	if ("adgmtt".equalsIgnoreCase(u) && "76430850".equalsIgnoreCase(p)) {
		b.append(now + "<BR/>");
		b.append("<H3>" + u + "," + StringUtil.greet(now) + "!</H3><BR/>");
		String[][] selfs = {
				{"k.jsp", "K"},
				{"k.jsp?p=2", "K2"},
				{"k.jsp?p=3", "K3"},
				{"show.do?url=0", "wujie"},
				{"show.do?url=1&encoding=gb2312", "Hope"},
				{"show.do?url=2", "TianMother"},
				{"show.do?url=3", "KanChina"},
				{"show.do?url=4", "ntdtv"},
				{"show.do?url=5", "哒嘉园"},
				{"show.do?url=6", "rfa"},
				{"show.do?url=7", "菠萝"},
				{"show.do?url=8", "youmarker"},
				{"show.do?url=9", "花园"},
				{"show.do?url=10", "heartyit"},
				{"show.do?url=11", "华夏"}
		};
		//b.append("<h3>Self</h3>");
		for (byte i = 0; i < selfs.length; i++) {
			b.append("<A HREF=\"" + selfs[i][0] + "\">" + selfs[i][1] + "</a>" + ((i == selfs.length - 1) ? "" : "|"));
		}
		b.append("<BR/>");
		selfs = null;u = null;p = null;
	}
	//b.append("<h3>Site</h3>");
	String[][] links = {
		{"http://3g.baidu.com", "百度"}, 
		{"http://wap.sina.com.cn/", "新浪"}, 
		{"http://3g.163.com/", "网易"}, 
		{"http://3g.baidu.com/news", "新闻"}, 
		{"http://www.google.com.hk/m", "谷歌HK"},
		{"http://www.google.com.co/m", "谷歌CO"},
		{"http://3g.baidu.com/game", "游戏"},
		{"http://wapiknow.baidu.com/", "知道"},
		{"http://3g.baidu.com/xs", "小说"},
		{"http://wapp.baidu.com", "贴吧"},
		{"http://3g.baidu.com/img", "图片"},
		{"http://gate.baidu.com/tc", "娱乐"},
		{"http://wapbaike.baidu.com", "百科"},
		{"http://m.baidu.com/tq", "天气"},
		{"http://wap.baidu.com/pub/rebang.php", "风云榜"},
		{"http://wap.baidu.com/pub/more.php", "更多"},
		{"http://dolphinmaple.appspot.com", "主页"}
	};
	for (byte i = 0; i < links.length; i++) {
		b.append("<A HREF=\"" + links[i][0] +  "\">" + links[i][1] + "</A>" + ((i == links.length - 1) ? "" : "|"));
	}out.println(b);b = null;links = null;
%>
<form action="wap" method="post" ><input type="hidden" id="code" name="code" value="" /><input type="radio" name="site" id="s0" value="0" checked />百度<input type="hidden" id="enc" name="enc" value="" /><input type="radio" name="site" id="s1" value="1" />维基<input type="radio" name="site" value="2" id="s2" />谷歌<input type="radio" name="site" value="3" id="s3" />115<br/><input type="text" id="word" name="word" value="" /><input type="submit" value="提交" onclick="check();"/></form><%=now%>
</body>
</html>
