package messenger;
import ipmsg.*;

import java.io.*;
import java.util.*;
import java.net.*;

public class Messenger extends IPMessenger{
    // メッセンジャリスナー
    private ArrayList<MessageListener> 
    	ob_messageListener = new ArrayList<MessageListener>();

    /**
     * コンストラクタ。(port番号,host名指定版)
     *
     * @param userName ユーザ名
     * @param nickName ニックネーム
     * @param group グループ名
     * @param in_inPort 使用port番号
     * @param st_inHost ホスト名
     */
    public Messenger(final String st_inUserName,
    				 final String st_inNickName,
    				 final String st_inGroup,
    				 final int in_inPort,
    				 final String st_inHost) throws IOException {
        super.nickName = st_inNickName;
        super.group = st_inGroup;
        super.userName = st_inUserName;
        super.hostName = st_inHost;
        super.in_port = in_inPort;
        super.hostName    = InetAddress.getLocalHost().getHostName();
        super.absenceMode = false;
        super.absenceMsg  = "";
        super.socket = new DatagramSocket(in_inPort);
    }

    /**
     * group設定
     */
    public void setGroup(String st_inGroup){
        super.group = st_inGroup;
    }
    /**
     * nickName設定
     */
    public void setNickName(String st_inNickName){
        super.nickName = st_inNickName;
    }

    public String getGroup(){
        return super.group;
    }
    public String getNickName(){
        return super.nickName;
    }
    public String getHost(){
        return super.hostName;
    }

    // メッセンジャリスナー設定
    public void addMessageListener(MessageListener ob_inMessageListener){
        ob_messageListener.add(ob_inMessageListener);
    }
    // 開封通知
    public void openMsg(String st_inHost,String st_inNickName){
        if (ob_messageListener != null){
            // 保持しているメッセンジャリスナーすべてに通知
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).openMsg(st_inHost,st_inNickName);
            }
            ob_iterator = null;
        }
    }
    // ユーザ追加通知
    public void addMember(String st_inHost,String st_inNickName,String st_inGroup,String st_inAddr, int in_inAbsence){
        if (ob_messageListener != null){
            // 保持しているメッセンジャリスナーすべてに通知
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).addMember(st_inHost,st_inNickName,st_inGroup,st_inAddr,in_inAbsence);
            }
            ob_iterator = null;
        }
    }
    // メッセージ受信
    public void receiveMsg(String st_inHost,String st_inNickName,String st_inMsg, boolean in_boLock){
        if (ob_messageListener != null){
            // 保持しているメッセンジャリスナーすべてに通知
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).receiveMsg(st_inHost,st_inNickName,st_inMsg,in_boLock);
            }
            ob_iterator = null;
        }
    }
    // ユーザ削除
    public void removeMember(String st_inHost){
        if (ob_messageListener != null){
            // 保持しているメッセンジャリスナーすべてに通知
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).removeMember(st_inHost);
            }
            ob_iterator = null;
        }
    }
}
