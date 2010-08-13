// /*
//    * Created on 2003-11-21 by Liudong
//*/
//    package jmsn.demo;
//import rath.msnm.MSNMessenger;
//import rath.msnm.SwitchboardSession;
//import rath.msnm.UserStatus;
//import rath.msnm.entity.MsnFriend;
//import rath.msnm.event.MsnAdapter;
//import rath.msnm.msg.MimeMessage;
///**
//* MSN演示程序
//* @author Liudong
//*/
//    public class MSNDaemon extends Thread
//{
//private static MSNMessenger msn;
//public static void main(String[] args)
//{
//msn = new MSNMessenger
//("youraccount@hotmail.com", "password");
//msn.setInitialStatus(UserStatus.ONLINE);
//msn.addMsnListener(new MSNAdapter(msn));
//msn.login();
//System.out.println
//("Waiting for the response....");
////捕捉Ctrl+C的输入以便注销MSN的登录
//    Runtime.getRuntime().
//addShutdownHook(new MSNDaemon());
//}
///**
//	 * 用户中止程序执行
//*/
//    public void run()
//{
//msn.logout();
//System.out.println("MSN Logout OK");
//}
//}
///**
//* MSN消息事件处理类
//* @author Liudong
//*/
//    class MSNAdapter extends MsnAdapter
//{
//MSNMessenger messenger;
//public MSNAdapter(MSNMessenger messenger)
//{
//this.messenger = messenger;
//}
///**
//* 某人正在输入信息
//*/
//    public void progressTyping(
//SwitchboardSession ss,
//MsnFriend friend,
//String typingUser)
//{
//System.out.println
//(friend.getLoginName() + "正在输入信息...");
//}
///**
//* 收到消息的时候执行该方法
//*/
//    public void instantMessageReceived(
//SwitchboardSession ss,
//MsnFriend friend,
//MimeMessage mime)
//{
//System.out.print("接收到消息：
//
//    " + friend.getFriendlyName() + "->");
//    System.out.println(mime.getMessage());
//try {
////发送相同的回复信息给发送者
//    messenger.sendMessage
//(friend.getLoginName(), mime);
//} catch (Exception e)
//{
//e.printStackTrace();
//}
//}
///**
//* 登录成功后执行该方法
//*/
//    public void loginComplete(MsnFriend own)
//{
//System.out.println
//(own.getLoginName() + " Login OK");
//}
///**
//* 登录失败后执行该方法
//*/
//    public void loginError(String header)
//{
//System.out.println
//("Login Failed: " + header);
//}
///**
//* 好友离线时执行该方法
//*/
//    public void userOffline(String loginName)
//{
//System.out.println
//("USER " + loginName + " Logout.");
//}
///**
//* 好友上线时执行该方法
//*/
//    public void userOnline(MsnFriend friend)
//{
//System.out.println
//("USER "+friend.getFriendlyName()+" Login.");
//}
///**
//* 有人加我为好友时执行
//*/
//    public void whoAddedMe(MsnFriend friend)
//{
//System.out.println
//("USER " + friend.getLoginName() + " Addme.");
//try {
//messenger.addFriend(friend.getLoginName());
//} catch (Exception e)
//{
//e.printStackTrace();
//}
//}
///**
//* 有人把我从好友列表中删除时执行
//*/
//    public void whoRemovedMe(MsnFriend friend)
//{
//System.out.println
//("USER "+friend.getLoginName()+" Remove me.");
//try {
//messenger.removeFriend(friend.getLoginName());
//} catch (Exception e)
//{
//e.printStackTrace();
//}
//}
//}