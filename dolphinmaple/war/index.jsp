<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%!
	public final static int random(final int max) {
		String time = Long.toString(new java.util.Date().getTime());
		int len = time.length();
		int result;
		for (int i = len - 1; i >= 0; i--) {
			result =(int) (time.charAt(i) - 48);
			if (result <= max){
				return result;
			}
		}
		return 0;
	}

	public final static String now() {
		return new java.text.SimpleDateFormat("yyyy年MM月dd日").format(new java.util.Date());
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>[天空之城]</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="author" content="tan">
<meta http-equiv="keywords" content="geturl,tan,showpage,geturl,search,includemain,capture,screen,escape,bookmark">
<meta http-equiv="description" content="geturl for the website">
<link rel="stylesheet" href="css/idx.css">
<script type="text/javascript" src="jslib/idx.js"></script>
<script type="text/javascript">
function u(type) {
	var url=document.getElementById("url");
	var u;
	switch (type) {
		case 0: u = "%68%74%74%70%3a%2f%2f%77%77%77%2e%77%75%6a%69%65%2e%6e%65%74%2f%64%6f%77%6e%6c%6f%61%64%2f%75%2e%7a%69%70"; break;
		case 1: u = "%68%74%74%70%3a%2f%2f%77%77%77%2e%77%75%6a%69%65%2e%6e%65%74%2f%64%6f%77%6e%6c%6f%61%64%2f%75%2e%7a%69%70"; break;
		default: u = = "%68%74%74%70%3a%2f%2f%77%77%77%2e%67%70%61%73%73%31%2e%63%6f%6d%2f%64%6f%77%6e%6c%6f%61%64%2f%47%50%61%73%73%2e%7a%69%70";
	}
	if(url){url.value=decodeURIComponent(u2);}
}

function paste(){
	var txt;
	if (window.clipboardData) {
		txt = window.clipboardData.getData("text");
	}
	if (txt && txt.length > 0) {
		txt = txt.toLowerCase();
		var url = document.getElementById("url");
		if (txt.substring(0, 7) == 'http://' && url.value.length == 0) {
			url.value = txt;
		}
	}
}
</script>
</head>
<body onload="idxLoad()">
<%
	pageContext.setAttribute("random", random(8));
	String style = request.getParameter("style");
	Cookie[] cs = request.getCookies();
	if (cs != null) {
		for (Cookie c : cs) {
			if ("username".equalsIgnoreCase(c.getName())) {
%><input type="hidden" id="t_u" value="<%=c.getValue()%>">
<% 			}// End if username.
			if ("password".equalsIgnoreCase(c.getName())) {
%><input type="hidden" id="t_p" value="<%=c.getValue()%>">
<%			}// End if password.
		}// End for.
	}// end for check cookies.
	if (request.getAttribute("source") != null) {
		request.setAttribute("source", null);
	}
	if (request.getAttribute("page_source") != null) {
		request.setAttribute("page_source", null);
	}
%>
<center>
<div id="myContent" class="myContent">
<h1 id="title" onmouseover="this.style.color = '#CCFFFF'" onmouseout="this.style.color = '#FFFFCC'" title="<%=now()%>">O(∩_∩)O~</h1>
<form action="geturl.do" method="post" >
<div class="error" id="error"></div>
<table cellspacing="0" cellpadding="0">
	<tr>
		<th align="left">网址:</th>
		<td colspan="2">
			<input type="text" id="url" name="url" value="" title="请输入网址" onmouseover="paste();" size="40" ondblclick="createTip();" class="txtout">
			<input type="text" id="wiki" name="wiki" class="txtout" title="wiki">
			<span onclick="getSuffix(this)"><span id="replacement" onmouseover="this.style.backgroundColor = '#FF00FF'" onmouseout="this.style.backgroundColor = '#CCFF00'">^_^
			</span></span>
			<input type='radio' name='sel' id='selAll' onclick='selectA()'><label for='selAll'>全选</label>
			<input type='radio' name='sel' id='revAll' onclick='reverseA()'><label for='revAll'>反选</label>
		</td>
	</tr>
	<tr>
		<th align="left">协议:</th>
		<td>
			<input name="protocols" type="checkbox" id="http" value="http"><label for="http">http</label> 
			<input name="protocols" type="checkbox" id="https" value="https"><label for="https">https</label> 
			<input name="protocols" type="checkbox" id="ftp" value="ftp"><label for="ftp">ftp</label> 
			<input name="protocols" type="checkbox" id="thunder" value="thunder"><label for="thunder">thunder</label> 
			<input name="protocols" type="checkbox" id="mms" value="mms"><label for="mms">mms</label>
			<input name="protocols" type="checkbox" id="smtp" value="smtp"><label for="smtp">smtp</label>
		</td>
	</tr>
	<tr>
		<th align="left">后缀:</th>
		<td>
<%
	// join suffix the by string builder.
	String[] suffixs = { "gif", "jpg", "wmv", "rm", "mp3", "wma",
			"zip", "rar", "torrent", "avi", "asf", "rmvb", "php",
			"jsp", "asp", "aspx" };
	StringBuilder suffixBuilder = new StringBuilder();
	for (byte i = 0; i < suffixs.length; i++) {
		suffixBuilder.append("<INPUT NAME=\"suffixs\" TYPE=\"CHECKBOX\" ID=\"")
				.append(suffixs[i]).append("\" VALUE=\"")
				.append(suffixs[i]).append("\"><LABEL FOR=\"")
				.append(suffixs[i]).append("\">").append(suffixs[i])
				.append("</LABEL>");
	}
	out.println(suffixBuilder);
%>	
			</td>
	</tr>
	<tr>
		<th align="left">
			<span id="login" onclick='login(this)'>
			<span id ="replacement" onmouseover="this.style.backgroundColor = '#FF00FF'" onmouseout="this.style.backgroundColor = '#CCFF00'">登录</span>
			</span>
		</th>
		<td>
			<input type="button" class="btnout" onfocus="this.blur();" value="Get" onclick="go(this);" /> 
			<input type="button" class="btnout" onfocus="this.blur();" value="Show" onclick="show(this);" /> 
			<input type="button" class="btnout" onfocus="this.blur();" value="ShowPage" onclick="showPage(this);" />
			<input type="button" class="btnout" onfocus="this.blur();" onfocus="this.blur();" value="Download" onclick="download(this)" />
			<input type="button" class="btnout" onfocus="this.blur();" onfocus="this.blur();" value="Bookmark" onclick="myBook(this)" /> 
			<input type="button" class="btnout" onfocus="this.blur();" onfocus="this.blur();" value="View" onclick="view(this)" /> 
			<input type="button" class="btnout" onfocus="this.blur();" onfocus="this.blur();" value="Parse2IP" onclick="parse(this)" /> 
			<input type="button" class="btnout" onfocus="this.blur();" value="ShowLinks" onclick="showlinks(this)" />
			<select name="encoding">
					<option value="gb2312" selected>gb2312</option>
					<option value="utf-8">utf-8</option>
					<option value="shift-jis">shift-jis</option>
					<option value="windows-31j">windows-31j</option>
					<option value="gb18030">gb18030</option>
					<option value="iso8859-1">iso8859-1</option>
					<option value="gbk">gbk</option>
					<option value="big5">Big5</option>
					<option value="Big5-HKSCS">Big5-HKSCS</option>
			</select> 
			<select name="t" onchange="changeTarget(this)">
					<option value="0" selected></option>
					<option value="1" >_blank</option>
					<option value="2" >_parent</option>
					<option value="3" >_top</option>
			</select> 
		</td>
	</tr>
</table>
<a href="unescape.html">[Unescape]</a> 
<a href="search.jsp">[Search]</a> 
<a href="./index.jsp">[音乐版]</a> 
<a href="./w.jsp">[手机版]</a> 
<a href="./?style=nomusic">[No Music]</a> 
<a href="db.jsp">DB</a>
<a href="#" onclick="u();">U</a>
</form>
<%if (!"nomusic".equalsIgnoreCase(style)) {/*Background music control start*/%>
<div>
<%
// join the by stringbuffer
	String[][] musics = new String[][] { { "#SkyCity", "天空之城" },
			{ "#KaNong", "卡农" }, { "#YuQiao", "渔樵问答" },
			{ "#ChunJiang", "春江花月夜" }, { "#BeacuseOfYou", "因为你" },
			{ "#MoonFlow", "Moon Flow" }, { "#illusion", "错觉" },
			{ "#YoungForYou", "Young For You" } };
	StringBuilder jsArray = new StringBuilder("<script>var musics = ["); 
	for (byte i = 0; i < musics.length; i++) {
		if (i == (musics.length - 1)) {jsArray.append('\"' + musics[i][1] + '\"');}
		else {jsArray.append('\"' + musics[i][1] + "\",");}
	}
	jsArray.append("];</script>");
	StringBuilder builder = new StringBuilder(jsArray);
	for (byte i = 0; i < musics.length; i++) {
		builder.append("<A HREF=\"").append(musics[i][0])
				.append("\" ONCLICK=\"music(").append(i)
				.append(")\" TITLE=\"").append(musics[i][1])
				.append("\">").append(i).append("</A> ");
	}
	out.println(builder);
%>
</div>
<script type="text/javascript" src="jslib/music.js"></script>
<script>music(random(<%=musics.length%>));</script>
<%}/*<embed width="290" height="40" type="application/x-shockwave-flash" src="http://www.u148.net/images/audio.swf?&amp;soundFile=http://www.haofumu.tv/tjzhfmadmin/ewebeditor/uploadfile/20090428165152438.mp3&amp;playerID=43019&amp;loop=yes&amp;autostart=yes" id="player">Background music control end.*/%>
<%/*Output the Links Start.*/%>
<script type="text/javascript">
function j(site,root,title,name) {
 return ["<a href=\"javascript:void((function(){var e=document.createElement('script');e.setAttribute('src','http://",site,".googlecode.com/svn/trunk/",root,"');document.body.appendChild(e);})())\" tilte=\"",title,"\">",name,"</a> "].join('');
}
document.write([j('dolphincode','tancode/s4jdk/js/init.js','Link', 'L'), j('dolphincode','tancode/s4jdk/js/close.js','Close', 'C'), j('fishstar','init.js','Fishstar', 'F')].join(''));
</script>
<a href="http://dolphinmaple.appspot.com" title="Dolphinmaple">D</a>
<%/*Output the Links End.*/%>

<%/*亦歌*/%><div align="center"><!-- 亦歌控制栏开始 -->
<div id="controller_t"><!-- 控制器界面部分 -->
<div id="playPauseBtn_1g1g"></div>
<!-- 播放/暂停按钮 -->
<div id="nextBtn_1g1g"></div>
<!-- 下一首按钮 -->
<div id="displayText_1g1g"></div>
<!-- 歌名/歌词显示 --></div>
<script type="text/javascript" src="jslib/1g1g.js"></script></div>
</center>
</div>
<p id="cp">&nbsp;<a href='http://joytyping.appspot.com' title="Joytyping">Joytyping</a>&nbsp;<a href='http://adgmtt.appspot.com' title="Adgmtt">Adgmtt</a>&nbsp;<%
	/*<a href="javascript:var DI=document.links;var R=0; var x1=.1; var y1=.05; var x2=.25; var y2=.24; var x3=1.6; var y3=.24; var x4=300;var y4=200; var x5=300; var y5=200;  var DIL=DI.length; function A(){for(i=0;i-DIL;i++){var DIS=DI[i].style;DIS.position='absolute';DIS.left=Math.sin(R*x1+i*x2+x3)*x4+x5;DIS.top=Math.cos(R*y1+i*y2+y3)*y4+y5;}R++;} setInterval('A()',5);void(0);">Dolphin Code&nbsp;</a>*/
%><a href="readme.txt" title="[Create by Dolphin]]">About</a>&nbsp;&copy;2010</p>
</body>
</html>
