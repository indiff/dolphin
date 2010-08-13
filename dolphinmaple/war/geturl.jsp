<%@ page language="java" contentType= "text/html; charset=GB2312" pageEncoding="GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE> Get url Power by Tan </TITLE>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="author" content="tan">
	<meta http-equiv="keywords" content="geturl,tan,showpage,geturl,search,includemain,capture,screen,escape,bookmark">
	<meta http-equiv="description" content="geturl for the website">
  	<link rel="stylesheet" href="css/idx.css" >
  <style>
  	body {margin: 0x;padding: 10px;}
  	.main {
  		background-color: pink;
  		color: blue;
  	}	
  </style>
  <SCRIPT LANGUAGE="JavaScript">
  <!--
		  String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g, "");} 
	       function getUrl() {var t1 = document.getElementById("t1"); var s = t1.value;t1.innerText = s;		   }
  //-->
  </SCRIPT>
 </HEAD>
 <BODY>
 	<table>
 		<tr>
 			<td><textarea class="main" cols="100" rows="60" id="t1">${source}</textarea></td>
 			<td valign="top">
 				<a href="#" onclick="window.clipboardData.setData('Text', document.getElementById('t1').value);return false;">Copy</a>
				<BR><a href="#" onclick="document.getElementById('t1').value = '';">Clear</a><BR> 			
				<a href="./">Home</a><BR>
				<a href="javascript:location.reload(document.referrer)">Back</a> 			
 			</td>
 		</tr>
 	</table>
 </BODY>
</HTML>
