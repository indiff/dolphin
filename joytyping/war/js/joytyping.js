function $(sid) {return document.getElementById(sid);}
window.onfocus = function() {
	$('navigator').style.top = '0px';
	$('navigator').style.left = '0px';
}
function ku(l) {
	var ss = document.getElementsByTagName('script');
	var had = false;
	for (var i=0;i<ss.length;i++) {
		if (ss[i].src.indexOf('i.js')>=0) {
			had = true;
		}
	}
	if (!had) {
			var s = document.createElement("SCRIPT");
			s.setAttribute("src", "js/i.js");
			document.body.appendChild(s);
	} else {
		document.getElementById('joytyping_0_0_1').style.display = '';
	}
}
function m() {
	var c = document.getElementById('controler');
	var d = c.style.display;
	if (c) {
		if (d == 'none') c.style.display = '';
		else c.style.display = 'none';
	}
}
	

