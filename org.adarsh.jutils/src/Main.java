import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton b1;
	
	final private String links = "D:\\Eclipse-Plugins\\links";
	final private String path = "D:\\Eclipse-Plugins\\myplugins\\eclipse\\plugins";
	final private String content = "path=D:/eclipse-plugins/myplugins";
	final private File linksDir = new File( links );
	final private File pluginDir = new File( path );
	
	public Main() {
		super();
		
		initComponent();
	}
	
	
	
	private void initComponent() {
		
		b1 = new JButton( "go" );
		
		this.setBounds( 300, 400, 200, 100);
		
		
		b1.addActionListener( new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				final String cp = System.getProperty( "java.class.path" ).toLowerCase();
				
				
				if ( cp.indexOf( "jutils" ) >= 0 && cp.endsWith( ".jar") ) {
					
					
					final File file = new File( linksDir , "mypllugins.txt" );
					if ( !file.exists() && file.length() <= 0 ) {
						if ( linksDir.exists() || linksDir.mkdirs() ) {
							write( content, file );
						}
					}						
					
					if ( pluginDir.exists() || pluginDir.mkdirs() ) {
						// copy self to the path, and named the file is jutils.jar
						final String command = "CMD /c COPY /Y \"" + new File( cp ).getAbsolutePath() + "\" \"" + path + "\\jutils.jar\"";
						System.out.println( command );
						
						try {
							Runtime.getRuntime().exec( command );
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} 
				}
			}
			
		});
		
		
		this.add( b1 );
		
		this.setVisible( true );
		
		this.setTitle( "Jutils create by tyj" );
		
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}



	protected void write(String content, File file) {
		OutputStream out = null;
		try {
			out = new FileOutputStream( file );
			out.write( content.getBytes() );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if ( out != null ) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				out = null;
			}
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main();
	}

}
