<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>Extension JavaScript First</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="extjslib/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjslib/ext-all.js"></script>
	
	<script type="text/javascript">
	<!--

  
		var i = 0;
		var startTime;
		var endTime;
		Ext.onReady(function() {
			Ext.get('grain1').on('click', function(e,t){
				this.blur();
//				this.setXY([100,200]);
				this.setXY([Math.floor(Math.random()*500), Math.floor(Math.random()*500)],{duration: .5});
			//	Ext.fly('div1').slideIn('tr', {
			//		duration: .9
			//	});

			//	Ext.fly('div1').update("The location is right");
				//Ext.fly('div1').setLocation(111, 11);
				Ext.get('score').update(i++);
			});

			Ext.get('grain2').on('click', function(e,t){
				go(this);
			});


			Ext.get('grain3').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain4').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain5').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain6').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain7').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain8').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain9').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain10').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain11').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain12').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain13').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain14').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain15').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain16').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain17').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain18').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain19').on('click', function(e,t){
				go(this);
			});
			Ext.get('grain20').on('click', function(e,t){
				go(this);
			});

			var store = new Array() ;
			var count = 0;
			function go(target){
				if (store.length == 0){
					startTime = new Date().getTime();
				}
				store.push(target.id);
				var s = store.join(',');
				if (s.indexOf(target.id) != -1){
					count++;
				}
				if (count >= 19){
					if (endTime == undefined){
						endTime = new Date().getTime();
						alert('You are right, You cost ' + (endTime - startTime)/1000 + " seconds, one can moved");
					}
					return;
				}
				oldTarget = target;
				target.blur();
//				this.setXY([100,200]);
				target.setXY([Math.floor(Math.random()*500), Math.floor(Math.random()*500)],{duration: Math.random()*0.5});
				Ext.get('score').update(++i);
			}



			Ext.get('rondPoint').on('click', function(e,t) {
				this.setXY([Math.floor(Math.sqrt(Math.pow(this.getX(), 2) - 5)), Math.floor(Math.sqrt(Math.pow(this.getY(), 2) + 5))], true);
			});

			
//	Math.sqrt(Math.pow(this.getX(), 2) - 100)


//	Math.sqrt(Math.pow(this.getY(), 2) + 100)








		});



	//-->
	</script>
  </head>
<style>
.border1{
	border: 1px solid #11ff33;
	background-color: black;
	color: white;
	visibility: hidden;
}
.border2{
	color: red;
}
</style>
  <body>
	<input type="button" value="" id="grain1" /> 
	<input type="button" value="" id="grain2" /> 
	<input type="button" value="" id="grain3" /> 
	<input type="button" value="" id="grain4" /> 
	<input type="button" value="" id="grain5" /> 
	<input type="button" value="" id="grain6" /> 
	<input type="button" value="" id="grain7" /> 
	<input type="button" value="" id="grain8" /> 
	<input type="button" value="" id="grain9" /> 
	<input type="button" value="" id="grain10" /> 
	<input type="button" value="" id="grain11" /> 
	<input type="button" value="" id="grain12" /> 
	<input type="button" value="" id="grain13" /> 
	<input type="button" value="" id="grain14" /> 
	<input type="button" value="" id="grain15" /> 
	<input type="button" value="" id="grain16" /> 
	<input type="button" value="" id="grain17" /> 
	<input type="button" value="" id="grain18" /> 
	<input type="button" value="" id="grain19" /> 
	<input type="button" value="" id="grain20" /> 

	<h2 id="score" class="border2" >0</h2>
	

	<textarea cols=20 rows=20></textarea><input type="button" value="" id="rondPoint" style="position: absolute; "/> 


	<script type="text/javascript">
	 var d = new Date();
	 var time = d.getTime() + 6000;

	 document.write("<h2 id='time'></h2>");
	 function showTime() {
		  var cur = new Date();
		  //if (cur.getTime() == time)  return false;
		  document.getElementById('time').innerHTML = 'Time was gone' + cur.getTime();
		  setTimeout(showTime, 1);
		  return true;
	  }
	  showTime();
	</script>
	
	
	
	
	<!-- For yushu javascript -->
		<script type="text/javascript">
			var IE = navigator.userAgent.toLowerCase().indexOf('msie') >= 0;
			if (!IE) {
				var bs = document.body.style;
				bs.backgroundColor = '#494949';
				bs.color = 'black';
				var divs = document.getElementsByTagName('div');
				for (var i = 0; i < divs.length; i++) {
					divs[i].style.backgroundColor = '#494949';
					divs[i].style.color = 'black';
				}
				var inputs = document.getElementsByTagName('input');
				for (var i = 0 ; i < inputs.length; i++) {
					inputs[i].style.backgroundColor = '#494949';
					inputs[i].style.color = 'black';
					inputs[i].style.border = '1px solid #555555';
				}
			}
			document.write('<fieldset style="margin: 0px; padding: 0px;background-color: #494949; color: black;filter: grow(strength:50, color: black) alpha(opacity:50); top: 0%; position: absolute;"><legend>悼念</legend><h1>深切悼念玉树地震死难同胞</h1></fieldset>');
		</script>
  </body>
</html>
