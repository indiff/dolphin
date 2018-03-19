var _account = "indiff";
var _username = $( '.username' ).text();
if ( typeof( _username ) != 'undefined' && _username != '' ) {
	_account = _username;
}
var jsonAccount = JSON.parse( localStorage[ _account ] );
var jsonStr = ( localStorage[ _account ] );
var passengersArr = JSON.parse( jsonStr )[ "passengers" ];
var g_map = {
	'景德镇' : 'JCG',
	'深圳' : 'SZQ',
	'南昌' : 'NCG',
	'九江' : 'JJG',
	'绩溪县' : 'JRH',
	'河源' : 'VIQ',
	'黄山' : 'HKH',
	'祁门' : 'QIH',
	'东莞东' : 'DMQ',
	'东莞' : 'RTQ',
	'惠州' : 'HCQ'
};

// 配置数据
var _forwardDate = "2016-02-13";
var _prioritySeats = [  "硬卧", "软卧" ];// , "硬座" 
// var _passengers = selectPassengerName( passengersArr, '谭元吉', '王晓芳');  //  '谭元吉', '王晓芳'  黄益成
var _passengers = selectPassengerName( passengersArr, '谭元吉');  //  '谭元吉', '王晓芳'  黄益成
var _alternativeDate = makeAlternativeDate( "2016-02-12", _forwardDate,  "2016-02-14" );
var _priorityTrains = [ "K25" ];  // K25

// 设置定时切换终点站
// var _delayCities = [ "黄山", "绩溪县", "景德镇" ];  // "黄山", "绩溪县", "景德镇"
var _delayCities = [ "绩溪县", "景德镇" ];  // "黄山", "绩溪县", "景德镇"

/*
6	芜湖	23:02	23:08	6分	5小时
7	宣城	00:02	00:09	7分	6小时
8	绩溪县	02:12	02:18	6分	8小时10分钟
9	黄山	03:21	03:30	9分	9小时19分钟
10	祁门
*/
// 设置定时切换起点站
var _delayFromCities = ["宣城", "绩溪县", "黄山", "祁门", "景德镇" ];  // "黄山", "绩溪县", "景德镇"
// var _delayFromCities = [ "黄山", "绩溪县", "宣城", "景德镇" ];  // "黄山", "绩溪县", "景德镇"
//var _delayCities = [ "定南", "赣州" ];
var _delayTime = 0;
var _delayIndex = 0;
var _delayFlag = true;
var _delayId = 0;


if ( _delayFromCities.length > 0 ) {
	_delayTime = _delayFromCities.length * 3 * 1000;
} else {
	_delayTime = _delayCities.length * 3 * 1000;
}

var intervalCall = function() {
		var _city = ""; 
		
		if ( _delayFromCities.length > 0 ) {
			_city = _delayFromCities[ _delayIndex++ ];
			$( "#station_from_text" ).focus().keydown().val( "" )
		} else {
			_city = _delayCities[ _delayIndex++ ];
			$( "#station_to_text" ).focus().keydown().val( "" )
		}

		if ( _city && _city != "" ) {
			$( "#piao_station_el_box1 > ul.b1-list" ).append( '<li title="' + _city + '" class="selected">' + _city + '</li>' );	
			$( "#piao_station_el_box1 > ul.b1-list > li.selected" ).click();
		}

		setTimeout(function(){
			for ( var i = 0;i < _priorityTrains.length; i++ ) {
				$( "#" + _priorityTrains[i] ).click();
			}
		},500);

		if ( _delayIndex == _delayCities.length ) _delayIndex = 0;
};

if ( _delayFlag && _delayTime > 0 ) {
	_delayId =　setInterval( intervalCall , _delayTime );
}

// 通过内容
function _travel( obj ) {
	var travelText = $( obj ).text();
	if ( typeof( travelText ) != "undefined" && $.trim( travelText ) != "" ) {
		var cities = travelText.split( "->" );
		if ( cities.length == 2 ){
			_load( cities[ 0 ], cities[ 1 ] );
		}	
	} else {
		alert( "路程数据为空！" );
		return false;
	}
}

