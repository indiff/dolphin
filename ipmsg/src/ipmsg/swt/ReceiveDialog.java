package ipmsg.swt;

import static util.Util.currentTime;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 受信�?��アログ�?
 * 
 * @author Naoki Takezoe
 */
public class ReceiveDialog {
    
    private Button open;
    private Text   text;
    private Shell  shell;
    private Composite composite;
    private Button response;
    private Button close;
    
    private String userName;
    //private String groupName;
    private String host;
    private String message;
    private boolean lock;
    private String date;
    private Messenger messenger;
    
    private Image image = ImageRegistry.getImage("icons/ipmsg16x16.gif");
    
	/**
	 * Constructor for ReceiveDialog.
	 * @param arg0
	 */
	public ReceiveDialog(final Shell shell,
						 final String userName,
						 final String host,
                         final String message,
                         final boolean lock,
                         final Messenger messenger) {
        this.shell = new Shell(shell,SWT.TITLE|SWT.RESIZE|SWT.MIN|SWT.CLOSE);
        this.shell.setImage(image);
        this.userName  = userName;
        //this.groupName = groupName;
        this.host      = host;
        this.message   = message;
        this.lock      = lock;
        this.date      = currentTime();
        this.messenger = messenger;
        initComponents();
	}
    
    /**
     * コンポ�?ネント�?初期�?
     */
    private void initComponents(){
        this.shell.setText("接受信息");
// 		TODO i18n. 
//      this.shell.setText("受信メ�?��ージ");
        GridLayout layout = new GridLayout();
        this.shell.setLayout(layout);
        
        Label label = new Label(shell,SWT.CENTER);
        label.setText(userName+"("+host+")\n"+date);
        GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(gridData1);
        
        composite = new Composite(shell,SWT.NULL);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setLayout(new GridLayout());
        
        if(lock){
            this.open = new Button(composite,SWT.NULL);
//     		TODO i18n.            
//          this.open.setText("開�?);
            this.open.setText("打开");
            GridData gridData2 = new GridData(GridData.FILL_BOTH);
            this.open.setLayoutData(gridData2);
            this.open.addSelectionListener(new OpenListener());
            this.shell.setDefaultButton(this.open);
        } else {
            this.text = new Text(composite,SWT.WRAP|SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY|SWT.BORDER);
            this.text.setText(message);
            GridData gridData3 = new GridData(GridData.FILL_BOTH);
            this.text.setLayoutData(gridData3);
        }
        
        Composite buttons = new Composite(shell,SWT.NULL);
        buttons.setLayout(new RowLayout());
        buttons.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
        
        close = new Button(buttons,SWT.NULL);
// 		TODO i18n.          
//        close.setText(" 閉じ�?");
        close.setText(" 关闭 ");
        close.addSelectionListener(new CloseListener());
        
        response = new Button(buttons,SWT.NULL);
// 		TODO i18n.           
//        response.setText(" �?信 ");
        response.setText(" 回复");
        response.addSelectionListener(new ResponseListener());
        
        shell.setSize(300,200);
        
        shell.setLocation(500, 500);
    }
    
    /**
     * 開封時の処�?
     */
    class OpenListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            open.dispose();
            text = new Text(composite,SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY|SWT.BORDER);
            text.setText(message);
            GridData gridData3 = new GridData(GridData.FILL_BOTH);
            text.setLayoutData(gridData3);
            composite.layout();
            
            // 開�??知
            try {
                messenger.readMsg(host);
            } catch(IOException ex){
                // 例外を報�?
                ex.printStackTrace();
                MessageBox mesBox = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
//         		TODO i18n.                  
//                mesBox.setText("例�?);
                mesBox.setText("异常");
                mesBox.setMessage(ex.toString());
                mesBox.open();
            }
        }
    }
    
    /**
     * 閉じる�?タンの処�?
     */
    class CloseListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            shell.dispose();
        }
    }
    
    /**
     * 返信ボタンの処�?
     */
    class ResponseListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            String msg = message;
            if(lock && text==null){
                msg = "";
            }
            SendDialog dialog = new SendDialog(shell,host,userName,msg,messenger);
            dialog.open();
        }
    }
    
    /**
     * �?��アログを表示します�?
     */
    public void open(){
        Display display = this.shell.getDisplay();
        this.shell.open();
        
        // ブロ�?��
        while (!this.shell.isDisposed ()){
            if (!display.readAndDispatch ()){
                display.sleep ();
            }
        }
    }
    
    /**
     * �?��ト用起動メソ�?��
     */
    public static void main(String[] args){
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setImage(ImageRegistry.getImage("icons/ipmsg16x16.gif"));
        ReceiveDialog dialog = new ReceiveDialog(shell,
        		"takezoe","TMS2","test",true,null);
        dialog.open();
    }

}
