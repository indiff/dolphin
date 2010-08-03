package com.tan.analyse.app;


import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warn;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.QaInfo;
import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.DialogUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.Leader0;
import com.tan.util.StringUtil;
import com.tan.util.TanUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/13 17:18:52
 */
public class Main {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	
	private static Leader0 LEADER = new Leader0();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO delete.
//		currentPath = "D:\\zsky\\01-管理域\\02-質問票\\02-翻訳後";
		DialogUtil dialogUtil = new DialogUtil();
		
		// テーブル定義 dir.
		String directory = dialogUtil.chooseDirectory(
				"Select	QA Excel Directory",
				"QA Excels", 
				LEADER.getOldPath());
//				ConfigUtil.getOldPath());
		
		if (directory == null || directory.trim().length() == 0) exitWithMessage("路径为空");
		
		File dir = new File(directory);
		
		FileList list = new FileList(dir, ".xls");
		
		List<File> results = list.getResults();
		
		if (TanUtil.isEmpty(results)) exitWithMessage("未找到Excel文件!");
		
		int size = results.size();
		println("文件个数:" + size);
		warnln("文件个数:" + size);
		
		List<QaInfo> qaInfos = getQaInfos(results, size, false);
		
		writeQaInfos(qaInfos, dir.getName());
	}
	
	
	@SuppressWarnings("unchecked")
	private static void writeQaInfos(final List<QaInfo> results,
			String name) {
		Collections.sort(results);
		if (name == null) name = "";
		
		String currentPath = BaseUtil.getCurrentPath();
		Workbook wb = ExcelUtil.getWorkbook(currentPath + File.separatorChar + "sample.xls");
		if (wb == null) exitWithMessage("Not Found sample.xls");
		
		Sheet sheet = wb.getSheetAt(0);
		int index = 0;
		
		for (Iterator<QaInfo> iter = results.iterator(); iter.hasNext(); ) {
			index++;
			QaInfo info = iter.next();
			
			Row row = sheet.createRow(index);
			
			for (int i = 0; i < 13; i++) {
				Cell cell = row.createCell(i);
				if (i == 0) {
					// No.
					cell.setCellValue(String.valueOf(index));
					continue;
				}
				setValue(i, cell, info);
			}
		}
		
		ExcelUtil.writeExcel(wb, currentPath + File.separatorChar + name + "output" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls");
		warnln("Write Successful!");
	}


	final private static void setValue(final int i, final Cell cell,final QaInfo info) {
		if (info == null) return;
		switch (i) {
		case 1 : 
			// QA番号.
			cell.setCellValue(info.getQaNum());
			break;
		case 2 : 
			// 機能ID.
			cell.setCellValue(info.getProgramId());
			break;	
		case 3 : 
			// 機能名.
			cell.setCellValue(info.getProgramName());
			break;				
		case 4 : 
			// 問題.
			cell.setCellValue(info.getAskContent());
			break;				
		case 5 : 
			// 回答.
			cell.setCellValue(info.getAnswerContent());
			break;				
		case 6 : 
			// 提出者.
			cell.setCellValue(info.getName());
			break;				
		case 7 : 
			// 回答者.
			cell.setCellValue(info.getAnswerName());
			break;				
		case 8 : 
			// 提出日.
			Date d = info.getHappendDate();
			if (d != null)
				cell.setCellValue(format.format(d));
			break;				
		case 9 : 
			// 予定回答日.
			Date date = info.getDueTime();
			if (date != null)
				cell.setCellValue(format.format(date));
			break;				
		case 10 : 
			// 発生工程.
			cell.setCellValue(info.getHappendProject());
			break;				
		case 11 : 
			// ○/×.
			String content = info.getAnswerContent();
			if (content == null || content.trim().length() == 0)
				cell.setCellValue("×");
			else 
				cell.setCellValue("○");
			break;				
		case 12 : 
			// Leader.
//			if (leader.indexOf("No-Leader") >= 0) {
//				CellStyle style = cell.getCellStyle();
//				style.setFillForegroundColor(IndexedColors.RED.getIndex());
//				style.setFillBackgroundColor(IndexedColors.RED.getIndex());
//				style.setBorderBottom(CellStyle.BORDER_DASHED);
//				style.setBorderLeft(CellStyle.BORDER_DASHED);
//				style.setBorderTop(CellStyle.BORDER_DASHED);
//				style.setBorderRight(CellStyle.BORDER_DASHED);
//				cell.setCellStyle(style);
//			}
			cell.setCellValue(info.getLeader());
			break;				
		}
	}


	public static List<QaInfo> getQaInfos(final List<File> files, final int size, final boolean logger) {
		List<QaInfo> results = new ArrayList<QaInfo>();
		int index = 0;
		for (Iterator<File> iter = files.iterator(); iter.hasNext();) {
			index++;
			File f = iter.next();
			Workbook wb = ExcelUtil.getWorkbook(f);
			Sheet sheet = null;
			
			try {
				sheet = wb.getSheet("QA管理表（入力シート）");
				if (sheet == null) sheet = wb.getSheetAt(1);
			} catch (Exception e) {
			}
			
			if (sheet == null) {
				String warn = f + "\r\nNOT FOUND QA管理表（入力シート）";
				warnln(warn);
				println(warn);
				continue;
			}
			
			
			Row row = sheet.getRow(6);
			if (row != null) {
				QaInfo qaInfo = new QaInfo();
				
				// No.
				// QA番号	0.
				Cell cell0 = row.getCell(0);
				// 機能ID	7.
				Cell cell7 = row.getCell(7);
				// 機能名	8.
				Cell cell8 = row.getCell(8);
				// 問題	10.
				Cell cell10 = row.getCell(10);
				// 回答	16.
				Cell cell16 = row.getCell(16);
				// 提出者	4.
				Cell cell4 = row.getCell(4);
				// 回答者	19.
				Cell cell19 = row.getCell(19);
				// 提出日	2.
				Cell cell2 = row.getCell(2);
				// 予定回答日	13.
				Cell cell13 = row.getCell(13);
				// 発生工程	1.
				Cell cell1 = row.getCell(1);
				// ○/×.
				
				if (logger) println(
						"QA番号:",ExcelUtil.getCellString(cell0), 
						"\t機能ID:",ExcelUtil.getCellString(cell7), 
						"\t機能名:",ExcelUtil.getCellString(cell8), 
						"\t問題:",ExcelUtil.getCellString(cell10), 
						"\t回答:",ExcelUtil.getCellString(cell16), 
						"\t提出者:",ExcelUtil.getCellString(cell4), 
						"\t回答者:",ExcelUtil.getCellString(cell19), 
						"\t提出日:",ExcelUtil.getCellDateString(cell2), 
						"\t予定回答日:",ExcelUtil.getCellDateString(cell13), 
						"\t発生工程:",ExcelUtil.getCellString(cell1)
				);
				// Leaderを設定する.
				qaInfo.setLeader(LEADER.getLeader(ExcelUtil.getCellString(cell4)));
				// ＱＡ管理番号を設定する.
				qaInfo.setQaNum(ExcelUtil.getCellString(cell0));
				// プログラムIDを設定する.
				qaInfo.setProgramId(ExcelUtil.getCellString(cell7));
				// プログラム名を設定する.
				qaInfo.setProgramName(ExcelUtil.getCellString(cell8));
				// 発信内容を設定する.
				qaInfo.setAskContent(ExcelUtil.getCellString(cell10));
				// （Ａ）　回答内容を設定する.
				qaInfo.setAnswerContent(ExcelUtil.getCellString(cell16));
				// 質問者氏名を設定する.
				qaInfo.setName(ExcelUtil.getCellString(cell4));
				// 回答者を設定する.
				qaInfo.setAnswerName(ExcelUtil.getCellString(cell19));
				// 発生日を設定する.
				qaInfo.setHappendDate(ExcelUtil.getCellDate(cell2));
				// 回答期限を設定する.
				qaInfo.setDueTime(ExcelUtil.getCellDate(cell13));
				// 発生工程を設定する.
				qaInfo.setHappendProject(ExcelUtil.getCellString(cell1));
				warn(index , ". " + f.getName() + " Finished");
				BaseUtil.showPercent(index, size);
				results.add(qaInfo);
			}
		}
		return results;
	}
	
	/**
	 * search the dir for the tableName
	 * and get the column's type.
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static String getColumnType(
			final String tableName, 
			final String columnName,
			final File dir) {
//		final String path = "C:\\tools\\Generate-Test-Sql\\論理テーブル定義";
		if (StringUtil.isEmpty(tableName) 
				|| StringUtil.isEmpty(columnName)) {
			println("参数有误");
			return null;
		}
		
		FileList list = new FileList(dir, ".xls");
		List<File> results = list.getResults();
		
		int tableNameIdx = -1;
		int columnIdx = -1;
		for (File f : results) {
			String fileName = f.getName();
			
			tableNameIdx = fileName.indexOf(tableName);
			if (tableNameIdx >= 0) {
				// search the table's excel right.
				Workbook wb = ExcelUtil.getWorkbook(f);
				
				Sheet sheet = 
					ExcelUtil.getSheetByNameAndIndex(wb, 
							"論理テーブル定義【" + tableName + "】", 
							0);
				
				if (sheet == null) {
					println("未找到 Sheet"); 
					return null;
				}
				
				for (Row row : sheet) {
					for (Cell cell : row){
						String value = ExcelUtil.getCellString(cell);
						columnIdx = value.indexOf(columnName);
						if (columnIdx >= 0) {
							Cell typeCell = row.getCell(7);
							return ExcelUtil.getCellString(typeCell);
						}
					}
				}
			}
		}
		
		println("Not Found Table : ", tableName, ", Column: " , columnName);
		return null;
	}
	
	

	
	
	
	/**
	 * check dierctory is right, if wrong then exit.
	 * @param dir
	 */
	public static File getDir(final String dirPath) {
		if (dirPath == null || dirPath.trim().length() == 0)
			exitWithMessage("路径为空.\r\nApp exit.");
		File file = new File(dirPath);
		if (file == null || !file.exists()) {
			exitWithMessage("文件不存在.\r\nApp exit.");
		} else if (file.isFile()) {
			exitWithMessage("不是一个目录.\r\nApp exit.");
		}
		return file;
	}
	
	/**
	 * check file is right, if wrong then exit.
	 * @param excel
	 */
	protected static File getFile(final String excel) {
		if (excel == null || excel.trim().length() == 0) 
			exitWithMessage("路径为空.\r\nApp exit."); 
		File file = new File(excel);
		if (file == null || !file.exists()) {
			exitWithMessage("文件不存在.\r\nApp exit."); 
		} else if (file.isDirectory()) {
			exitWithMessage("不是 一个文件.\r\nApp exit.");
		}
		return file;
	}
	
	/**
	 * println the message and exit.
	 * @param message
	 */
	private static void exitWithMessage(String message){
		warnln(message, " App Exit.");System.exit(0);
	}
}