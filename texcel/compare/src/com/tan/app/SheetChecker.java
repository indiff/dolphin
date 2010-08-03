package com.tan.app;

import static com.tan.util.ExcelUtil.getCheckSheetInfo;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warn;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.List;

import com.tan.excel.util.StringUtil;
import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.PathConfig;

/**
 * 
 * @author dolphin
 * 
 *         2010/07/02 14:03:37
 */
public class SheetChecker extends AbstractChecker{
	/**
	 * 
	 * @param msg	Dialog's message.
	 * @param txt	Dialog's text.
	 * @param path	Dialog's default path.
	 */
	SheetChecker(final String msg, 
			     final String txt,
			     final String path){
		super(msg, txt, path);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PathConfig config = new PathConfig();
		String oldPath = config.getOldPath();
		
		if (StringUtil.isEmpty(oldPath)) {
			oldPath = AbstractChecker.INPUT_PATH;
		}
		SheetChecker checker = new SheetChecker(
				"选择Excel目录",
				"CheckExcelSheetName",
				oldPath);
		String excelPath = checker.getDirectory();
		
		
		println("Checking Sheet Name .......");
		/** Check the Excel Path**/
		if (StringUtil.isEmpty(excelPath)) {
			warnln("路径为空, exit.");
			System.exit(0);
		}

		File file = new File(excelPath);
		
		/** Check the Excel File or The Excel's Directory**/
		if (!file.exists()) {
			warnln("文件不存在, exit.");
			System.exit(0);
		}
		
		if (file.isFile()) { // check excel file.
			checker.checkExcelFile(file);
		} else if (file.isDirectory()) { // check excel dir.
			checker.checkExcelDir(file);
		}
		
		// prompt info.
		warnln("See the CheckSheetInfo.log File.");
	}

	private void checkExcelDir(final File dir) {
		FileList list = new FileList(dir, ".xls");
		List<File> excels = list.getResults();
		
		int size = 0;
		if (excels == null || (size = excels.size()) == 0) {
			BaseUtil.exitWithMessage("Dir is empty with xls\t", dir);
		}
		
		println("Excel文件个数:" + size);
		warnln("Excel文件个数:" + size);
		
		int index = 0;
		for (File e : excels) {
			ExcelUtil.sleep(DELAY_MILLIS);
			warn(BaseUtil.percent(++index, size));
			checkExcelFile(e);
		}
	}

	private void checkExcelFile(final File file) {
		if (!isExcel(file)) {
			warnln("输入不是Excel文件, exit.");
			System.exit(0);
		}
		String information = getCheckSheetInfo(file);
		if (information != null && information.length() > 0)
			println(file + "\r\n" + information);
	}
}
