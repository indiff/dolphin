package ipmsg;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Naoki Takezoe
 */
public abstract class IPMessenger extends Thread {

    protected String  userName;
    protected String  nickName;
    protected String  group;
    protected String  hostName;
    protected DatagramSocket socket;
    protected boolean absenceMode;
    protected String  absenceMsg;
    protected int     in_port;
    protected boolean debug;
    private   boolean loopFlag;
    
    // ホストとユーザ名のマッピングを行うマップ
    private HashMap<String,String> userNames = new HashMap<String,String>();
    
    /** 送受信に使用する文字コード */
    private static String CHARSET = "utf-8";
    
    /**
     * デフォルトのコンストラクタ。
     */
    public IPMessenger(){
    }
    
    /** デバッグモードがtrueのとき標準出力にデバッグメッセージを出力します。*/ 
    protected void debugMessage(final String message){
        if(debug){
            System.out.println(message);
        }
    }
    
    /**
     * コンストラクタ。
     *
     * @param userName ユーザ名
     * @param nickName ニックネーム
     * @param group    グループ名
     * @param debug    デバッグモード
     */
    public IPMessenger(final String userName,
    		final String nickName,
    		final String group,
    		final boolean debug) throws IOException {
        this.userName    = userName;
        this.nickName    = nickName;
        this.group       = group;
        this.hostName    = InetAddress.getLocalHost().getHostName();
        this.absenceMode = false;
        this.absenceMsg  = "";
        this.socket      = new DatagramSocket(Constants.PORT);
        this.in_port     = Constants.PORT;
        this.debug       = debug;
    }

    /**
     * ログインします。
     */
    public void login() throws IOException {
        broadcastMsg(makeTelegram(Constants.IPMSG_BR_ENTRY|Constants.IPMSG_BROADCASTOPT,
                     this.nickName+"\0"+this.group));
        this.loopFlag = true;
    }

    /**
     * ログアウトします。
     */
    public void logout() throws IOException {
        broadcastMsg(makeTelegram(Constants.IPMSG_BR_EXIT|Constants.IPMSG_BROADCASTOPT,
                     this.nickName+"\0"+this.group));
        this.loopFlag = false;
    }

    /**
     * 不在者モード
     */
    public void absence(final String msg,
    		final boolean mode) throws IOException {
        int command;
        if(mode){
            if(msg==null || msg.equals("")){
                this.absenceMsg = "ABSENCE";
            } else {
                this.absenceMsg = "[" + absenceMsg + "]";
            }
            this.absenceMode = true;
            command = Constants.IPMSG_BR_ABSENCE|Constants.IPMSG_ABSENCEOPT;
        } else {
            this.absenceMsg  = "";
            this.absenceMode = false;
            command = Constants.IPMSG_BR_ABSENCE;
        }
        broadcastMsg(makeTelegram(command,this.nickName+this.absenceMsg+"\0"+this.group));
    }

    /**
     * メッセージ受信時にフックされる抽象メソッドです。
     * アプリケーション固有の処理を実装してください。
     *
     * @param host 送信者のホスト名
     * @param user ユーザ名
     * @param msg メッセージ
     * @param lock 封書かどうか
     */
    public abstract void receiveMsg(final String host,
    		final String user,final String msg, final boolean lock);

    /**
     * メッセージを送信します。
     */
    public void sendMsg(final String host,
    		final String msg,
    		final boolean secret) throws IOException {
        int mode = 0;
        if(secret){ mode = Constants.IPMSG_SECRETEXOPT; }
        send(makeTelegram(Constants.IPMSG_SENDMSG|mode, msg), host, in_port);
    }

    /**
     * メンバー追加時にフックされるメソッドです。
     * アプリケーション固有の処理を実装してください。
     *
     * @param host
     * @param nickName
     * @param group
     * @param addr
     * @param absence
     */
    public abstract void addMember(final String host,
    		final String nickName,
    		final String group,
    		final String addr,
    		final int absence);

    /**
     * メンバー削除時にフックされるメソッドです。
     * アプリケーション固有の処理を実装してください。
     *
     * @param host
     */
    public abstract void removeMember(final String host);

    /**
     * 封書メッセージの開封時にフックされるメソッドです。
     * アプリケーション固有の処理を実装してください。
     *
     * @param host
     * @param user
     */
    public abstract void openMsg(final String host,final String user);

    /**
     * 開封通知を送信します。
     *
     * @param host
     */
    public void readMsg(final String host) throws IOException {
        send(makeTelegram(Constants.IPMSG_READMSG|Constants.IPMSG_READCHECKOPT,
                          String.valueOf(new Date().getTime()/1000)),host, in_port);
    }

