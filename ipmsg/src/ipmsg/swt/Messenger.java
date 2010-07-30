package ipmsg.swt;

import static util.Util.currentTime;
import ipmsg.IPMessenger;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;


/**
 * SWT�?PMessengerのバックエンドで動作するサーバスレ�?���?
 * 
 * @author Naoki Takezoe
 */
public final class Messenger extends IPMessenger {
    

    private Main main;
    private HashMap<String,String> hash = 
    	new HashMap<String,String>();
    
	/**
	 * Constructor for Messenger.
	 * 
	 * @param userName
	 * @param nickName
	 * @param group
	 * @throws IOException
	 */
	public Messenger(final String userName, 
					 final String nickName, 
					 final String group,
					 final Main main) throws IOException {
		super(userName, nickName, group, main.getDebugMode());
        this.main = main;
	}
	
	public Messenger(final String userName, 
			 final String nickName, 
			 final String group,
			 final String signature,
			 final Main main) throws IOException {
		super(userName, nickName, group,signature, main.getDebugMode());
		this.main = main;
	}

	public final void receiveMsg(final String host, 
								 final String user, 
								 final String msg, 
								 final boolean flag) {
		// �?��アログを表示
        main.getShell().getDisplay().asyncExec(new RunnableReceiveMessage(host,user,msg,flag,this));
        // ログ出�?
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("From:" + user + "(" + host + ")\n");
        sb.append(currentTime() + "\n");
        sb.append("-------------------------------------\n");
        sb.append(msg + "\n\n");
        Logger.writeLog(sb.toString());
	}
    
    public final void openMsg(final String host,
    						  final String user){
        main.getShell().getDisplay().asyncExec(new RunnableOpenMessage(host,user));
    }
    
	public final void addMember(final String host,
							  final String nickName,
							  final String group,
							  final String addr,
							  final int absence,
							  final String signature) {
        if(hash.get(host)==null){
            main.getShell()
	            .getDisplay()
	            .asyncExec(new RunnableAddMember(nickName,group,host,signature));
            hash.put(host,nickName);
        }
	}
	public final void addMember(
			  final String host,
			  final String nickName,
			  final String group,
			  final String addr,
			  final int absence) {
		if (hash.get(host) == null) {
			main.getShell()
			.getDisplay()
			.asyncExec(new RunnableAddMember(nickName, group, host, main.getSign()));
			hash.put(host, nickName);
	}
	
}	

	public final void sendMsg(final String user, 
			final String host, 
			final String msg,
			final boolean secret) throws IOException {
		// 送信
		super.sendMsg(host, msg, secret);
		// ログ出�?
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================\n");
        sb.append("To:" + user + "(" + host + ")\n");
        sb.append(currentTime() + "\n");
        sb.append("-------------------------------------\n");
        sb.append(msg + "\n\n");
        Logger.writeLog(sb.toString());
	}
	
	public final void removeMember(final String host) {
        if(hash.get(host)!=null && !main.getShell().isDisposed()){
            main.getShell().getDisplay().asyncExec(new RunnableRemoveMember(host));
            hash.remove(host);
        }
	}
    
    /**
     * メンバ�?を追�?��るスレ�?��
     */
    final class RunnableAddMember implements Runnable {
        
        private String nickName;
        private String group;
        private String host;
        private String signature;
        
        public RunnableAddMember(final String nickName,
        		final String group,
        		final String host,
        		final String signature){
            this.nickName = nickName;
            this.group    = group;
            this.host     = host;
            if (signature == null || signature.trim().length() == 0) {
            	this.signature= "这个人很懒什么也没留下!";
            } else {
            	this.signature= signature;
            }
        }
        
              
        
        public void run(){
            main.addMember(nickName,group,host, signature);
        }
    }
    
    /**
     * メンバ�?を削除するスレ�?��
     */
    final class RunnableRemoveMember implements Runnable {
        
        private String host;
        
        public RunnableRemoveMember(final String host){
            this.host = host;
        }
        
        public void run(){
            main.removeMember(host);
        }
        
    }
    
    /**
     * メ�?��ージを受信して�?��アログを表示するスレ�?��
     */
    class RunnableReceiveMessage implements Runnable {
        
        private String    user;
        private String    host;
        private String    message;
        private boolean   flag;
        private Messenger messenger;
        
        public RunnableReceiveMessage(final String host,
        		final String user,
        		final String message,
        		final boolean flag,
        		final Messenger messenger){
            this.host      = host;
            this.user      = user;
            this.message   = message;
            this.flag      = flag;
            this.messenger = messenger;
        }
        
        public void run(){
            ReceiveDialog dialog = new ReceiveDialog(main.getShell(),user,host,message,flag,messenger);
            dialog.open();
        }
    }
    
    /**
     * 開�??知�?��アログを表示するスレ�?���?
     */
    final class RunnableOpenMessage implements Runnable {
        
        private String host;
        private String user;
        
        public void setHost(final String h) {host = h;}
        public String getHost() {return host;}
        
        public RunnableOpenMessage(final String h,final String u){
            this.host = h;
            this.user = u;
        }
        
        public void run(){
            MessageBox dialog = new MessageBox(main.getShell(),SWT.OK);
            dialog.setText(this.user);
            // i18n.
			dialog.setMessage("("+currentTime()+")打开");
//            dialog.setMessage("開封されました\n("+new Date().toString()+")");
            dialog.open();
        }
    }

}
