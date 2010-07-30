package messenger;
import ipmsg.*;

import java.io.*;
import java.util.*;
import java.net.*;

public class Messenger extends IPMessenger{
    // ���b�Z���W�����X�i�[
    private ArrayList<MessageListener> 
    	ob_messageListener = new ArrayList<MessageListener>();

    /**
     * �R���X�g���N�^�B(port�ԍ�,host���w���)
     *
     * @param userName ���[�U��
     * @param nickName �j�b�N�l�[��
     * @param group �O���[�v��
     * @param in_inPort �g�pport�ԍ�
     * @param st_inHost �z�X�g��
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
     * group�ݒ�
     */
    public void setGroup(String st_inGroup){
        super.group = st_inGroup;
    }
    /**
     * nickName�ݒ�
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

    // ���b�Z���W�����X�i�[�ݒ�
    public void addMessageListener(MessageListener ob_inMessageListener){
        ob_messageListener.add(ob_inMessageListener);
    }
    // �J���ʒm
    public void openMsg(String st_inHost,String st_inNickName){
        if (ob_messageListener != null){
            // �ێ����Ă��郁�b�Z���W�����X�i�[���ׂĂɒʒm
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).openMsg(st_inHost,st_inNickName);
            }
            ob_iterator = null;
        }
    }
    // ���[�U�ǉ��ʒm
    public void addMember(String st_inHost,String st_inNickName,String st_inGroup,String st_inAddr, int in_inAbsence){
        if (ob_messageListener != null){
            // �ێ����Ă��郁�b�Z���W�����X�i�[���ׂĂɒʒm
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).addMember(st_inHost,st_inNickName,st_inGroup,st_inAddr,in_inAbsence);
            }
            ob_iterator = null;
        }
    }
    // ���b�Z�[�W��M
    public void receiveMsg(String st_inHost,String st_inNickName,String st_inMsg, boolean in_boLock){
        if (ob_messageListener != null){
            // �ێ����Ă��郁�b�Z���W�����X�i�[���ׂĂɒʒm
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).receiveMsg(st_inHost,st_inNickName,st_inMsg,in_boLock);
            }
            ob_iterator = null;
        }
    }
    // ���[�U�폜
    public void removeMember(String st_inHost){
        if (ob_messageListener != null){
            // �ێ����Ă��郁�b�Z���W�����X�i�[���ׂĂɒʒm
            Iterator<MessageListener> ob_iterator = ob_messageListener.iterator();
            while(ob_iterator.hasNext()){
                ((MessageListener)ob_iterator.next()).removeMember(st_inHost);
            }
            ob_iterator = null;
        }
    }
}
