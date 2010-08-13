function findword(){
 var word1="";
 word1=document.forms[0].elements[0].value;
 if(document.forms[0].elements[0].value!="")
 window.open("http://www.baidu.com/s?lm=0&si=&rn=10&ie=gb2312&ct=1048576&wd="+word1+"&tn=baidu","词典"," top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");
else
window.alert("No Words!");
document.forms[0].elements[0].value="";
}
function changebody(){
	i = parseInt(Math.floor(Math.random()*9) + 1, 10);
	document.body.className = 'body' + i;
}

function over1(){
	a=Math.floor(Math.random()*256);
	b=Math.floor(Math.random()*256);
	c=Math.floor(Math.random()*256);
	a=parseInt(a,16);
	b=parseInt(b,16);
	c=parseInt(c,16);
	//document.forms[0].elements[3].style.color="#"+a+b+c;
	var str="tanyuanji";
	//将字符串放到剪切板
	//window.clipboardData.setData("text",str);
	//获得剪切板中的内容
	//alert(window.clipboardData.getData('text'));
}


function goBaidu()
{
  var word,url;
  word=document.forms[0].elements[0].value;
 if(document.forms[0].elements[0].value=="")
    alert("没有输入内容");
 if(document.forms[0].sousuo[0].checked && document.forms[0].elements[0].value!="")
  window.open("http://www.baidu.com/s?wd="+word,"搜索"," top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");
 if(document.forms[0].sousuo[1].checked && document.forms[0].elements[0].value!="")
  window.open("http://www.google.cn/search?hl=zh-CN&q="+word+"&btnG=Google+1&meta=&aq=f&oq=","搜索"," top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no");  
	document.forms[0].elements[0].value="";
return true;
  
}

function cleartext(){
	document.forms[0].elements[0].value="";
	 return true;
}
function stop(){ 
	return false; 
} 

function blinkfont(){
   document.fgColor="#0000ff";
}
function $(sid) {return document.getElementById(sid);}
window.onfocus = function() {
	$('navigator').style.top = '0px';
	$('navigator').style.left = '0px';
}
function cls(l) {
	switch (l.innerHTML) {
		case 'Open' : {
			$('links').style.display = '';
			l.innerHTML = 'Close';	
		}break;
		case 'Close' : {
			$('links').style.display = 'none';
			l.innerHTML = 'Open';	
		}break;
		default: break;
	}
}
//function initialize() {
//
//var text = document.getElementById("text").value;
// // alert();
// 	google.language.detect(text, function(result) {
//		if (!result.error && result.language) {
//			google.language.translate(text, result.language, "en",function(result) {
//				var translated = document.getElementById("translation");
//					if (result.translation) {
//					translated.innerHTML = result.translation;
//					 //translated.style.backgroundColor="#223355" ;
//					translated.style.position = "absolute" ;
//					translated.style.left = "175px" ;
//					translated.style.top ="18px"; 
//					}
//			 });
//		}
//	});
//}

Number.prototype.toHex = function () {
	var d = this;
	var q = r = null
	var s = '';
	do {
	  q = Math.floor(d / 16);
	  r = d % 16;
	  s = r < 10 ? r + s : String.fromCharCode(r + 55) + s;
	  d = q;
	}while(q)
	return s;
}
function randomColor() {
	var r = Math.floor(Math.random() * 256 + 1).toHex();
	var g = Math.floor(Math.random() * 256 + 1).toHex();
	var b = Math.floor(Math.random() * 256 + 1).toHex();
	return '#' + r + g + b;
}

