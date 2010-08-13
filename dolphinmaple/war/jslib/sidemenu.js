function $(sid) {return document.getElementById(sid);} 
function follow() {
	var sidemenu =  $('sidemenu');
	sidemenu.style.position = 'absolute';
	sidemenu.style.top = document.body.scrollTop + 200 + 'px';
}
window.onscroll = follow;
window.onload = follow;