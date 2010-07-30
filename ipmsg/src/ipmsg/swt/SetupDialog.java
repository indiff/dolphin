package ipmsg.swt;

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
public final  class SetupDialog {
    
    private Shell   shell;
//    private Display display;
    private Text    userName;
    private Text    groupName;
    private Button  button;
    private String  user;
    private String  group;
    private boolean returnFlag = false;
    private Image image = ImageRegistry.getImage("icons/ipmsg16x16.gif");
    
	/**
	 * Constructor for SetupDialog.
	 * @param arg0
	 */
	public SetupDialog(final Shell shell) {
        this.shell = new Shell(shell,SWT.TITLE|SWT.MIN|SWT.CLOSE);
        this.shell.setImage(image);
        initComponents();
	}
    
    /**
     * コンポ�?ネント�?初期�?
     */
    private void initComponents(){
// 		TODO i18n.
//      shell.setText("ログイン");
        shell.setText("登录");
        
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        shell.setLayout(layout);

        Label label1 = new Label(shell,SWT.NULL);
//         TODO i18n.
//        label1.setText("ユーザ�?);
        label1.setText("用户名");
        this.userName = new Text(shell,SWT.BORDER);
        this.userName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label label2 = new Label(shell,SWT.NULL);
//         TODO i18n.
//        label2.setText("グループ名");
        label2.setText("组名");
        this.groupName = new Text(shell,SWT.BORDER);
        this.groupName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_END);     
        gridData.horizontalSpan = 2;
        
        this.button = new Button(shell,SWT.NULL);
//         TODO i18n.
//        this.button.setText("ログイン");
        this.button.setText("登录");
        this.button.addSelectionListener(new LoginListener());
        this.button.setLayoutData(gridData);
        
        userName.setFocus();
        shell.setDefaultButton(button);
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
    public final void setInitValue(final String userName,final String groupName){
        if(userName!=null){
            this.userName.setText(userName);
        }
        if(groupName!=null){
            this.groupName.setText(groupName);
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
            // 入力チェ�?��
            if(userName.getText()==null || userName.getText().equals("")){
                MessageBox mesBox = new MessageBox(shell,SWT.OK);
//                mesBox.setText("エラー");
//                mesBox.setMessage("ユーザ名を入力してください");
// i18n.                
                mesBox.setText("错误");
                mesBox.setMessage("请输入用户名和组名");
                mesBox.open();
                userName.setFocus();
                return;
            }
            returnFlag = true;
            user  = userName.getText();
            group = groupName.getText();
            shell.dispose();
        }
    }
}