// 深圳->赣州1
function sz_gz_01(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'深圳';
	newObj[ 'TO_STATION' ]		=	'赣州';
	newObj[ 'passengers' ] = _passengers;
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'prioritySeats' ]	=	[  "一等座", "二等座", "硬卧", "硬座", "动卧","软卧", "高级软卧" ];
	// newObj[ 'priorityTrains' ] ["SZQ_GZG"]	= [  "K106","K1620","K26" ];
	newObj[ 'priorityTrains' ]	= {
		"SZQ_GZG" : [  "K106","K1620","K26" ] ,   // 设置对应的坐票
		"SZQ_GZG" : [  "K106","K1620","K26" ] ,   // 设置对应的坐票
	};
	localStorage[ _account ] = JSON.stringify( newObj );
}



// 深圳->赣州2 
function sz_gz_02(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'深圳';
	newObj[ 'TO_STATION' ]		=	'定南';
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'prioritySeats' ]	=	[  "一等座", "二等座", "硬卧", "硬座", "动卧","软卧", "高级软卧" ];
//	newObj[ 'priorityTrains' ] ["SZQ_DNG"]	= [  "K106","K1620","K26" ];
	newObj[ 'priorityTrains' ]	= {
		"SZQ_GZG2015/12/7" : [  "K106","K1620","K26" ],
		"SZQ_DNG" : [  "K106","K1620","K26" ]
	};
	newObj[ 'passengers' ] = _passengers;
	localStorage[ _account ] = JSON.stringify( newObj );
}


function _load( from, to ){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	from;
	newObj[ 'TO_STATION' ]		=	to;
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'prioritySeats' ]	=	_prioritySeats;  //高级软卧
	newObj[ 'passengers' ] = _passengers;
	localStorage[ _account ] = JSON.stringify( newObj );
}


// 深圳->景德镇
function sz_jdz1(){
	_load( "深圳", "景德镇" );
}

// 景德镇->深圳
function jdz_sz(){
	_load( "景德镇", "深圳" );
}

// 景德镇->深圳345
function jdz_sz345(){
	_load( "景德镇", "深圳" );
}

// 南昌->景德镇
function nc_jdz_02(){
	_load( "南昌", "景德镇" );
}

// sz_dest
function sz_dest( dest ){
	_load( "深圳", dest );
}


// 深圳北->长沙南
function szb_csn(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'深圳北';
	newObj[ 'TO_STATION' ]		=	'长沙南';
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'prioritySeats' ]	=	["一等座","二等座","硬卧", "软卧", "高级软卧"];
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'passengers' ] = selectPassengerName( passengersArr, '何骧', '康贺梁', '黄秋菊');
	localStorage[ _account ] = JSON.stringify( newObj );
}


// 广州南->长沙南
function gzn_csn(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'广州南';
	newObj[ 'TO_STATION' ]		=	'长沙南';
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'prioritySeats' ]	=	["一等座","二等座","硬卧", "软卧", "高级软卧"];
	newObj[ 'alternativeDate' ] = makeAlternativeDate(  "2015-03-08", "2015-03-09", "2015-03-10" );
	newObj[ 'passengers' ] = selectPassengerName( passengersArr, '何骧', '康贺梁', '黄秋菊' );
	localStorage[ _account ] = JSON.stringify( newObj );
}

// 深圳->南昌
function sz_nc_01(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'深圳';
	newObj[ 'TO_STATION' ]		=	'南昌';
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'prioritySeats' ]	=	[  "一等座", "二等座", "硬卧", "动卧","软卧", "高级软卧" ];
	newObj[ 'priorityTrains' ] ["SZQ_NCG"]	= [  "G638","G634","D2322" ];
	newObj[ 'passengers' ] = _passengers;
	localStorage[ _account ] = JSON.stringify( newObj );
}





