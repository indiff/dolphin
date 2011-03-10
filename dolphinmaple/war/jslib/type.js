var _timeout; 
var _length = _contents.length;
var _count = 0;
var _closeable = false;
var _speed = 100;
var _mod = 50;
function _type(){ 
  if(_timeout!=undefined){ 
	clearTimeout(_timeout); 
  } 
  if (_closeable){
	_count = 0; _closeable = false;
	delete _timeout,_length,_count,_closeable,_speed,_mod,_contents;
	return;
  } 
  appendChar(); 
  _timeout = window.setTimeout(_type,_speed); 
} 
function appendChar(){ 
  if (_count == _length - 1){
	_closeable = true;
	return;
  }
  var element = document.createElement('<b>'); 
  var c = _contents.charAt(_count++);
  if (_count % _mod == 0){
	  element.innerHTML= c + '<BR>'; 
  } else {
    element.innerHTML= c; 
  }
  document.getElementById('content').appendChild(element);    
} 