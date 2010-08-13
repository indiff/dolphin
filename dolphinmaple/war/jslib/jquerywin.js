// 显示浮动的窗口的方法

function showwin() {
    //alert("showwin");

    //1. 找到 div 节点

    var winNode = $("#win");

    //2. 让节点显示出来
       //** 方法1.修改节点的css值,让窗口显示出来
    //winNode.css("display","block");
       //** 方法2.利用Jquery的 show 方法s
    //winNode.show("show");    
       //** 方法3.利用Jquery的 fadeIn 方法s
    winNode.fadeIn("show");
}

function closeWin(){
    //alert("Closing the window");
    var winNode = $("#win");
    //alert(winNode);
    winNode.css("display","none");
}
//隐藏窗口的方法
function hide(){
    //alert("I am hide ");
    //1 找到窗口的节点
    var winNode = $("#win");
    //2 将窗口隐藏起来
      //**方法1 ，修改css
    //winNode.css("display","none");
      //**方法2 ，利用  hide 方法
    //winNode.hide("slow");
      //**方法3 ，利用  fadeOut 方法
    winNode.fadeOut("slow");
}