var cache = ''; 
function $(sid) {return document.getElementById(sid);} 
String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g, ''); }  
function getUrl(button) { if(!isUrl($('url'))) {alert('Please intput url'); } else {var f = document.forms[0]; alert(f.action); f.action = f.action + "?suffix=" + button.name; f.submit(); } } 
function go(button) { if(!isUrl($('url'))) { alert('Please intput url'); } else { button.value = "Processing..."; var f = document.forms[0]; f.action = "geturl.do"; f.submit(); } } 
function showPage(button) { if(!isUrl($('url'))) { alert('Please intput url'); } else { button.value = "Showing..."; var f = document.forms[0]; f.action = "showpage.do"; f.submit(); } } 
function download(button) { if(!isUrl($('url'))) { alert('Please intput url'); } else { button.value = "Downloading..."; var f = document.forms[0]; f.action = "tan.download"; f.submit();	 } } 
function getSuffix(rel) { if (cache != '') { return false; } else { cache = rel.innerHTML ; rel.innerHTML = "<span onclick='getSuffix(this)' ><input type='text' name='downloadSuffix' size='10' ondblclick='location.reload()'/></span>"; } } 
function myBook(b) { if($('login') != null) $('login').click(); var u = $('username'); var p = $('password'); if (u == undefined || p == undefined || u.value.trim() === '' || p.value.trim() === '') { alert('Please input the username and password!'); return ; } else { b.value = 'bookmark...'; document.forms[0].action = "tan.book"; document.forms[0].submit(); } }
function login(t) { var t_u = ''; var t_p = ''; if ($('t_p')) t_p = $('t_p').value;  if ($('t_u')) t_u = $('t_u').value; if (t.id === '')  return false; t.innerHTML = "<label for=\"username\">u</label><input type=\"text\" id=\"username\" name=\"username\" size=\"10\" value=\"" + t_u + "\"><label for=\"password\">p</label><input type=\"password\" id=\"password\" name=\"password\" size=\"10\" value=\"" + t_p + "\">"; t.id = ''; }
function isUrl(v) {return /^\w+[\:\/\\\w\$\#\&\;]+.*$/.test(v.value);}
window.onload = function() {if($('error').innerHTML != '')  $('error').style.display = 'block'; window.setTimeout("$('error').style.display = 'none'", 1000);}
function view(b) { 	if($('login') != null) $('login').click();	var u = $('username'); var p = $('password'); 	if (u == undefined || p == undefined || u.value.trim() === '' 		|| p.value.trim() === '') { alert('Please input the username and password!'); return ; } 		else	 { b.value = 'view...'; document.forms[0].action = "tan.view"; document.forms[0].submit(); }}function selectA() {	var ps = document.getElementsByName("protocols");	var ss = document.getElementsByName('suffixs');	for (var i = 0; i < ps.length; i++) ps[i].checked = true;	for (var i = 0; i < ss.length; i++) ss[i].checked = true;}function reverseA() {	var ps = document.getElementsByName('protocols');	var ss = document.getElementsByName('suffixs');	for (var i = 0; i < ps.length; i++) ps[i].checked = !ps[i].checked;	for (var i = 0; i < ss.length; i++) ss[i].checked = !ss[i].checked;}
var ie = (navigator.userAgent.toLowerCase().indexOf('msie') > 0);
var firefox = (navigator.userAgent.toLowerCase().indexOf('firefox') > 0);
function parse(button) { if (!isUrl($("url"))) {alert("Please intput url"); } else {var f = document.forms[0]; 	f.action = "parse.tan"; 	f.submit(); } }   function showlinks(button){ if(!isUrl($('url'))) { alert('Please intput url'); } else {  button.value = "Showing...";   var f = document.forms[0];   f.action = "showlinks.do";    f.submit();  }  } function pushSite(l) { 	$('url').value = l.innerHTML; 	var tips = $('tips'); 	var content = $('myContent'); 	tips.innerHTML = ''; 	tips.style.display = 'none'; 	if (ie) content.style.filter = 'alpha(opacity=100)'; 	if (firefox) content.style.opacity = '';	 } function createTip() { var tips = $('tips'); tips.style.display = ''; var content = $('myContent'); if (ie) content.style.filter = 'alpha(opacity=50)'; if (firefox) content.style.opacity = '0.5'; var link1 = "<a href='#baidu' onclick='pushSite(this);'>http://www.baidu.com</a><BR>"; var link2 = "<a href='#google' onclick='pushSite(this);'>http://www.google.com</a><BR>"; var link3 = "<a href='#qq' onclick='pushSite(this);'>http://www.qq.com</a><BR>"; var link4 = "<a href='#minisite' onclick='pushSite(this);'>http://minisite.qq.com/others8</a><BR><a href='#close' onclick='pushSite(this);'>Close</a><BR>"; var websites = new Array(link1, link2, link3, link4); tips.innerHTML = websites.join(''); }
function show(button) {
	if (!isUrl($("url"))) {
		alert("Please intput url");
	} else {
		button.value = "Showing...";
		var f = document.forms[0];
		f.action = "show.do";
		f.submit();
	}
}


