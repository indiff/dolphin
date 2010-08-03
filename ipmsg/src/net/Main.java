package net;

import ipmsg.swt.ImageRegistry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 接続情報を�?力するダイアログ�?
 * 
 * @author Naoki Takezoe
 */
public final  class Main {
    
    private Shell   shell;
//    private Display display;
    private Text    userName;
    private Text    groupName;
    private Text    signature;
    private Button  button;
    private String  user;
    private String  group;
    private String sign;
    private boolean returnFlag = false;
    private Image image = ImageRegistry.getImage("icons/ipmsg16x16.gif");
    
    
	/**
	 * Constructor for SetupDialog.
	 * @param arg0
	 */
	public Main(final Shell shell) {
        this.shell = new Shell(shell,SWT.TITLE|SWT.MIN|SWT.CLOSE|SWT.RESIZE);
        this.shell.setImage(image);
        initComponents();
	}
    
    /**
     * コンポ�?ネント�?初期�?
     */
    private void initComponents(){
        this.button = new Button(shell,SWT.NULL);
        this.button.setText("   登     录  ");
        this.button.addSelectionListener(new LoginListener());
        
        
        shell.pack();
        Point point = shell.getSize();
        point.x = 200;
        shell.setSize(point);
        
        shell.setLocation(500, 500);
    }
    
    /**
     * �?��アログの初期値を設定します�?
     * 
     * @param userName  ユーザ�?
     * @param groupName グループ名
     */
    public final void setInitValue(final String userName,final String groupName, final String sign){
        if(userName!=null){
            this.userName.setText(userName);
        }
        if(groupName!=null){
            this.groupName.setText(groupName);
        }
        if (sign != null) {
        	this.signature.setText(sign);
        }
    }
    
    /**
     * �?��アログを開きます�?
     */
    public boolean open(){
        this.returnFlag = false;
        
        Display display = this.shell.getDisplay();
        this.shell.open();
        
        // ブロ�?��
        while (!this.shell.isDisposed ()){
            if (!display.readAndDispatch ()){
                display.sleep ();
            }
        }
        return returnFlag;
    }
    
    /**
     * 入力されたユーザ名を取得します�?
     */
    public String getUserName(){
        return this.user;
    }
    /**
     * Get the signature.
     * @return
     */
    public String getSign() {return sign;}
    
    /**
     * 入力されたグループ名を取得します�?
     */
    public String getGroupName(){
        return this.group;
    }
    
    /**
     * ログインボタン押下時の処�?
     */
    class LoginListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            
        }
    }
    
    public static void main(String[] args) {
    	Display display = new Display();
    	Shell shell = new Shell(display);
    	Main dialog = new Main(shell);
    	dialog.open();
    }
}