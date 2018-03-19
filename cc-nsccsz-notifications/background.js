//  Copyright (c) 2014 NSCCSZ. All rights reserved.
//  by qwop 2014 07 31


/*
  Displays a notification with the current time. Requires "notifications"
  permission in the manifest file (or calling
  "webkitNotifications.requestPermission" beforehand).
*/


chrome.extension.onMessage.addListener(function(request, sender, sendResponse) {
	if(request.cmd=='getUser'){
		sendResponse( { 'username':localStorage.cc_username, 'password':localStorage.cc_password } );
	}else if(request.cmd=='notify'){
		notify(request.msg);
		sendResponse('ok');
	}
})


function notify(msg){
	  var notification = window.webkitNotifications.createNotification(
		'48.png',                      // The image.
		'fuck', // hour + time[2] + ' ' + period, // The title.
		msg     // The body.
	  );
	  notification.show();
}


// Conditionally initialize the options.
console.log( 'qwop cc: Conditionally initialize the options.' );
if (!localStorage.isInitialized) {
  localStorage.isActivated = true;   // The display activation.
  localStorage.frequency = 1;        // The display frequency, in minutes.
  localStorage.isInitialized = true; // The option initialization.
}


var loginTitle = '超算中心服务台系统';
var loginedTitle = '服务台业务处理系统';
var logining = false;
var logined = false;

chrome.windows.getCurrent( function(currentWindow) { 
	chrome.tabs.query(
		{ windowId: currentWindow.id }, 
		function(activeTabs){ 
			for( var i=0; i<activeTabs.length; i++ ){
				console.log( activeTabs[i].id );
				if( activeTabs[i].title == loginTitle ){
					logining = true;
				}
				else if( activeTabs[i].title == loginedTitle ){
					logined = true;
				}
			}
			//chrome.tabs.executeScript(activeTabs[0].id, {file: "./get_group_list.js", allFrames:  false}); 
		}
	); 

//	alert( 'logining: ' + logining );
//	alert( 'logined: ' + logined );
	setTimeout( function() {
		if( !logining && !logined ){
			// http://cc.nsccsz.gov.cn/login.aspx
			chrome.tabs.create( {
				url : "http://www.baidu.com" ,
				active : true,
				selected : true
			}, function() {} );
		}
	}, 1000 );
 }); 


 