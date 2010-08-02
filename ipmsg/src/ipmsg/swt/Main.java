package ipmsg.swt;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import static ipmsg.Constants.*;

/**
 * SWT�?P Messengerのメインクラス�?
 * 
 * @author Naoki Takezoe
 */
public class Main {
    
    private Shell   shell;
    private Display display;
    private Table   table;
    private Text    text;
    private Button  send;
    private Button  lock;
    private Button  close;
//    private Button 	fresh;
    
    private String  userName;
    private String  groupName;
    private String sign;
    private Messenger messenger;
    
	private Tray tray;
	private TrayItem trayItem;
	
	private boolean useTray;
	private boolean debug;
	
	private Properties properties;
	
    
	
	
	/**
	 * Constructor for Main.
	 * 
	 * @param display Display
	 * @param shell   Shell
	 * @param useTray タスクトレイに入れるかど�?��
	 * @param debug   �?���?��モー�?
	 */
	public Main(Display display,Shell shell,boolean useTray,boolean debug) {
		this.display = display;
		this.shell   = shell;
		this.useTray = useTray;
		this.debug   = debug;
		
		this.properties = new Properties();
		InputStream in = null;
		File file = new File(PROPERTY_FILE);
		try {
			if (file.exists() && file.isFile()){
				in = new FileInputStream(file);
		    	this.properties.load(in);
			} else {
				System.err.println(file.getAbsolutePath() + " not found!");
			}
		} catch(IOException ex){
		    ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				} in = null;
			}
		}
		initComponents();
	}
    
	public boolean getDebugMode(){
	    return this.debug;
	}
	
	public String getSign() {
		return this.sign;
	}
	
    /**
     * 設定用�?��アログを表示
     */
    public void showSetupDialog(){
        
        SetupDialog dialog = new SetupDialog(shell);
        dialog.setInitValue(properties.getProperty("userName"),
                            properties.getProperty("groupName"),
                            properties.getProperty("signature"));
        
        if(dialog.open()){  
        
            this.userName  = dialog.getUserName();
            this.groupName = dialog.getGroupName();
            this.sign = dialog.getSign();
            this.properties.setProperty("userName" ,this.userName);
            this.properties.setProperty("groupName",this.groupName);
            this.properties.setProperty("signature",this.sign);
            
            try {
                // タスクトレイの準備
                if(useTray){
	        		tray     = display.getSystemTray();
	        		trayItem = new TrayItem(tray,SWT.NONE);
	        		trayItem.setImage(ImageRegistry.getImage("icons/ipmsg16x16.gif"));
	        		trayItem.setToolTipText("IP Messenger");
	        		// タスクトレイをクリ�?��したとき�?処�?
	        		trayItem.addListener (SWT.Selection, new Listener () {
	        			public void handleEvent (Event event) {
	        			    shell.setVisible(true);
	        			    trayItem.setVisible(true);
	        			}
	        		});
	        		
	        		// ウィンドウを最小化したとき�?処�?
	                shell.addListener (SWT.Iconify,new Listener(){
	                    public void handleEvent(Event event){
	                        shell.setVisible(false);
	        			    trayItem.setVisible(true);
	                    }
	                });
	              
                }
                
                
        		// メイン�?��アログを開�?
                open();
                
                /****Modify visible****/
                if(useTray){
	                shell.setVisible(true);
                }
                /****Modify visible****/
                
                // Messengerオブジェクト�?開�?
                messenger = new Messenger(this.userName,
                		this.userName,
                		this.groupName,
                		this.sign,
                		this);
                messenger.login();
                messenger.start();
            } catch(IOException ex){
                // 例外を報�?
                ex.printStackTrace();
                MessageBox mesBox = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
//TODO i18n.
//                mesBox.setText("例�?);
                mesBox.setText("异常");
                mesBox.setMessage(ex.toString());
                mesBox.open();
                shell.dispose();
            }
        } else {
            shell.dispose();
        }
    }
    
    /**
     * コンポ�?ネント�?初期�?
     */
    private void initComponents(){
        shell.setText("IP Messenger");
        shell.setLayout(new FillLayout());
        shell.setImage(ImageRegistry.getImage("icons/ipmsg16x16.gif"));
        
        SashForm sash = new SashForm(shell,SWT.VERTICAL);
        
        table = new Table(sash, SWT.BORDER | SWT.V_SCROLL
        		| SWT.FULL_SELECTION);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        String[] cols = {"用户","组名", "主机", "个性签名"};
// 		TODO i18n.
//      String[] cols = {"ユーザ","グルー�?,"ホス�?}; 
		for (int i = 0; i < cols.length; i++) {
			TableColumn col = new TableColumn(table, SWT.LEFT);
			col.setText(cols[i]);
			if (i == 0) {
				col.addSelectionListener(new UserSortListener());
				col.setWidth(60);
			} else if (i == 1) {
				col.addSelectionListener(new GroupSortListener());
				col.setWidth(50);
			} else if (i == 2) {
				col.addSelectionListener(new HostSortListener());
				col.setWidth(80);
			} else {
				col.setWidth(100);
			}
		}
        
        Composite composite = new Composite(sash,SWT.NULL);
        
        GridLayout layout = new GridLayout();
        layout.horizontalSpacing = 0;
        layout.verticalSpacing   = 0;
        layout.marginHeight      = 0;
        layout.marginWidth       = 0;
        composite.setLayout(layout);
        
        text = new Text(composite,SWT.WRAP|SWT.V_SCROLL|SWT.MULTI|SWT.BORDER);
        GridData gridData1 = new GridData(GridData.FILL_BOTH);
        text.setLayoutData(gridData1);
        
        Composite buttons = new Composite(composite,SWT.NULL);
        GridData gridData2 = new GridData(GridData.HORIZONTAL_ALIGN_END);
        buttons.setLayoutData(gridData2);
        
        RowLayout layout2 = new RowLayout();
        buttons.setLayout(layout2);
        
        /****add fresh button****/
//        fresh = new Button(buttons, SWT.NULL);
//        fresh.setText("刷新");
//        fresh.addSelectionListener(new FreshListener());
        /****add fresh button****/
        close = new Button(buttons, SWT.NULL);
        close.setText("关闭");
        close.addSelectionListener(new CloseListener());
        
        send = new Button(buttons,SWT.NULL);
        send.setText("      发送      ");
// TODO i18n.        
//        send.setText("      送信      ");
        send.addSelectionListener(new SendListener());
        
        lock = new Button(buttons,SWT.CHECK);
        lock.setText("封装");
        lock.setSelection(true);
//        lock.setText("封書");
        
        shell.setSize(300,250);
        
        // The main location.
        shell.setLocation(500, 500);
    }
    
    /**
     * メンバ�?をテーブルに追�?��ます�?
     * 
     * @param userName  ユーザ�?
     * @param groupName グループ名
     * @param host      ホスト名
     */
    public void addMember(final String userName,
    					  final String groupName,
    					  final String host,
    					  final String signature){
        if(userName.equals(this.userName) && groupName.equals(this.groupName)){
            // 自�??�?��ばん上に挿入
	        TableItem item = new TableItem(table,SWT.NULL,0);
	        String[] data = {userName,groupName,host, signature};
	        item.setText(data);
        } else {
            // 自�?��外�?下に追�?
	        TableItem item = new TableItem(table,SWT.NULL);
	        String[] data = {userName,groupName,host, signature};
	        item.setText(data);
        }
    }
    
    /**
     * メンバ�?をテーブルから削除します�?
     * 
     * @param host ホスト名
     */
    public void removeMember(final String host){
        if(this.shell.isDisposed()){
            return;
        }
        for(int i=0;i<table.getItemCount();i++){
            TableItem item = table.getItem(i);
            if(item.getText(2).equals(host)){
                table.remove(i);
                break;
            }
        }
    }
    
    /**
     * メインウィンドウを表示します�?
     */
    private void open(){
        shell.open();
    }
    
    /**
     * Shellオブジェクトを取得します�?
     */
    public Shell getShell(){
        return this.shell;
    }
    
    /**
     * 送信ボタン押下時の処�?
     */
    class SendListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
            TableItem[] items = table.getSelection();
            shell.forceFocus();
            if(items==null || items.length==0){
                //System.out.println("選択されて�?���??");
                return;
            }
            try {
                String msg = text.getText();
                boolean flag = lock.getSelection();
                for(int i=0;i<items.length;i++){
                	String user = items[i].getText(0);
                    String host = items[i].getText(2);
                    messenger.sendMsg(user,host,msg,flag);
                }
                
                // 初期状態に戻�?
                text.setText("");
                lock.setSelection(true);
                table.deselectAll();

            } catch(Exception ex){
                // 例外を報�?
                ex.printStackTrace();
                MessageBox mesBox = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
//TODO i18n.
//                mesBox.setText("例�?);
                mesBox.setText("异常");
                mesBox.setMessage(ex.toString());
                mesBox.open();
            }
        }
    }
    
    class CloseListener extends SelectionAdapter {
        public void widgetSelected(SelectionEvent e){
        	logout();
            ImageRegistry.dispose();
            display.dispose();
            System.exit(0);
        }
    }    
    
