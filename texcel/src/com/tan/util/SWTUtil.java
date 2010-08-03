package com.tan.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author dolphin
 *
 * 2010/07/13 17:25:57
 */
public final class SWTUtil {
	private SWTUtil() {
	}

	public static void main(String[] args) {
		chooseDirectory("c:\\", "title", "message");
	}

	public static String chooseFile(final String path, final String extension) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		FileDialog dialog = new FileDialog(shell, SWT.SINGLE);
		dialog.setFilterPath(path);
		dialog.setFilterNames(new String[] { extension });
		dialog.setFilterExtensions(new String[] { extension });
		String url = dialog.open();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}
		display.dispose();
		return url;
	}

	public static String chooseDirectory(final String path, final String title, final String message) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		DirectoryDialog dialog = new DirectoryDialog(shell);
		dialog.setText(title);
		dialog.setMessage(message);
		dialog.setFilterPath(path);
		String selectedDirectory = dialog.open();
		display.dispose();
		return selectedDirectory;
	}
}