    /**
     * サーバの実行。ログイン後にstartメソッドから実行してください。
     */
    public void run(){
        try {
            while(loopFlag){
                byte[] buf = new byte[Constants.BUFSIZE];
                DatagramPacket packet = new DatagramPacket(buf,buf.length);
                this.socket.receive(packet);
                new ChildThread(packet).start();
            }
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * メッセージを送信します。
     *
     * @param msg メッセージ
     * @param host 送信先のホスト名
     * @param port 送信先のポート番号
     */
    private void send(final String msg,
    		final String host,
    		final int port) throws IOException {
        String message = msg;
        byte[] byteMsg = message.getBytes(CHARSET);
        DatagramPacket packet = new DatagramPacket(byteMsg,byteMsg.length,
                                                   InetAddress.getByName(host),port);
        socket.send(packet);
    }

    /**
     *
     */
    private String makeTelegram(final int command,
    		final String supplement){

        StringBuffer sb = new StringBuffer();

        sb.append(Constants.PROTOCOL_VER);
        sb.append(":");
        sb.append(new Date().getTime()/1000);
        sb.append(":");
        sb.append(this.userName);
        sb.append(":");
        sb.append(this.hostName);
        sb.append(":");
        sb.append(command);
        sb.append(":");
        sb.append(supplement);

        return sb.toString();
    }

    /**
     *
     */
    private void broadcastMsg(final String msg) throws IOException {
        String message = msg;
        byte[] byteMsg = message.getBytes(CHARSET);

        DatagramPacket packet = new DatagramPacket(byteMsg,byteMsg.length,
                                                   InetAddress.getByName("255.255.255.255"),
                                                   in_port);
        this.socket.send(packet);
    }

    /**
     * 文字列を指定した文字列で分割し、配列で返します。
     *
     * @param s1 分割対象の文字列。
     * @param s2 分割時の区切りに使用する文字列。
     * @return 分割結果を配列で返します。s1がnullの場合はnullを返します。
     */
    private static String[] split(final String s1,
    		final String s2){
        if(s1 == null){
            return null;
        }
        ArrayList<String> v  = new ArrayList<String>();
        int last  = 0;
        int index = 0;
        while((index=s1.indexOf(s2,last))!=-1){
            v.add(s1.substring(last,index));
            last = index + s2.length();
        }
        if(last!=s1.length()){
            v.add(s1.substring(last));
        }
        return (String[])v.toArray(new String[v.size()]);
    }
    
    /**
     * 受信したパケットを処理する内部スレッドクラス。
     */
    private class ChildThread extends Thread {
    	
    	private DatagramPacket packet;
    	
    	/** コンストラクタ。*/
    	public ChildThread(DatagramPacket packet){
    		this.packet = packet;
    	}
    	
    	/** パケットを処理します。*/
    	public void run(){
    		try {
    			String message = new String(packet.getData(),CHARSET);
    			debugMessage("[MSG]" + message.trim());
    			
                String[] telegram = split(message,":");

                int command = 0;
                command = Integer.parseInt(telegram[4]);
                int cmd_no  = command & 0x000000ff;

                InetAddress from = packet.getAddress();
                String fromAddr = split(from.toString(),"/")[1];
                String fromHost = fromAddr;
                int    fromPort = packet.getPort();
                int    packetNo = Integer.parseInt(telegram[1]);

                /* port番号が違っていたら処理しない */
                if (fromPort != in_port){
                    return;
                }
                
                switch(cmd_no){
                    case Constants.IPMSG_ANSENTRY: {
                        String[] dim = split(telegram[5], "\0");
                        if (dim[0].equals("")) {
                            dim[0] = telegram[2];
                        }
                        userNames.put(fromHost,dim[0]);
                        addMember(fromHost, dim[0], dim[1], fromAddr,
                                  Constants.IPMSG_ABSENCEOPT & command);
                        break;
                    }
                    case Constants.IPMSG_BR_ENTRY: {
                        String[] dim = split(telegram[5], "\0");
                        userNames.put(fromHost,dim[0]);
                        addMember(fromHost, dim[0], dim[1], fromAddr,
                                  Constants.IPMSG_ABSENCEOPT & command);
                        if (absenceMode) {
                            send(makeTelegram(Constants.IPMSG_ANSENTRY | Constants.IPMSG_ABSENCEOPT,
                                              nickName + absenceMsg + "\0" + group), fromAddr, fromPort);
                        }
                        else {
                            send(makeTelegram(Constants.IPMSG_ANSENTRY,nickName + "\0" + group),
                                 fromAddr, fromPort);
                        }
                        break;
                    }
                    case Constants.IPMSG_SENDMSG: {
                        boolean lockFlag = false;
                        if ( (command & Constants.IPMSG_SENDCHECKOPT) != 0) {
                            int ack_cmd = Constants.IPMSG_RECVMSG;
                            send(makeTelegram(ack_cmd,String.valueOf(packetNo)),
                                              fromAddr, in_port);
                        }
                        if ( (command & Constants.IPMSG_SECRETOPT) != 0) {
                            int ack_cmd = Constants.IPMSG_RECVMSG;
                            send(makeTelegram(ack_cmd,String.valueOf(packetNo)),
                                              fromAddr, in_port);
                            lockFlag = true;
                        }
                        // :で区切られたものを戻す
                        int in_length = telegram.length;
                        if(telegram.length > 6){
                            for(int j = 6;j < in_length ;j++){
                                telegram[5] += ":" + telegram[j];
                            }
                        }
                        String nickName = (String)userNames.get(fromHost);
                        //receiveMsg(fromHost, telegram[2], telegram[5].trim(),lockFlag);
                        receiveMsg(fromHost, nickName, telegram[5].trim(),lockFlag);
                        break;
                    }

                    case Constants.IPMSG_READMSG: {
                        if ( (command & Constants.IPMSG_READCHECKOPT) != 0) {
                            int ack_cmd = Constants.IPMSG_ANSREADMSG;
                            send(makeTelegram(ack_cmd,String.valueOf(packetNo)),
                                              fromAddr, in_port);
                            openMsg(fromHost, telegram[2]);
                        }
                        break;
                    }
                    case Constants.IPMSG_BR_ABSENCE: {
                        String[] dim = split(telegram[5], "\0");
                        addMember(fromHost, dim[0], dim[1], fromAddr,
                                  Constants.IPMSG_ABSENCEOPT & command);
                        break;
                    }

                    case Constants.IPMSG_BR_EXIT: {
                        userNames.remove(fromHost);
                        removeMember(fromHost);
                        break;
                    }
                }
            } catch(IOException ex){
               ex.printStackTrace();
            } finally {
            }
    	}
    }
}
