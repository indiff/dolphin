		void((function(){
	var e = document.getElementById('fishstar_0_0_1');
	window.scroll(0, 0);  // scroll to top.
	if(e) 
{e.style.display= e.style.display == 'none' ? '' : 'none';}
	else {
var s = document.createElement('div');s.id = 'fishstar_0_0_1';
var blank = 'about:blank';
var ie = navigator.userAgent.toLowerCase().indexOf('msie') >= 0;
if (ie) blank = 'about:Tabs';
// join the string for the html.
Array.prototype.add = function(h,n,e) {if(n == undefined && e == undefined) this.push([h,'<br>'].join('')); else this.push(['<a style="color:#000000" href="',h,'"><b>',n,'</b></a>',e==undefined?'':'<sup>'+e+'</sup>', '<br>'].join(''))};
var arr = [];
arr.add('javascript:void(0);" onclick="var e = document.getElementById(\'fishstar_0_0_1\');if (e) e.style.display = \'none\';', '<span style="font-size:20pt;left:100%;">X</span>');
arr.add('http://www.baidu.com', '\u767e\u5ea6', '<b>\u641c\u7d22</b>');
arr.add('http://top.baidu.com/once/index2.html?c=0&s=1', 'T\u767e\u5ea6');
arr.add('http://www.igoogle.com', '\u8c37\u6b4c');
 
arr.add('http://www.ku6.com', '\u91776', '<b>\u89c6\u9891</b>');
arr.add('http://www.youku.com', '\u4f18\u9177');
arr.add('http://www.tudou.com', '\u571f\u8c46');
 
arr.add('http://dzh.mop.com', '\u732b\u6251', '<b>\u8bba\u575b</b>');
arr.add('http://www.tianya.cn', '\u5929\u6daf');
arr.add('http://www.qq.com', 'QQ');
arr.add('http://www.renren.com', '\u4eba\u4eba');
 
 
arr.add('http://www.u148.net/', '\u6709\u610f\u601d\u5427', '<b>\u5a31\u4e50</b>');
arr.add('http://minisite.qq.com/others8', '\u8ff7\u4f60\u9996\u9875');
arr.add('http://joytyping.appspot.com/', '\u5feb\u4e50\u6253\u5b57');
arr.add('http://play.typeracer.com/', 'typeracer');

arr.add('http://fishstar.googlecode.com/svn/trunk/init.js', 'self', '<b>SELF</b>');
 
//**** add links please before this place. ***//
arr.add('javascript:(void(function(){alert(\'\u753b\u9762\u6709\u94fe\u63a5\u6570\:\t\' +( document.links.length - ' + (arr.length + 1) + '));})())', '\u94fe\u63a5\u6570');
//arr.add('javascript:(void(function(){var c = document.getElementById(' + '\'fishstar_content\'' + ');var m=[]; var ls = document.links; for(var i=0;i<ls.length;i++) var h=ls[i].href; if (/.+\.html/.test(h)) m.push([h, \'\\r\\n\'].join(\'\')); c.value=m.join(\'\');})())', 'Show Links');
 
//arr.add('<textarea id="fishstar_content" cols="30" rows="20"></textarea>');
s.innerHTML = arr.join('');
// set style.
s.style.backgroundColor = 'green';
s.style.position = 'absolute';
s.style.left = document.body.scrollLeft;
s.style.top = document.body.scrollTop;
s.style.color ='#003300';
s.style.cursor = 'hand';
s.style.margin = '5px';
s.style.padding = '5px';
s.style.textAlign = 'left';
s.style.border = '1px solid green';
s.style.wordWrap = 'break-word';
s.style.width = '90px';
s.style.zIndex = '1000';
s.style.font = '12px arial';
s.style.backgroundImage = 'url(http://fishstar.googlecode.com/svn/trunk/z.jpg)';
//s.style.filter = 'gray';
if (ie){
	// s.style.filter = 'glow(color=yellow;strength=50)';
}
document.body.appendChild(s);
document.body.onscroll=function(){
	s.style.top = document.body.scrollTop+2; 
};
 }
})())