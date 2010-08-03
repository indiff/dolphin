package com.tan.app;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.showInputDialog;
import static com.tan.util.TanUtil.warnln;
import static com.tan.util.XMLUtil.analyseRow;
import static com.tan.util.XMLUtil.getDocument;

import java.io.File;
import java.util.List;

import org.dom4j.Document;

import com.tan.bean.Dataset;
import com.tan.excel.util.StringUtil;
import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.Checker;
import com.tan.util.ExcelUtil;

/**
 * 
 * @author dolphin
 * 
 *         2010/07/02 14:03:37
 */
public class XmlChecker extends AbstractChecker{
	
	private Checker  chk = new Checker();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlChecker checker = new XmlChecker();
		println("Checking XML .......");
		
		String excelPath = showInputDialog("请输入文件路径", INPUT_PATH);
		
		/** Check the XML Path**/
		if (StringUtil.isEmpty(excelPath)) {
			warnln("路径为空, exit.");
			System.exit(0);
		}

		File file = new File(excelPath);
		
		/** Check the xml File or The Excel's Directory**/
		if (!file.exists()) {
			warnln("文件不存在, exit.");
			System.exit(0);
		}
		
		if (file.isFile()) { // check xml file.
			checker.checkExcelFile(file, OUTPUT_PATH);
		} else if (file.isDirectory()) { // check xml dir.
			checker.checkExcelDir(file, OUTPUT_PATH);
		}
		
		// prompt info.
		warnln("See the CheckXml.log File.");
	}

	private void checkExcelDir(final File dir, final String outputPath) {
		/** Get the list of the excels.**/
		FileList list = new FileList(dir, ".xsd");
		List<File> xmls = list.getResults();
		
		int size = 0;
		if (xmls == null || (size = xmls.size()) == 0) {
			BaseUtil.exitWithMessage("Dir is empty without xsd\t", dir);
		}
		
		println("XSD文件个数:" + size);
		warnln("XSD文件个数:" + size);
		
		ExcelUtil.sleep(DELAY_MILLIS);
		for (File e : xmls) {
			String fileInfo = e.toString() + " --------------------------------";
			ExcelUtil.sleep(DELAY_MILLIS);
			println(fileInfo);
			
//			warnln(fileInfo);
//			checkExcelFile(e, outputPath);
		}
	}

	private void checkExcelFile(final File file, final String outputPath) {
		if (!isXML(file)) {
			warnln("输入不是XML文件, exit.");
			System.exit(0);
		}
		Document doc = getDocument(file);
		Dataset[] datasets = analyseRow(doc, chk);
		
		if (datasets == null || datasets.length == 0) {
			warnln("No datasets.");
			return;
		}
		
		ExcelUtil.writeExcel(datasets, outputPath + file.getName() + ".xls");
	}
}
