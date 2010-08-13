<%
String node = request.getParameter("node");
String id = request.getParameter("id");
// get the json objects from by the node
if (node != null) {
	if ("src".equals(node)) {
		%>[{"text":"java","id":"\/java","cls":"folder"},{"text":"test","id":"\/test","cls":"folder"},{"text":"lib","id":"\/lib","cls":"folder"},{"text":"image","id":"\/image","cls":"folder"}]<%
	}
	if ("/test".equals(node)) {
		%>[{"text":"test.js","id":"src\/test\/test.js","leaf":true,"cls":"file"}]<%
	}
	if ("/lib".equals(node)) {
		%>[{"text":"apache-all.jar","id":"src\/lib\/apache-all.jar","leaf":true,"cls":"file"},{"text":"lib.js","id":"src\/lib\/lib.js","leaf":true,"cls":"file"}]<%
	}	
	if ("/image".equals(node)) {
		%>[{"text":"hand.jpg","id":"src\/image\/hand.jpg","leaf":true,"cls":"file"},{"text":"mouse.jpg","id":"src\/image\/mouse.jpg","leaf":true,"cls":"file"}]<%
	}
	if ("/java".equals(node)) {
		%>[{"text":"Book.java","id":"src\/java\/Book.java","leaf":true,"cls":"file"},{"text":"Engine.java","id":"src\/java\/Engine.java","leaf":true,"cls":"file"}]<%
	}	
}
// return the node's id which is leaf 
if (id != null) {
	%>{"id":"<%=id%>"}<%
}
%>
