void((function(){
	var e = document.getElementById('fishstar_0_0_1');
	window.scroll(0, 0);  // scroll to top.
	if(e) 
{e.style.display= e.style.display == 'none' ? '' : 'none';}
	else {
var s = document.createElement('span');s.id = 'fishstar_0_0_1';
var blank = 'about:blank';
var ie = navigator.userAgent.toLowerCase().indexOf('msie') >= 0;
if (ie) blank = 'about:Tabs';
// join the string for the html.
Array.prototype.add = function(h,n,e) {if(n == undefined && e == undefined) this.push([h,'<br>'].join('')); else this.push(['<a href="',h,'">',n,'</a>',e==undefined?'':'<sup>'+e+'</sup>', '<br>'].join(''))};
var arr = [];
arr.add('javascript:void(0);" onclick="var e = document.getElementById(\'fishstar_0_0_1\');if (e) e.style.display = \'none\';', '<span style="font-size:20pt;left:100%;">X</span>');
arr.add('http://www.baidu.com', 'Baidu', '<b>SE</b>');
arr.add('http://top.baidu.com/once/index2.html?c=0&s=1', 'topBaidu');
arr.add('http://www.igoogle.com', 'iGoogle');

arr.add('http://www.ku6.com', 'Ku6', '<b>VIDEO</b>');
arr.add('http://www.youku.com', 'YouKu');
arr.add('http://www.tudou.com', 'TuDou');

arr.add('http://dzh.mop.com', 'Mop', '<b>FORUM</b>');
arr.add('http://www.tianya.cn', 'TianYa');
arr.add('http://www.qq.com', 'QQ');


arr.add('http://www.u148.net/', 'U148', '<b>ENT</b>');
arr.add('http://minisite.qq.com/others8', 'MiniSite');

//**** add links please before this place. ***//
arr.add('javascript:(void(function(){alert(document.links.length - ' + (arr.length + 1) + ');})())', 'links-length');
s.innerHTML = arr.join('');
// set style.
s.style.backgroundColor = '#CCCCCC';
s.style.position = 'absolute';
s.style.left = document.body.scrollLeft;
s.style.top = document.body.scrollTop;
s.style.cursor = 'hand';
s.style.margin = '5px';
s.style.padding = '5px';
s.style.textAlign = 'left';
s.style.border = '1px solid blue';
s.style.wordWrap = 'break-word';
s.style.width = '90px';
s.style.zIndex = '1000';
s.style.font = '12px arial';
document.body.appendChild(s);
document.body.onscroll=function(){
	s.style.top = document.body.scrollTop+2; 
};
 }
})())