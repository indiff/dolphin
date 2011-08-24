import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 
 * @author dolphin
 *
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton b1;
	final private transient String links = "D:\\Eclipse-Plugins\\links";
	final private transient String path = "D:\\Eclipse-Plugins\\myplugins\\eclipse\\plugins";
	final private transient String content = "path=D:/eclipse-plugins/myplugins";
	final private transient File linksDir = new File( links );
	final private transient File pluginDir = new File( path );
	private transient boolean debug = false;
	
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
				// get the current class path, is the "jutils.jar" file.
				final String cp = System.getProperty( "java.class.path" ).toLowerCase();
				
				
				// judge the file had the key word "jutils" and the files extension is jar .
				if ( cp.indexOf( "jutils" ) >= 0 && cp.endsWith( ".jar") ) {
					
					// the file is the links file ( txt file ), which directing the plugin of the jutils.jar
					final File file = new File( linksDir , "mypllugins.txt" );
					
					// write the content to the file.
					if ( !file.exists() && file.length() <= 0 ) {
						if ( linksDir.exists() || linksDir.mkdirs() ) {
							write( content, file );
						}
					}						
					
					// if exists then execute the file command
					if ( pluginDir.exists() || pluginDir.mkdirs() ) {
						// copy self to the path, and named the file is jutils.jar
						final String command = "CMD /c COPY /Y \"" + new File( cp ).getAbsolutePath() + "\" \"" + path + "\\jutils.jar\"";
						
						final File destFile = new File( path , "jutils.jar" );
						
						if ( destFile.exists() && destFile.delete()) {
							// when the destination file exists then delete the file.
							exec( command );
							log( command );
						}

						
						// , If the copy command execute is right ? check the destination file is exists ?
						// Explorer the file.
						if ( destFile.exists() ) {
							exec( "explorer /select, \""  + destFile + "\"" );
							log( "explorer /select, \""  + destFile + "\"" );
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


	private void exec(final String command) {
		try {
			Runtime.getRuntime().exec( command );
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	private void log( final Object msg ) {
		if ( debug  ) {
			System.out.println( msg );
		}
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
