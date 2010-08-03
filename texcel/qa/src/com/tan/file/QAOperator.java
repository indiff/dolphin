package com.tan.file;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.warn;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.Qa;
import com.tan.qa.BaseUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.Leader;
import com.tan.util.TanUtil;
public class QAOperator {
	
	
	private @Setter Leader l ;
	/**
	 * initialize the QaOperator
	 * and the make the directory.
	 * @param currentPath
	 */
	public QAOperator(String currentPath) {
		mkdir(currentPath);
	}
	
	public QAOperator(String currentPath, final String format) {
		mkdir(currentPath, format);
	}
	
	
	public QAOperator(String currentPath, final String format, final String tail) {
		mkdir(currentPath, format, tail);
	}
	
	public QAOperator(final File current, final String format) {
		mkdir0(current, format);
	}	
	
	/**
	 * save the maked directory.
	 */
	private @Getter File dir ;
	private boolean hadDir;
	
	/**
	 * modify the excel and write to the new copy directory.
	 * @param f	
	 * @param number
	 * @return
	 */
	public Qa modifyExcel(final File f , final String number) {
		Workbook wb = ExcelUtil.getWorkbook(f);
		StringBuilder modifyInfo = new StringBuilder();
		
		Qa q = new Qa();
		
		if (wb == null) {
			warnln(f + "\t文件获取失败!");
		} else {
			Sheet qaSheet = wb.getSheet("QA管理表（入力シート）");
			
			if (qaSheet == null) {
				warnln("QA管理表（入力シート） Sheet 获取失败,查看是否存在 该Sheet");
				return null;
			}
			
			// ＱＡ	管理番号.
			Row qaInfoSheet = qaSheet.getRow(6);
			
			// 管理番号.
			Cell numCell = qaInfoSheet.getCell(0);
			String qaNumber = "QA_KNL_ES_" + number;
			numCell.setCellValue(qaNumber);
			q.setQaNumber(qaNumber);
			
			// 質問者氏名.
			Cell nameCell = qaInfoSheet.getCell(4);
			String name = nameCell.getStringCellValue();
			q.setUserName(name);
			
			/*** 質問者氏名 set start.**/
			String checkName = BaseUtil.checkName(name);
			
			if (checkName != null && checkName.length() > 0) {
				TanUtil.warnln(checkName);
				TanUtil.println(checkName);
			}
			
			name = BaseUtil.filter(name);
			String leader =  "";
			
			if (l != null)
				leader = l.getLeader(name);
			
//			String leader = ConfigUtil.getLeader(name);
			/*** 質問者氏名 set end.**/
			
			q.setLeader(leader);
			append(modifyInfo, leader);
			append(modifyInfo, "\t" , name);
			
			// プログラムID.
			Cell programIdCell = qaInfoSheet.getCell(7);
			String programId = programIdCell.getStringCellValue();
			append(modifyInfo, "\t" , programId);
			q.setProgramId(programId);
			
			
//			warnln("QA_KNL_ES_" + number);
			
			append(modifyInfo, "\t" , "QA_KNL_ES_" + number);
			
			
			if (hadDirectory()) {
				File dest = new File(dir, qaNumber + '_' +  findFileName(f.getName()) + ".xls");
				ExcelUtil.writeExcel(wb, dest);
				warn(dest);
			} else {
				warn("存放Excel目录未创建!");
			}
			
		}
		
		return q;
	}

	/**
	 * make the directory 
	 * in the current path.
	 * by the date which format is yyyyMMddhhmm.
	 * @param currentPath
	 */
	private void mkdir(final String currentPath) {
		mkdir(currentPath, "yyyyMMddhhmm");
	}
	
	private void mkdir(final String currentPath, final String format) {
		String dirName = 
			formatDate(format);
		
		File mkdir = new File(currentPath + File.separatorChar + dirName);
		boolean right = false;
		
		if (mkdir.exists() &&
				mkdir.isDirectory()) {
			right = true;
		}
		
		if (mkdir.isFile()) {
			mkdir.delete();
		}
		
		if (!mkdir.exists()) {
			right = mkdir.mkdir();
		}
		
		if (right) dir = mkdir;
		
		hadDir = true;
	}
	
	public void mkdir(final File current, final String ... formats) {
		if (formats == null || formats.length == 0) return;
		
		String dirName = 
			formatDate(formats[0]);
		
		if (formats.length == 2)
			dirName = dirName + formats[1];
		
		File mkdir = new File(current,  dirName);
		
		boolean right = false;
		
		if (mkdir.exists() &&
				mkdir.isDirectory()) {
			right = true;
		}
		
		if (mkdir.isFile()) {
			mkdir.delete();
		}
		
		if (!mkdir.exists()) {
			right = mkdir.mkdir();
		}
		
		if (right) dir = mkdir;
		
		hadDir = true;
		
	}
	
	public void mkdir(final String current, final String ... formats) {
		mkdir(new File(current), formats);
	}
	
	private void mkdir0(final File current, final String format) {
		String dirName = 
			formatDate(format);
		File mkdir = new File(current,  dirName + "まで");
		
		boolean right = false;
		
		if (mkdir.exists() &&
				mkdir.isDirectory()) {
			right = true;
		}
		
		if (mkdir.isFile()) {
			mkdir.delete();
		}
		
		if (!mkdir.exists()) {
			right = mkdir.mkdir();
		}
		
		if (right) dir = mkdir;
		
		hadDir = true;
	}		

	/**
	 * Format the date by the format string.
	 * @param format
	 * @return
	 */
	private String formatDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}
	
	public static void main(String[] args) {
		
//		QAOperator operator = new QAOperator("D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後");
//		
//		
//		
//		if (operator.hasCopyDir()) {
//			println("文件存在");
//		}
//		
//		
//		if (operator.hasCopyDir()) {
//			println("文件存在");
//		}
//		
//		
//		if (operator.hasCopyDir()) {
//			println("文件存在");
//		}
		
		String value = "QA_EA0160_貸付金額等変更目録印刷_20100707_0003.xls";
		
		String number = "QA_KNL_ES_0001";
		
		System.out.println(number + '_' +  findFileName(value));
		System.out.println("Find file name:" + findFileName(value));
	}

	/**
	 * get the file name .
	 * @param value
	 * @return
	 */
	private static String findFileName(String value) {
		Pattern pattern = Pattern.compile(
				"QA_[A-Z]{2}\\d{4}_(.*)_\\d{8,}_\\d{4}",
				Pattern.CASE_INSENSITIVE);
//		Pattern pattern = Pattern.compile("_([^_\\w\\d]+)_");
		
		
		Matcher matcher = pattern.matcher(value);
		
		if (matcher.find()) {
			 return matcher.group(1);
		} else {
			pattern = Pattern.compile("QA_KNL_ES_\\d{4}_(.+)",
					Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(value);
			if (matcher.find()) {
				return matcher.group(1);
			}
		}
		return "not-found-name";
	}

	/**
	 * 判断是否创建了目录 .
	 * @return
	 */
	private boolean hadDirectory() {
		return hadDir;
	}
}