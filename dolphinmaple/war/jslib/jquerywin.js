// ��ʾ�����Ĵ��ڵķ���

function showwin() {
    //alert("showwin");

    //1. �ҵ� div �ڵ�

    var winNode = $("#win");

    //2. �ýڵ���ʾ����
       //** ����1.�޸Ľڵ��cssֵ,�ô�����ʾ����
    //winNode.css("display","block");
       //** ����2.����Jquery�� show ����s
    //winNode.show("show");    
       //** ����3.����Jquery�� fadeIn ����s
    winNode.fadeIn("show");
}

function closeWin(){
    //alert("Closing the window");
    var winNode = $("#win");
    //alert(winNode);
    winNode.css("display","none");
}
//���ش��ڵķ���
function hide(){
    //alert("I am hide ");
    //1 �ҵ����ڵĽڵ�
    var winNode = $("#win");
    //2 ��������������
      //**����1 ���޸�css
    //winNode.css("display","none");
      //**����2 ������  hide ����
    //winNode.hide("slow");
      //**����3 ������  fadeOut ����
    winNode.fadeOut("slow");
}