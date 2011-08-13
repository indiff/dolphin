package com.sysdeo.eclipse.tomcat.actions;

/*
 * (c) Copyright Sysdeo SA 2001, 2002.
 * All Rights Reserved.
 */

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.sysdeo.eclipse.tomcat.TomcatLauncherPlugin;

/**
 * 打开Tomcat配置文件目录
 * @author tanyuanji
 *
 */
public class PathActionDelegate implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	public static final String WINDOWS = "win32";
	public static final String LINUX = "linux";	
	private boolean isWindows;
	private boolean isLogger = true; // Debug.
	private String line;	
	private String systemBrowser = "explorer";
	
	public PathActionDelegate() {
		String os = System.getProperty("osgi.os");
		if (WINDOWS.equalsIgnoreCase(os)){
			systemBrowser = "explorer";
			isWindows = true;
		}
		else if (LINUX.equalsIgnoreCase(os)) {
			systemBrowser = "nautilus";
		}
		line = System.getProperty("line.separator", "\r\n");
	}
	
	/*
	 * @see IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/*
	 * @see IWorkbenchWindowActionDelegate#init(IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/*
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		final String tomcatDir = TomcatLauncherPlugin.getDefault().getTomcatDir();
		
		if(TomcatLauncherPlugin.checkTomcatSettingsAndWarn()) {				
			//TomcatLauncherPlugin.log(TomcatLauncherPlugin.getResourceString("msg.start"));
			command( tomcatDir + File.separatorChar +  "conf" + File.separatorChar + "server.xml" );
		}
	}
	
	
	private void command(String location) {
		StringBuffer command = new StringBuffer();
		Runtime runtime = Runtime.getRuntime();
		try {
			if ( isWindows ) {
				command.append(systemBrowser)
				.append(" /select, \"")
				.append(location)
				.append("\"");
			} else {
				command.append(systemBrowser)
				.append(" \"")
				.append(location)
				.append("\"");
			}
			runtime.exec(command.toString());
		} catch (IOException e) {
			MessageDialog.openError(window.getShell(),
					"PathActionDelegate", "Can't open " + location);
			e.printStackTrace();
		} finally {
			log( command );
			command = null;
			runtime = null;
			location = null;
		}
	}
	
	private void log( final Object msg ) {
		if ( isLogger ) {
			System.out.println( msg );
		}
	}

	/*
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {

	}

}