// 东莞->九江
function dg_jj(){
	var newObj = JSON.parse( jsonStr );
	newObj[ 'FROM_STATION' ]	=	'东莞';
	newObj[ 'TO_STATION' ]		=	'九江';
	newObj[ 'forwardDate' ]		=	_forwardDate;
	newObj[ 'timeout' ]		=	1500;
	newObj[ 'autoSubmit' ]		=	{ "auto": true };
	newObj[ 'alternativeDate' ] = _alternativeDate;
	newObj[ 'prioritySeats' ]	=	[  "一等座", "二等座", "硬卧", "动卧","软卧", "高级软卧" ];
	newObj[ 'priorityTrains' ] ["RTQ_JJG"]	= [  "K4510","3160","3388", "3108", "L270" ];
	newObj[ 'passengers' ] = _passengers;
	localStorage[ _account ] = JSON.stringify( newObj );
}

// 全点击
function all_click(){
	var ids = [
		'sz_jdz1', 'sz_lp', 'sz_hs', 'sz_jx', 'sz_xc' 
	];
	alert( 'new all click' );
	document.getElementById( 'sz_jdz1' ).click();
	document.getElementById( 'sz_lp' ).click();
	for( var i=0; i<ids.length; i++ ){
		break;
	}
}





/////////////////// base function start
function getType(o)
{
	var _t;
	return ((_t = typeof(o)) == "object" ? o==null && "null" || Object.prototype.toString.call(o).slice(8,-1):_t).toLowerCase();
}
function extend(destination,source)
{
	for(var p in source)
	{
		if(getType(source[p])=="array"||getType(source[p])=="object")
		{
			destination[p]=getType(source[p])=="array"?[]:{};
			arguments.callee(destination[p],source[p]);
		}
		else
		{
			destination[p]=source[p];
		}
	}
}

function selectPassengerName(){
	var args = arguments;
	if( null != args && args.length > 1 ){
		var arr = args[0];
		if( null != arr && arr.length > 0 ){
			var newArr = [];
			var priorityIndex = 1;
			// 1. deep copy 
			extend(newArr, arr );

			// 2. reset checked
			for( var j=0; j < newArr.length; j++ ){
				newArr[j].checked = false;
				newArr[j].priority = 0;
			}

			// 3. set checked 
			for( var i=1; i<args.length; i++ ){
				var name = args[i];
				for( var j=0; j < newArr.length; j++ ){
					if( name != '' && name == newArr[j].passenger_name  ){
						newArr[j].checked = true;
						newArr[j].priority = priorityIndex++;
					} 
				}
			}
			return newArr;
		}
	}
	return null;
	// passenger_name
}



// 创建备选日期
function makeAlternativeDate(){
	var args = arguments;
	var arr = [];
	var date = new Date().getDate();
	if( null != args && args.length > 0 ){
		var priorityIndex = 0;
		for( var i=0; i< args.length ; i++ ){
			var theDate = new Date( args[i] );
			/*
			var theDateDate = theDate.getDate();
			if( theDateDate > date ){
				continue;
			}
			*/
			arr.push( {
				"id": theDate.getTime(), 
				"text": args[i], 
				"checked": true, 
				"priority": priorityIndex++, 
				"display": ""
			});
		}
	}
	return arr;
}



/////////////////// base function end

function stop_delay_01() {
	if ( _delayId != 0 ){
		clearInterval( _delayId );
		_delayId = 0;
		alert( "关闭成功" );
	} else {
		_delayId =　setInterval( intervalCall , _delayTime );
		alert( "启动成功" );
	}
}
/*
乐平市	LPS	LPG		南	FALSE	赣	×
绩溪县	JXX	JRH	31373	上	FALSE	皖	
宣城	XCH	ECH	31319	上	TRUE	皖	
景德镇	JDZ	JCG	31469	南	FALSE	赣	

var href = parent.location.href;
var idx = href.indexOf( '#' );
if( idx> 0 ){
	var funName = ( href.substring( idx+1 ) );
	eval( funName + "();");
}
*/

