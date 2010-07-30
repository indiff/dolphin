package ipmsg.swt;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * メ�?��ージを返信するための�?��アログ�?
 */
public final class SendDialog {
    
    private Shell shell;
    
    private String message;
    private String host;
    private String user;
    
    private Text text;
    private Button send;
    private Button lock;
    private Messenger messenger;
    
	/**
	 * Constructor for SendDialog.
	 */
	public SendDialog(final Shell shell,
			final String host,
			final String user,
			final String message,
			final Messenger messenger) {
		this.shell = new Shell(shell,SWT.TITLE|SWT.RESIZE|SWT.MIN|SWT.CLOSE);
        this.message = message;
        this.host = host;
        this.messenger = messenger;
        this.user = user;
        
        initComponents();
	}
    
    /**
     * コンポ�?ネント�?初期�?
     */
    private void initComponents(){
    	// TODO i18n.
//        shell.setText(user + "への返信");
        shell.setText("回复" + user);
        
        GridLayout layout = new GridLayout();
        shell.setLayout(layout);
        shell.setImage(ImageRegistry.getImage("icons/ipmsg16x16.gif"));
        
        text = new Text(shell,SWT.V_SCROLL|SWT.WRAP|SWT.MULTI|SWT.BORDER);
        GridData gridData1 = new GridData(GridData.FILL_BOTH);
        text.setLayoutData(gridData1);
        String res = createResponseMessage(message);
        text.setText(res);
        text.setSelection(res.length());
        
        Composite composite = new Composite(shell,SWT.NULL);
        GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_END);
        composite.setLayoutData(gridData2);
        RowLayout layout2 = new RowLayout();
        composite.setLayout(layout2);
        
        send = new Button(composite,SWT.NULL);
        // TODO i18n.
//        send.setText("      送信      ");
        send.setText("      发送      ");
        send.addSelectionListener(new SendListener());
        
        lock = new Button(composite,SWT.CHECK);
        // TODO 18n.
//        lock.setText("封書");
        lock.setText("封装");
        
        shell.setSize(300,200);
        
        shell.setLocation(500, 500);
    }
    
    /**
     * �?��アログを表示します�?
     */
    public void open(){
        shell.open();
        
        // ブロ�?��
        while (!shell.isDisposed()){
            if (!shell.getDisplay().readAndDispatch ()){
                shell.getDisplay().sleep ();
            }
        }
    }
    
    /**
     * 返信用のメ�?��ージを作�?します�?
     */
    private final static String createResponseMessage(final String source){
        
        if(source==null || source.equals("")){
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
//        StringBuilder sb = new StringBuilder();
        sb.append("> ");
        for(int i=0;i<source.length();i++){
            char c = source.charAt(i);
            if(c=='\n'){
                sb.append(c);
                sb.append("> ");
            } else {
                sb.append(c);
            }
        }
        //sb.append("\n\n");
        return sb.toString();
    }
    
    /**
     * 送信ボタン押下時の処�?
     */
    class SendListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            String msg = text.getText();
            if(msg!=null && !msg.equals("")){
                try {
                    messenger.sendMsg(user,host,msg,lock.getSelection());
                } catch(IOException ex){
                    // 例外を報�?
                    ex.printStackTrace();
                    MessageBox mesBox = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
//                    TODO i18n .
//                    mesBox.setText("例�?);
                    mesBox.setText("异常");
                    mesBox.setMessage(ex.toString());
                    mesBox.open();
                }
            }
            shell.dispose();
        }
    }
    
    /**
     * �?��ト用起動メソ�?��
     */
    public static void main(String[] args){
        Display display = new Display();
        Shell shell = new Shell(display);
        SendDialog dialog = new SendDialog(shell,
    			"HOST",
    			"USER",
    			"MESSAGE",
    			null);
        dialog.open();
    }
}
