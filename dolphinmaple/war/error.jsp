<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
 <HEAD>
  <TITLE>ERROR</TITLE>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="author" content="tan">
	<meta http-equiv="keywords" content="get16,geturl,tan,showpage,geturl,search,includemain,capture,screen,escape,bookmark">
	<meta http-equiv="description" content="geturl for the website">
 	<link rel="stylesheet" href="css/idx.css">
  <style>
  	#error{
  		font-family: FixedSys;
  		font-size: large;
  		color: pink;
  	}
  	h1 {
  		color: pink;
  	}
  	.txt, .btn {
  		color: red;
  		background-color: red;
  	}
  	.link {
  		text-weight: bold;
  		font-size: 25;
  	}
  </style>
 <script>document.write(decodeURIComponent("%3Cstyle%3E%0A%09input.btn%20%7B%0A%09%09background-color%20%3A%20white%3B%0A%09%7D%0A%3C%2Fstyle%3E%0A%3CSCRIPT%20LANGUAGE%3D%22JavaScript%22%3E%0A%3C!--%0Afunction%20isChinese(name)%20%7B%0A%09if(name.length%20%3D%3D%200)%20return%20false%3B%0A%09for(i%20%3D%200%3B%20i%20%3C%20name.length%3B%20i%2B%2B)%20%7B%0A%09%20%20if(name.charCodeAt(i)%20%3E%20128)%20return%20true%3B%0A%09%7D%0A%09return%20false%3B%0A%7D%20%0ANumber.prototype.toHexString%20%3D%20function()%20%7Breturn%20this.toString(16)%7D%3B%0AString.prototype.toCharArray%20%3D%20function()%7Breturn%20this.split(%22%22)%3B%7D%0Afunction%20get16URL(str)%20%7B%0A%09var%20result%20%3D%20%22%22%3B%0A%09var%20ss%20%3D%20str.toCharArray()%3B%0A%09for%20(var%20i%20in%20ss)%7B%0A%09%09var%20j%20%3D%20new%20Number(ss%5Bi%5D.charCodeAt(0))%3B%0A%09%09result%20%2B%3D%20%22%25%22%20%2B%20j.toHexString()%3B%0A%09%7D%0A%09return%20result.toString()%20%3B%0A%7D%0Afunction%20changeB(o%2Cc)%20%7B%0A%09o.style.backgroundColor%20%3D%20c%3B%0A%7D%0A%2F%2F--%3E%0A%3C%2FSCRIPT%3E"));</script>
 <BODY onLoad="init();">
	 <center>
	 	<h1>ERROR</h1>
		<input class="txt" type="text" id="t1" value="Error" disabled onClick="" size="100" onMouseOver="changeB(this,'pink');" onMouseOut="changeB(this,'white');"><br>
		<input class="btn" type="button" value="get16URL" onClick="t1.value = get16URL(t1.value);" onMouseOver="changeB(this,'pink');" onMouseOut="changeB(this,'white');">
		<input class="btn" type="button" value="decodeURI" onClick="t1.value = decodeURI(t1.value);" onMouseOver="changeB(this,'pink');" onMouseOut="changeB(this,'white');">
		<input class="btn" type="button" value="unescape" onClick="t1.value = unescape(t1.value);" onMouseOver="changeB(this,'pink');" onMouseOut="changeB(this,'white');">
		<br>
		<div id="error">${error}</div>
		<a href="./"><span class="link">HOME</span></a><BR>
		<a href="javascript:history.go(-1)"><span class="link">BACK</span></a>
	 </center>
 </BODY>
 </html>