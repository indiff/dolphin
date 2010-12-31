<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>[天空之城]</title>
<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8"/>
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
	String[][] links = {
		{"http://3g.baidu.com", "百度"}, 
		{"http://wap.sina.com.cn/", "新浪"}, 
		{"http://3g.163.com/", "网易"}, 
		{"http://3g.baidu.com/news", "新闻"}, 
		{"http://m.google.com/", "谷歌"},
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
		{"http://dolphinmaple.appspot.com", "主页"}};
	StringBuilder linksBuilder = new StringBuilder();
	for (byte i = 0; i < links.length; i++) {
		if (i != 0 && i % 5 == 0) {
			linksBuilder.append("<A HREF=\"" + links[i][0] +  "\">" + links[i][1] + "</A><BR/>");
		} else {
			linksBuilder.append("<A HREF=\"" + links[i][0] +  "\">" + links[i][1] + "</A>|");
		}
	}
	out.println(linksBuilder.substring(0, linksBuilder.length() - 1));
	linksBuilder = null;
	links = null;
%>
<form action="wap" method="post" ><input type="hidden" id="code" name="code" value="" /><input type="radio" name="site" id="s0" value="0" checked />百度<input type="hidden" id="enc" name="enc" value="" /><input type="radio" name="site" id="s1" value="1" />维基<input type="radio" name="site" value="2" id="s2" />谷歌<br/><input type="text" id="word" name="word" value="" /><input type="submit" value="提交" onclick="check();"/></form>
</body>
</html>
