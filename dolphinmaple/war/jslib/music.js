var playerControl;
function music(i) {
	 if (!playerControl) {playerControl = document.getElementById('playerControl');}
	 var html = ['<embed width="290" height="40" type="application/x-shockwave-flash" src="', 'resources/audio.swf?&amp;soundFile='];
	 i = parseInt(i);
	 if(i===0){html.push('http://www.haofumu.tv/tjzhfmadmin/ewebeditor/uploadfile/20090428165152438.mp3');}
	 if(i===1){html.push('http://rm.sina.com.cn/wm/VZ200711211724501086VK/music/1.mp3');}
	 if(i===2){html.push('http://file.u148.net/attachments/audio/month_0806/concert/yuqiaowenda.mp3');}
	 if(i===3){html.push('http://file.u148.net/attachments/audio/month_0806/concert/chunjianghuayueye.mp3');}
	 if(i===4){html.push('http://api.ning.com/files/ts*8syD9E*I0THFhL9U*xrF8Ea8KhvNr6T3Cg8BrRAvNgGZQSv2nE4TaJH-AqnsjfZsVeNccSN6X9hwlxIAvBUtcw5Muiu3Y/nX1-l6SngpCfe36ZpGmYmqF8daqWenxcMQ$$.mp3'); }
	 if(i===5){html.push('http://www.lounge.org.cn/wp-content/uploads/2009/04/08-moon-flow.mp3');}
	 if(i===6){html.push('http://qq.345655.com/345655/%E9%9F%B3%E4%B9%90%E5%BF%83%E6%83%85/%E9%94%99%E8%A7%89_%E7%8E%8B%E9%9C%B2%E5%87%9D.mp3');}
	 if(i===7){html.push('http://c2.api.ning.com/files/lmgkjmB8BXuDne7ur995ovJM4BdrBFj*MK1fRONMyvrUfch0bLfAxidgmBarVk1COnonwoYJz98aOlpFX1EYRU*SFEMa9OLD/oqN6o6SmsJuho6SlpJegp5-MqJmikGmMfqlXWDU$.mp3');}
	 if(i===8){html.push('http://www.haofumu.tv/tjzhfmadmin/ewebeditor/uploadfile/20090428165152438.mp3');}
	 if(i===9){html.push('http://www.lin255188.com/Music2/%A1%B6%CA%AE%CA%D7%B1%AF%C7%E9%C7%E1%D2%F4%C0%D6%A1%B7/200812685582441.mp3');}
	 if(i===10){html.push('http://www.kngyp.com/11.mp3');}
	 html.push('&amp;playerID=43019&amp;loop=yes&amp;autostart=yes" id="player">');
	 if (!playerControl) {
		 html.unshift("<p id=\"playerControl\">");
		 html.push("</p>");
		 document.write(html.join(''));
	 } else {playerControl.innerHTML = html.join('');}
	 if (musics[i]) {document.title = '[' + musics[i] + ']';}
	 // Destroy the variable. 
	 delete html,i;
}
function random(max){
	   var time = new Date().getTime().toString();
	   var len = time.length;
	   var c ;
	   for (var i = len - 1; i >= 0; i++){
		    c = time.charAt(i);
			if (c <= max){return c;}
	   }
	   return 0;
}