var script = document.createElement('script');
script.type = 'text/javascript';
// http://pc.huochepiao.360.cn/?from=BJP&to=DLT&date=2015-03-08
script.innerHTML = ""
	// + "$('<a id=first style=color:red; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>新页面</b></a>').appendTo('div.filter');"

	+ "$('<a id=sz_nc_01 style=color:#3300cc; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>01.深圳->南昌</b></a>').appendTo('div.filter');"
	+ "$('<a id=nc_jdz_02 style=color:##3300cc; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>02.南昌->景德镇</b></a>').appendTo('div.filter');"
	+ "$('<a id=dg_jj style=color:#00cc00; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>东莞->九江</b></a>').appendTo('div.filter');"
	

	+ "$('<a id=sz_jdz1 style=color:#0000ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->景德镇</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_lp style=color:#ff00ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->乐平市</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_hs style=color:#cc0000; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->黄山</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_jx style=color:#330000; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->绩溪县</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_xc style=color:#ff9900; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->宣城</b></a>').appendTo('div.filter');"
	+ "$('<a id=jdz_sz class=_travel style=color:#ff00ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>景德镇->深圳</b></a>').appendTo('div.filter');"
	+ "$('<a class=_travel style=color:#ff3300; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>黄山->深圳</b></a>').appendTo('div.filter');"
	+ "$('<a class=_travel style=color:#cc33ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>绩溪县->深圳</b></a>').appendTo('div.filter');"
	+ "$('<a class=_travel style=color:#9900ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>宣城->深圳</b></a>').appendTo('div.filter');"
	+ "$('<a class=_travel style=color:#9966ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>乐平->深圳</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_gz_01 style=color:#0000ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->赣州1</b></a>').appendTo('div.filter');"
	+ "$('<a id=sz_gz_02 style=color:#0000ee; target=_blank href=http://pc.huochepiao.360.cn/?src=ext><b>深圳->赣州2</b></a>').appendTo('div.filter');"
	+ "$('<a id=stop_delay_01 style=color:#330000; href=#close ><b>关闭</b></a>').appendTo('div.filter');"
	+ "$('<a id=jdz_sz345 style=color:#cc66ff; target=_blank href=http://pc.huochepiao.360.cn/?src=ext title=3月4日、3月5日><b>景德镇->深圳345</b></a>').appendTo('div.filter');"
	
	
	// + "$('<a id=all_click style=color:#ff0000; title=全部点击，延迟1秒><b>全点</b></a>').appendTo('div.filter');"
	// + "$('<a id=szb_csn style=color:green; target=_blank href=http://pc.huochepiao.360.cn/?from=SZQ&to=JCG&date=2015-02-14><b>深圳北->长沙南</b></a>').appendTo('div.filter');"
	//	+ "$('<a id=gzn_csn style=color:#ff33ff; target=_blank href=http://pc.huochepiao.360.cn/?from=SZQ&to=JCG&date=2015-02-14><b>广州南->长沙南</b></a>').appendTo('div.filter');"


	+ "$('#sz_nc_01').click(function(){ sz_nc_01(); }); "

	+ "$('#nc_jdz_02').click(function(){ nc_jdz_02(); }); "
	+ "$('#dg_jj').click(function(){ dg_jj(); }); "


	+ "$('#sz_jdz1').click(function(){ sz_jdz1(); }); "

	+ "$('#sz_lp').click(function(){ sz_dest( '乐平市' ); }); "

	+ "$('#sz_hs').click(function(){ sz_dest( '黄山'); }); "

	+ "$('#sz_jx').click(function(){ sz_dest( '绩溪县'); }); "

	+ "$('#sz_xc').click(function(){ sz_dest( '宣城'); }); "

	+ "$('#jdz_sz').click(function(){ jdz_sz(); }); "

	+ "$('#jdz_sz345').click(function(){ jdz_sz345(); }); "

	+ "$('#szb_csn').click(function(){ szb_csn(); }); "

	+ "$('#gzn_csn').click(function(){ gzn_csn(); }); "
	+ "$('#sz_gz_01').click(function(){ sz_gz_01(); }); "  // 深圳->赣州1
	+ "$('#sz_gz_02').click(function(){ sz_gz_02(); }); " // 深圳->赣州2
	+ "$('#stop_delay_01').click(function(){ stop_delay_01(); }); " // 深圳->赣州2
	
	+ "$('#all_click').click(function(){ all_click(); }); "
	
	+ "$('a._travel').click(function(){ _travel( this ); }); "
;

document.head.appendChild(script);
document.title = jsonAccount[ 'FROM_STATION' ].charAt( 0 ) + jsonAccount[ 'TO_STATION' ].charAt( 0 );
jQuery( '#btn_refresh' ).trigger( 'click' );