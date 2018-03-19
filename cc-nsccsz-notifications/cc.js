/********************
  Copyright (c) 2014 NSCCSZ. All rights reserved.
  by qwop 2014 07 31
*********/
var g_oldTemplateTitle = '';
var g_times = 0;

function show() {
	//  var time = /(..)(:..)/.exec(new Date());     // The prettyprinted time.
	//  var hour = time[1] % 12 || 12;               // The prettyprinted hour.
	//  var period = time[1] < 12 ? '上午' : '下午'; // The period of the day.
	  var body = '';
	  // first load template title.
	  if( g_oldTemplateTitle == '' ){
		  g_oldTemplateTitle = document.title + " 已查询 #times# 次 by qwop" ; 
	  }
	

	  if( typeof( Ext ) != 'undefined'  ){
		// 登陆页面自动登陆.
		if(document.location.href.indexOf( 'login.aspx' ) >= 0 ){
			chrome.extension.sendMessage( { cmd: 'getUser' } ,function(response) {
				Ext.getDom("txtUsername").value = response.username;
				Ext.getDom("txtPassword").value = response.password;
				setTimeout( function() {
					var btn1 = Ext.getDom( "btnLogin" );
					if( btn1 ){
						btn1.click();
						// Ext.fly("btnLogin").click();
						return;
					}
				}, 
				1000 );
			});
		}

		var btn = Ext.getDom( "button-1040" );
		if( btn != null ){
			btn.click();
			setTimeout( function() {
				var gridHTML = Ext.getDom( "gridview-1054" ).innerHTML;
				g_times++;
				document.title = g_oldTemplateTitle.replace( '#times#', g_times );
				if( gridHTML.indexOf( '没有数据' ) < 0 ){
					body = "工单有数据";
				}
				if(body != '' ){
					chrome.extension.sendMessage({cmd: 'notify', msg: body },function(response) {} );			
				}
			}, 1000 );
		}
	  }
}


// Test for notification support.
if (window.webkitNotifications) {
	// While activated, show notifications at the display frequency.
	if (JSON.parse(localStorage.isActivated)) { 
		show(); }

	var interval = 0; // The display interval, in minutes.

	setInterval(function() {
		interval++;

		if ( JSON.parse(localStorage.isActivated) && localStorage.frequency <= interval ) {
		  show();
		  interval = 0;
		}
	}, 60000 );
}