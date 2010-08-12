package com.tan.util;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author dolphin
 *
 * 2010/07/14 9:57:13
 */
public final class DialogUtil {
	private Display display ;
	private Shell shell;
	private int i;
	
	public DialogUtil() {
		init();
	}
	
	public File chooseFile(final String text, 
			final String defaultFileName,
			final String[] extensions, final String defaultPath) {
		FileDialog dialog = new FileDialog(shell, SWT.SINGLE);
		
		dialog.setText(text);
		dialog.setFileName(defaultFileName);
		dialog.setFilterPath(defaultPath);
		
		dialog.setFilterNames(extensions);
		dialog.setFilterExtensions(extensions);
		String url = dialog.open();
		if (url == null) return null;
		dispose(i++);
		return new File(url);
	}
	
	public String chooseDirectory(final String message,
			final String text, final String path) {
		DirectoryDialog dialog = new DirectoryDialog(shell);
		dialog.setFilterPath(path);
		dialog.setMessage(message);
		dialog.setText(text);
		String url = dialog.open();
		dispose(i++);
		return url;
	}
	
	private void init() {
		if (display == null) {
			display = new Display();
			shell = new Shell(display);
		}
	}
	
	private void dispose(int i) {
		if (display != null && i >= 1) {
			display.dispose();
			display = null;
		}
	}
	
	public void forceDispose() {
		if (display != null) {
			display.dispose();
			display = null;
		}
	}
	
	public static void main(String[] args) {
		DialogUtil dialogUtil = new DialogUtil();
		
		File file = dialogUtil.chooseFile("text", "", new String[]{"*.xls"}, "c:\\tools\\");
		
		String directory = dialogUtil.chooseDirectory("message", "text", "c:\\tools\\");
		
		TanUtil.println(file);
		TanUtil.println(directory);
	}
}
