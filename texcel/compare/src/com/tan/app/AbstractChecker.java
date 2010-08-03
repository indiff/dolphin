package com.tan.app;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.List;

import lombok.Getter;

import com.tan.io.FileList;
import com.tan.util.DialogUtil;
import com.tan.util.ExcelChecker;
import com.tan.util.ExcelUtil;

/**
 * 
 * @author dolphin
 * 
 *         2010/07/02 14:03:37
 */
public abstract class AbstractChecker {
	/**
	 * 延时.
	 */
	static int DELAY_MILLIS = 50;
	
	/**
	 * 输入路径.
	 */
	static String INPUT_PATH = "";

	/**
	 * 输出路径.
	 */	
	static String OUTPUT_PATH = "";
	
	private DialogUtil util;
	private @Getter String directory;
	
	AbstractChecker() {
		String currentPath = this.getCurrentPath();
		INPUT_PATH = currentPath + "excels";
		OUTPUT_PATH = currentPath + "output" + File.separatorChar;
	}
	
	AbstractChecker(final String dialogMsg, 
			  		final String dialogText,
			  		final String path
			  		) {
		this();
		util = new DialogUtil();
		directory = util.chooseDirectory(dialogMsg, dialogText, path);
		util.forceDispose();
	}
	
	@SuppressWarnings("unused")
	private void checkExcelDir(final File dir) {
		FileList list = new FileList(dir, ".xls");
		
		List<File> excels = list.getResults();
		
		println("文件个数:" + excels.size());
		warnln("文件个数:" + excels.size());
		ExcelUtil.sleep(DELAY_MILLIS);
		for (File e : excels) {
			String fileInfo = e.toString() + " --------------------------------";
			ExcelUtil.sleep(DELAY_MILLIS);
			println(fileInfo);
			warnln(fileInfo);
			checkExcelFile(e);
		}
	}

	private void checkExcelFile(final File file) {
		if (!isExcel(file)) {
			warnln("输入不是Excel文件, exit.");
			System.exit(0);
		}
		String information = ExcelChecker.checkExcelName(file.getName());
		println(information);
		warnln(information);
	}

	protected boolean isExcel(final File file) {
		return file.isFile() && file.getAbsolutePath().toLowerCase().endsWith(".xls");
	}
	
	protected boolean isXML(final File file) {
		String path = file.getName().toLowerCase();
		return file.isFile() && (path.endsWith(".xml") || path.endsWith(".xsd"));
	}	
	
	protected String getCurrentPath() {
//		return Thread.currentThread().getContextClassLoader().getResource("").getFile();
		return System.getProperty("user.dir") + File.separatorChar;
	}
}