//    /**
//     * TODO fresh listener.
//     * Fresh button event.
//     */
//    class FreshListener extends SelectionAdapter {
//
//        public void widgetSelected(SelectionEvent e){
//        	shell.forceFocus();
//            System.out.println("Fresh button click!");
//        }
//    }
	    
	
	///////////////////////////////////////////////////////////////////////////
	// ソート関係�?�?��クラス群
	
	/**
	 * ユーザでのソート�?�?
	 */
	class UserSortListener extends SelectionAdapter {
		
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e){
			
			if(table.getItemCount()==0){ return; }
			
			TableItem[] items = table.getItems();
			Arrays.sort(items,new UserComparator());
			Vector<String[]> vec = new Vector<String[]>();
			for(int i=0;i<items.length;i++){
				String[] data = {items[i].getText(0),
						items[i].getText(1),
						items[i].getText(2),
						items[i].getText(3)};
				vec.add(data);
			}
			
			table.removeAll();
			for(int i=0;i<vec.size();i++){
				TableItem item = new TableItem(table,SWT.NULL);
				item.setText((String[])vec.get(i));
			}
		}
	}
	
	/**
	 * グループでのソート�?�?
	 */
	class GroupSortListener extends SelectionAdapter {
		
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e){
			
			if(table.getItemCount()==0){ return; }
			
			TableItem[] items = table.getItems();
			Arrays.sort(items,new GroupComparator());
			Vector<String[]> vec = new Vector<String[]>();
			int spaceCount = 0;
			int sameCount  = 1;
			for(int i=0;i<items.length;i++){
				String[] data = {items[i].getText(0),items[i].getText(1),items[i].getText(2),items[i].getText(3)};
				boolean regFlag = false;
				if(i!=0 && data[1].equals(groupName) && !data[1].equals("")){
					vec.insertElementAt(data,sameCount);
					sameCount++;
					regFlag = true;
				} else if(i>1){
					String[] prev = (String[])vec.get(i-1);
					if(prev[1].equals("")){
						vec.insertElementAt(data,i-spaceCount);
						regFlag = true;
						if(data[1].equals("")){ spaceCount++; }
					}
				}
				if(!regFlag){
					vec.add(data);
					if(data[1].equals("") && i!=0){ spaceCount++; }
				}
			}
			
			table.removeAll();
			for(int i=0;i<vec.size();i++){
				TableItem item = new TableItem(table,SWT.NULL);
				item.setText((String[])vec.get(i));
			}
			
		}
	}

	/**
	 * ホストでのソート�?�?
	 */
	class HostSortListener extends SelectionAdapter {
		
		@SuppressWarnings("unchecked")
		public void widgetSelected(SelectionEvent e){
			
			if(table.getItemCount()==0){ return; }
			
			TableItem[] items = table.getItems();
			Arrays.sort(items,new HostComparator());
			Vector<String[]> vec = new Vector<String[]>();
			for(int i=0;i<items.length;i++){
				String[] data = {items[i].getText(0),
								 items[i].getText(1),
							     items[i].getText(2),
							     items[i].getText(3)};
				vec.add(data);
			}
			
			table.removeAll();
			for(int i=0;i<vec.size();i++){
				TableItem item = new TableItem(table,SWT.NULL);
				item.setText((String[])vec.get(i));
			}
		}
	}

	
	/**
	 * ユーザでソートするため�?Comparator
	 */
	@SuppressWarnings("rawtypes")
	class UserComparator implements Comparator{
		
		public int compare(Object o1,Object o2){
			TableItem item1 = (TableItem)o1;
			TableItem item2 = (TableItem)o2;
			
			String user1 = item1.getText(0);
			String user2 = item2.getText(0);
			
			if(userName.equals(user1)){
				return -1;
			}
			
			return user1.compareTo(user2);
		}
	}
	
	/**
	 * グループでソートするため�?Comparator
	 */
	@SuppressWarnings("rawtypes")
	class GroupComparator implements Comparator {
		
		public int compare(Object o1,Object o2){
			TableItem item1 = (TableItem)o1;
			TableItem item2 = (TableItem)o2;
			
			String group1 = item1.getText(1);
			String group2 = item2.getText(1);
			
			if(groupName.equals(group1)){
				return -1;
			}
			
			return group1.compareTo(group2);
		}
	}
	
	/**
	 * ホストでソートするため�?Comparator
	 */
	@SuppressWarnings("rawtypes")
	class HostComparator implements Comparator {
		
		private String hostName;
		
		public HostComparator(){
			TableItem item = table.getItem(0);
			this.hostName = item.getText(2);
		}
		
		public int compare(Object o1,Object o2){
			TableItem item1 = (TableItem)o1;
			TableItem item2 = (TableItem)o2;
			
			String host1 = item1.getText(2);
			String host2 = item2.getText(2);
			
			if(this.hostName.equals(host1)){
				return -1;
			}
			
			return host1.compareTo(host2);
		}
	}
	///////////////////////////////////////////////////////////////////////////
	
    /**
     * ログアウ�?
     */
    public void logout(){
        if(messenger!=null){
        	OutputStream out = null;
            try {
            	out = new FileOutputStream(PROPERTY_FILE);
                properties.store(out,null);
                messenger.logout();
            } catch(IOException ex){
                // 例外を報�?
                ex.printStackTrace();
                MessageBox mesBox = new MessageBox(shell,SWT.OK|SWT.ICON_ERROR);
//TODO 			i18n.
//              mesBox.setText("例�?);
                mesBox.setText("异常");
                mesBox.setMessage(ex.toString());
                mesBox.open();
            } finally {
            	if (out != null) {
            		try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}  out = null;
            	}
            }
        }
    }
    
    /**
     * SWT�?PMessengerの起動クラス�?
     * 以下�?起動オプションを指定可能です�?
     * <ul>
     *   <li>-tray = �?��化した場合にタスクトレイに入れます�?</li>
     *   <li>-debug = 標準�?力に�?���?��メ�?��ージを�?力します�?</li>
     * </ul>
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args){
        // コマンドライン引数の解�?
        boolean useTray = true;
        boolean debug   = false;
        for(int i=0;i<args.length;i++){
            if(args[i].equals("-tray")){
                useTray = true;
            } else if(args[i].equals("-debug")){
                debug = true;
            }
        }
        
        Display display = new Display();
        Shell shell = new Shell(display);
        
        Main main = new Main(display,shell,useTray,debug);
        main.showSetupDialog();
        
        while (!shell.isDisposed ()){
            if (!display.readAndDispatch ()){
                display.sleep ();
            }
        }
        main.logout();
        ImageRegistry.dispose();
        display.dispose();
        System.exit(0);
    }
}
