<%@ page language="java" contentType="text/html; charset=GB2312"
	pageEncoding="GB2312"%>
<html>
	<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="author" content="tan">
	<meta http-equiv="keywords" content="geturl,tan,showpage,geturl,search,includemain,capture,screen,escape,bookmark">
	<meta http-equiv="description" content="geturl for the website">
	<link rel="stylesheet" href="css/idx.css">
	<link rel="stylesheet" href="css/tan.css">
	<link type="text/css" rel="stylesheet" href="css/win.css" />
	<script type="text/javascript" src="jslib/jsapi"></script>
	<script type="text/javascript" src="jslib/jquery.js"></script>
	<script type="text/javascript" src="jslib/jquerywin.js"></script>
	<script type="text/javascript" src="jslib/myJs.js"></script>
<script type="text/javascript">
var oPopup = window.createPopup();
google.load("language", "1");

function initialize(button) {
	// 获取文本框内容
	var text = document.getElementById("kw").value;
	if (text.length == 0){
	  alert("你没有输入内容");
	  document.forms[0].elements[0].focus();
	  return ;
	}
	// 获取翻译的类型的按钮，并且设定翻译的类型
	var languageType = "en" ;
	 if (button.id =="enButton" ){
	     languageType = "en" ;
	 }
	 if (button.id == "jaButton" ) {
	     languageType = "ja" ;
	 }
	google.language.detect(text, function(result) {
		if (!result.error && result.language) {
		google.language.translate(text, result.language, languageType,
		function(result) {
		var translated = document.getElementById("content");
		if (result.translation) {
		//translated.innerHTML = result.translation;        // 以窗口的形式显示
		showPopup(result.translation);                      // 以Popup窗口形式显示
		 //translated.style.backgroundColor="#223355" ;
		translated.style.position = "absolute" ;
		translated.style.left = "175px" ;
		translated.style.top ="18px"; 
		}
	});}
	});
}


//google.setOnLoadCallback(initialize);
function showPopup(msg){
   var oPopupBody = oPopup.document.body;
   oPopupBody.style.backgroundColor = "black" ;
   oPopupBody.style.border = "solid white 1px" ;
   oPopupBody.innerHTML =  "<font color=white>" + msg + "</font>" ;
   oPopup.show(327,304,350,20,document.body);
}
    </script>
	</head>
	<body bgcolor="black" onload="document.forms[0].elements[0].focus();"
		class='body4' name="thebody" oncontextmenu="return false;">
		<div id="navigator" name="navigator" class='navigator'>
			<div id='links' style='display:none'>
				<a href='javascript:history.go(-1)'>Back</a><BR>
				<a href='./'>Home</a><BR>
				<a href='http://www.baid.com'>Baidu</a><BR>
				<a href='http://www.google.cn'>Google</a><BR>
				<a href='http://www.%71%71.com'>QQ</a><BR>
				<a href='http://www.csdn.net'>CSDN</a><BR>
			</div>
			<a id='opener' href='#open' onclick='cls(this)'>Open</a><BR>
		</div>
		<center>
			<div id="layer1" onclick="setInterval('change()', 500)">
				O(∩_∩)O
			</div>
		</center>
<!--
		<a class=tantextoff onmouseover="this.className='tantexton'"
			onclick="blinkfont();" onmouseout="this.className='tantextoff'">someone</a>
		<script type="text/javascript" src="jslib/translate.js" />
		<div id="layer1" onclick="setInterval('change()',1000)"
			style="Position: absolute; filter: progid :DXImageTransform.Microsoft.gradient(startColorStr=#FF0000FF,endColorStr=#00000000);">
			O(∩_∩)O
		</div>
-->
		<script language="javascript">
		 	var bVisible = true ;
			var block ;
			var ns = (document.layers)?true:false;
			var ie = (document.all)?true:false;
			if (ns) 
			  block =document.layer1; 
		    else if(ie)
			  block = layer1.style ;
		    function showObject(obj){ 
			    if(ns) obj.visibility = "show" ;
				else if(ie) obj.visibility = "visible" ;
				obj.color = randomColor();
			}
			function hideObject(obj){
			    if(ns) obj.visibility = "hide" ;
				else if(ie) obj.visibility = "hidden" ;
			}
			function change(){
			    if(bVisible)
				   showObject(block);
		        else
				   hideObject(block);
		        bVisible = !bVisible ;
			} 
	  </script>
		<marquee direction=right behavor=sroll>搜索一下</marquee>
		<div id="controller">
		<center>
			<!--  <span id="attentionText" style="position:absolute;left:530;top:330;color:#888888" >请输入内容O(∩_∩)O</span>-->
			<form name=f action="http://www.google.cn/search?hl=zh-CN&q=">
				<table>
					<tr>
						<td>
							<input type=text name=q id=kw size=42 maxlength=100
								onmouseover="" onmouseout="">
							<br>
						</td>
					</tr>

					<tr>
						<td align=center>
							<input id='b_i' type=radio value=Baidu name=sousuo checked>
							 <label for='b_i' class=tantextoff onmouseover="this.className='tantexton'"
								onmouseout="this.className='tantextoff'">Baidu</label>
							 <input id='g_i' type=radio value=Google name=sousuo> 
							 <label for='g_i' class=tantextoff onmouseover="this.className='tantexton'"
								onmouseout="this.className='tantextoff'">Google</label>
						</td>
					</tr>
					<tr>
						<td align=center bgcolor>
							<input type=button value=搜索 class=btnoff
								onmouseover="this.className='btnon'"
								onmouseout="this.className='btnoff'" onclick="goBaidu();">
							<input type=button value=词典 class=btnoff
								onmouseover="this.className='btnon'"
								onmouseout="this.className='btnoff'" onclick="findword();">
							<input type=reset value=随机 class=btnoff
								onmouseover="this.className='btnon'"
								onmouseout="this.className='btnoff'" onclick="changebody();">

							<!-- input type="button" value="翻译" class=btnoff onclick="showwin();initialize();"/ -->
							<input type="button" value="英语翻译" id="enButton" class=btnoff
								onclick="initialize(this);" />
							<input type="button" value="日语翻译" id="jaButton" class=btnoff
								onclick="initialize(this);" />
							<div id="translation" style="color: #00ff00;"></div>
							<div id="win">
								<div id="title">
									<span id="close" onclick="hide()">X</span>
								</div>
								<div id="content" style="top: 1px; width: 0px; height: 0px; padding-top: 0px; padding-left: 0px; left: 1px;"></div>

							</div>
						</td>
					</tr>
				</table>
			</form>
		</center>
	</div>	
