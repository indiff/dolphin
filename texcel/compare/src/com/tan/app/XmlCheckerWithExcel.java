package com.tan.app;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.isEmpty;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.showInputDialog;
import static com.tan.util.TanUtil.warnln;
import static com.tan.util.XMLUtil.analyseRow;
import static com.tan.util.XMLUtil.getDocument;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;

import com.tan.bean.Dataset;
import com.tan.excel.util.StringUtil;
import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.Checker;
import com.tan.util.ExcelUtil;
import com.tan.util.PathConfig;

/**
 * 
 * @author dolphin
 * 
 *         2010/07/02 14:03:37
 */
public class XmlCheckerWithExcel extends AbstractChecker{
	
	private Checker  chk ;
	private List<File> excels ;
	
	private  StringBuilder fileNameInfo;
	
	XmlCheckerWithExcel () {
		chk = new Checker();
		fileNameInfo = new StringBuilder();
	}
	
	public String getFileNameInfo() {
		if (fileNameInfo != null && fileNameInfo.length() != 0) {
			return fileNameInfo.toString();
		}
		return "";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlCheckerWithExcel checker = new XmlCheckerWithExcel();
		
		/*** logger. **/
		println("Checking XML .......");
		
		PathConfig config = new PathConfig();
		
		String xsdsPath = config.getXsdsPath();
		if (StringUtil.isEmpty(xsdsPath))	
			xsdsPath = checker.getCurrentPath() + "xsds\\";
		String excelsPath = config.getExcelsPath();
		if (StringUtil.isEmpty(excelsPath))
			excelsPath = checker.getCurrentPath() + "excels\\";
		
		
		/*** Input the args by the dialog. **/
		xsdsPath = showInputDialog("请输入文件路径", xsdsPath);
		excelsPath = showInputDialog("请输入文件路径", excelsPath);
		
		/** Check the XML Path **/
		if (StringUtil.isEmpty(xsdsPath)) {
			warnln("路径为空, exit.");
			System.exit(0);
		}
		
		/** Trim the path. **/
		if (xsdsPath != null) xsdsPath = xsdsPath.trim();
		if (excelsPath != null) excelsPath = excelsPath.trim();

		File file = new File(xsdsPath);

		/** Check the xml File or The Excel's Directory **/
		if (!file.exists()) {
			warnln("文件不存在, exit.");
			System.exit(0);
		}
		
		/*** Get the 詳細設計書 Excels's lists . **/
		checker.initExcelList(new File(excelsPath));
		
		if (file.isFile()) { // check xml file.
			checker.checkXsdFile(file, OUTPUT_PATH);
		} else if (file.isDirectory()) { // check xml dir.
			checker.checkXsdDir(file, OUTPUT_PATH);
		}
		
		
		// println the checked filename info.
		warnln(checker.getFileNameInfo());
		println(checker.getFileNameInfo());
		
		// prompt info.
		warnln("App Over. See the CheckXmlWithExcel.log File.");
	}
	
	/**
	 * Check the xsd's directory.
	 * @param dir
	 * @param outputPath
	 */
	private void checkXsdDir(final File dir,String outputPath) {
		FileList list = new FileList(dir, ".xsd");
		List<File> xmls = list.getResults();

		if (isEmpty(xmls)) {
			println("XSD File not found!");
			warnln("XSD File not found!");
		}
		
		int size = xmls.size();
		
		/*** logger. **/
		println("XSD文件个数:" + size);
		warnln("XSD文件个数:" + size);
		
		ExcelUtil.sleep(DELAY_MILLIS);
		for (File e : xmls) {
			String fileInfo = e.toString() + " -------------------------------- XSD File";
			
			ExcelUtil.sleep(DELAY_MILLIS);
			
			/*** logger. **/
			println(fileInfo);
			warnln(fileInfo);
			
			/*** Check the xsd file. **/
			checkXsdFile(e, outputPath);
		}
	}
	
	/**
	 * Check the xsd file.
	 * And write the result to the output/*.xls.
	 * @param file
	 * @param outputPath
	 */
	private void checkXsdFile(final File file, String outputPath) {
		
		/** BaseUtil.checkFileName(e.getAbsolutePath());**/
		String info = BaseUtil.checkFileName(file.getAbsolutePath());
		if (info != null) {
			append(fileNameInfo, info);
		}
		
		if (!isXML(file)) {
			/*** logger. **/
			warnln("输入不是XML文件, exit.");
			System.exit(0);
		}
		
		/*** get the key (id). **/
		String id = getId(file.toString());
		File excel =  null;
		if (id != null) {
			excel = getExcel(id);
			
			/*** logger. **/
			if (excel == null){
				println(id + "--------------- Excel File Not found!");
				warnln(id + "--------------- Excel File Not found!");
			} else {
				println(excel + "--------------- Excel File");
				warnln(excel + "--------------- Excel File");
			}
		}
		
		Document doc = getDocument(file);
		Dataset[] datasets = analyseRow(doc, chk);
		
		/*** logger. **/
		println(outputPath + file.getName() + ".xls" + "--------------- Output File.");
		warnln(outputPath + file.getName() + ".xls" + "--------------- Output File.");
		
		/*** pint line. **/
		println();
		warnln();
		
		/*** Write the dataset the the excels. **/
		ExcelUtil.writeExcel(datasets, outputPath + id + '-' + file.getName() + ".xls", excel);
	}
	
	
	/**
	 * iterate the excels and find the excel which had the 'id'.
	 * @param id
	 * @return
	 */
	private File getExcel(String id) {
		Iterator<File> iter = excels.iterator();
		while(iter.hasNext()) {
			File file = iter.next();
			if(file.toString().contains(id)) {
				return file;
			}
		}
		return null;
	}
	
	/**
	 * find the key (id) by the regular expression.
	 * @param value
	 * @return
	 */
	private String getId(String value) {
		Pattern p = Pattern.compile("[A-Z]{2}[0-9]{4}");
		Matcher matcher = p.matcher(value);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
	
	/**
	 * Init the excel's list.
	 * @param dir
	 */
	private void initExcelList(final File dir) {
		if (dir == null || !dir.exists()
				|| dir.isFile()
				) return ;
		FileList list = new FileList(dir, ".xls");
		excels = list.getResults();
		
		/*** logger. **/
		if (excels != null ) {
			int size = excels.size();
			if (size > 0) {
				println("Excel文件个数:" + size);
				warnln("Excel文件个数:" + size);
			}
		}
	}	
}
