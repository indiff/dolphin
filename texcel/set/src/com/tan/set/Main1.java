package com.tan.set;

import static com.tan.excel.util.NumberUtil.round;
import static com.tan.util.TanUtil.print;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.showInputDialog;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.Schedule;
import com.tan.qa.BaseUtil;
import com.tan.util.ExcelUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/12 13:34:04
 */
public class Main1 {
	// Default date format.
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	// the percent format when the value >= 10%.
	private static final String DATA_PERCENT_FORMAT = "00%";
	
	// the percent format short when the value < 10%.
	private static final String DATA_PERCENT_FORMAT_SHORT = "0%";
	
	
	final static BigDecimal RATE1 = new BigDecimal(.25d);
	final static BigDecimal RATE2 = new BigDecimal(.65d);
	final static BigDecimal RATE3 = new BigDecimal(.1d);
	
	final static BigDecimal PERCENT1 = new BigDecimal(0.1);
	final static BigDecimal PERCENT100 = new BigDecimal(1);
	
	
	public static void main(String[] args) {
		
		String currentPath = BaseUtil.getCurrentPath();
		
//		currentPath = "C:\\tools\\Set-Schedule";
		
		// 全社協簡易状況（不動産）_201000708.xls.
		String outer = showInputDialog("请输入外部Excel文件名:", "全社協簡易状況（不動産）.xls");
		
		// 内部進捗管理表.xls.
		String inner = showInputDialog("请输入外部Excel文件名:", "内部進捗管理表.xls");
		
		checkPathsIsNullWithExit(outer, inner);
		
		/** logger the paths of the excels.**/
		println("外部Excel:\t" + (outer = currentPath + File.separatorChar + outer));
		println("内部Excel:\t" + (inner = currentPath + File.separatorChar + inner));
		
		warnln("外部Excel:\t" + outer);
		warnln("内部Excel:\t" + inner);
		
		/** Logger.**/
		warnln("Loading 内部進捗管理  Excel......");
		/** Operate the inner excel.**/
		Workbook innerWorkbook = ExcelUtil.getWorkbook(inner);
		
		/** Logger.**/
		warnln("Analysing 内部進捗管理  Excel......");
		List<Schedule> schedules = analyseSchedules(innerWorkbook, false);

		if (schedules != null) {
			println("Schedules Size: " + schedules.size());
		}
		
		
		/** Logger.**/
		warnln("Loading 全社協簡易状況  Excel......");
		/** Operate the outer excel.**/
		Workbook outerWorkbook = ExcelUtil.getWorkbook(outer);
		
		/** Logger.**/
		warnln("Setting 全社協簡易状況  Excel......");
		setSchedules(outerWorkbook, schedules, true);
	}

	private static void checkPathsIsNullWithExit(String ... paths) {
		if (paths == null || paths.length == 0) System.exit(0);
		for (String path : paths) {
			if (path == null || path.length() == 0) {
				warnln("路径为空 App Exit.");
				System.exit(0);
			}
		}
	}

	private static void setSchedules(Workbook wb, List<Schedule> schedules, boolean logger) {
		if (wb == null) {
			warnln("外部Excel获取失败!");
			return;
		}
		if (schedules == null || schedules.size() == 0) {
			warnln("Schedules 未获取到!");
			return;
		}
		// get the sheet.
		Sheet sheet = wb.getSheet("進捗報告（実態）");
		if (sheet == null) sheet = wb.getSheetAt(0);
		
		int lastRowNum = sheet.getLastRowNum();
		
		int idx = 3;
		for (; idx < lastRowNum; idx++) {
			Row row = sheet.getRow(idx);
			
			// プログラムＩＤ.
			Cell programIdCell = row.getCell(2);
			String proId = getCellString(programIdCell, wb);
			Schedule schedule = getSchedule(proId, schedules);

			
			if (schedule == null) continue;
			Date actualStartDate = schedule.getActualStartDate();
			if (actualStartDate == null) continue;
			
			// 実際の着手日.
			Cell actualDateCell = row.getCell(6);
			actualDateCell.setCellValue(actualStartDate);
			
			// 仕様理解進捗率.
			Cell understandCell = row.getCell(8);
			BigDecimal understandRate = schedule.getUnderstandRate();
			setCellPercent(understandCell, understandRate, wb);
			
			
			// 単体テスト設計進捗率.
			Cell utdRateCell = row.getCell(10);
			BigDecimal utdRate = null;
			/** When the understandrat is right.**/
				utdRate = schedule.getUtdRate();
				setCellPercent(utdRateCell, utdRate, wb);
			
			
			// 単体テスト設計レビュー進捗率.
			Cell utdReviewRateCell = row.getCell(12);
			BigDecimal utdReviewRate = null;
			/** When the utdRate is right.**/
				setCellPercent(utdReviewRateCell, utdReviewRate, 
						wb);
			
			
			// 製造（コーディング）進捗率.
			Cell codingRateCell = row.getCell(14);
			BigDecimal codingRate = null;
			/** When the utdReviewRateCell is right.**/
				codingRate = schedule.getCodingRate();
				setCellPercent(codingRateCell, codingRate, wb);
			
			
			// CDI進捗率.
			Cell cdiRateCell = row.getCell(16);
			BigDecimal cdiRate = null;
			
				cdiRate = schedule.getCdiRate();
				setCellPercent(cdiRateCell, cdiRate, wb);
			
			
			// 単体テスト進捗率.
			Cell utRateCell = row.getCell(16);
			BigDecimal utRate = null;
			
			/** When the utdReviewRateCell is right.**/
				utRate = schedule.getUtRate();
				setCellPercent(utRateCell, utRate, wb);	
			
		}
		
		String output = BaseUtil.getCurrentPath() + 
		File.separatorChar + "全社協簡易状況（不動産）_" + 
		new SimpleDateFormat(DATE_FORMAT).format(new Date()) + ".xls";
		
		ExcelUtil.writeExcel(wb, output);
		
		if (logger) {
			warnln("Write successful!\r\nSee the excel " + output);
		}
	}
	
	private static void setCellPercent(Cell cell, BigDecimal rate, Workbook wb) {
		if (cell == null || rate == null) return;
		cell.setCellValue(rate.doubleValue());	
		
		CellStyle style = cell.getCellStyle();
		
		if (rate.compareTo(PERCENT1) < 0) {
			style.setDataFormat(wb.createDataFormat()
					.getFormat(DATA_PERCENT_FORMAT));
		} else {
			style.setDataFormat(wb.createDataFormat()
					.getFormat(DATA_PERCENT_FORMAT_SHORT));
		}
		cell.setCellStyle(style);
		
	}

	/**
	 * Get the schedule.
	 * @param proId
	 * @param schedules
	 * @return
	 */
	private static Schedule getSchedule(String proId, List<Schedule> schedules) {
		if (proId == null || proId.length() == 0) return null;
		Iterator<Schedule> iter = schedules.iterator();
		while (iter.hasNext()) {
			Schedule s = iter.next();
			if (proId.equals(s.getProgramId())) return s;
		}
		return null;
	}
	
	/**
	 * analyse schedules by the 内部進捗管理表. 
	 * @param wb
	 * @param logger
	 * @return
	 */
	private static List<Schedule> analyseSchedules(Workbook wb, boolean logger) {
		int number = wb.getNumberOfSheets();
		
		List<Schedule> schedules = new ArrayList<Schedule>();
		
		for (int i = 0 ; i < number; i++) {
			Sheet sheet = wb.getSheetAt(i);
			
//			println(i + "\t" + sheet.getSheetName());
			
			/************** iterate the cell start	***************/
//			showSheetAllCells(sheet);
			
			int lastRowNum = sheet.getLastRowNum();
			String sheetName = sheet.getSheetName();
			if (logger) println("" + i, '\t', sheetName, '\t', "LastRowNumber:", lastRowNum);
			
//			println("\t", "lastRowNum", lastRowNum);
			
			for (int rowNum = 2; rowNum <= lastRowNum; rowNum += 2) {
				
				Row row = sheet.getRow(rowNum);
				
				//	プログラムID.
				Cell proIdCell = row.getCell(3);
				String proId = proIdCell.getStringCellValue();
				
				/**
				 * When found the プログラムID.
				 */
				if (proId != null && proId.trim().length() != 0) {
					Schedule s = new Schedule(); 
					s.setProgramId(proId);
					
					s.setLeader(sheetName);
					
					if (logger) print("\t", rowNum, "\t", proId);
					
					    Row actualRow = sheet.getRow(rowNum + 1);
					
						// UTDパターン作り込み-作業進捗率.
						BigDecimal utdPattern = getProgress(row.getCell(8)); 
						if (logger) print("\t", "UTDパターン作り込み-作業進捗率:",  utdPattern);
						
						// UTDパターン作り込みレビュー進捗率.
						BigDecimal utdPatternReview = getProgress(row.getCell(14)); 
						if (logger) print("\t", "UTDパターン作り込みレビュー進捗率",  utdPatternReview);
						
						// UTDデータ作り込み進捗率.
						BigDecimal utdData = getProgress(row.getCell(20)); 
						if (logger) print("\t", "UTDデータ作り込み進捗率",  utdData);
						
						// UTDデータ作り込みレビュー進捗率.
						BigDecimal utdDataReview = getProgress(row.getCell(26)); 
						if (logger) print("\t", "UTDデータ作り込みレビュー進捗率",  utdDataReview);
						
						// UTD進捗率.
						BigDecimal utd = getProgress(row.getCell(32)); 
						if (logger) print("\t", "UTD進捗率",  utd);
						
						// UTDレビュー進捗率.
						BigDecimal utdReview = getProgress(row.getCell(38)); 
						if (logger) print("\t", "UTDレビュー進捗率",  utdReview);
						
						// Coding進捗率.
						BigDecimal coding = getProgress(row.getCell(44)); 
						if (logger) print("\t", "Coding進捗率",  coding);
						
						// Codingレビュー進捗率.
						BigDecimal codingReview = getProgress(row.getCell(44)); 
						if (logger) print("\t", "Codingレビュー進捗率",  codingReview);
						
						// UT進捗率.
						BigDecimal ut = getProgress(row.getCell(44)); 
						if (logger) print("\t", "UT進捗率",  ut);
						
						// UTレビュー.
						BigDecimal utReview = getProgress(row.getCell(44)); 
						if (logger) print("\t", "UTレビュー進捗率",  utReview);
						
						
					/**
					 * When had a row. 
					 */
					if (actualRow != null) {
						// UTDパターン作り込み-開始日.
						Cell utd1StartDateCell = actualRow.getCell(10);
						
						Date utd1StartDate = utd1StartDateCell.getDateCellValue();
						if (logger) print("\t", "UTDパターン作り込み-開始日:", 
								getDateString(utd1StartDate));
						
						/**
						 * 如果有了 UTDパターン作り込み-開始日.
						 */
						if (utd1StartDate != null) {
							
							/** 単体テスト設計進捗率.**/
							BigDecimal utdRate = null;
							
							/** 単体テスト設計レビュー進捗率.**/
							BigDecimal utdReviewRate = null;
							
							
							/** 単体テスト設計進捗率	start	.**/							
							// 1. UTDパターン作り込み-作業進捗率.
							if (utdPattern != null){
								utdRate = new BigDecimal(0);
								// TODO .
								utdRate = utdRate.add(utdPattern.multiply(RATE1));
							}
							// 2. UTDデータ作り込み進捗率.
							if (utdData != null && utdRate != null) {
								utdRate = utdRate.add(utdData.multiply(RATE2));
							}
							// 4. UTD進捗率.
							if (utd !=  null && utdRate != null) {
								utdRate = utdRate.add(utd.multiply(RATE3));
							}
							
							// set data.
							if (utdRate != null) {
								s.setUtdRate(round(utdRate, 1));
							}
							/** 単体テスト設計進捗率	end		.**/
							
							
							
							/** 単体テスト設計レビュー進捗率	start.	**/							
							// 1. UTDパターン作り込み-レビュー作業進捗率.
							if (utdPatternReview != null){
								utdReviewRate = new BigDecimal(0);
								utdReviewRate = utdReviewRate.add(utdPatternReview.multiply(RATE1));
							}
							// 2. UTDデータ作り込みレビュー進捗率.
							if (utdDataReview != null && utdReviewRate != null) {
								utdReviewRate = utdReviewRate.add(utdDataReview.multiply(RATE2));
							}
							// 3. UTDレビュー進捗率.
							if (utdReview !=  null && utdReviewRate != null) {
								utdReviewRate = utdReviewRate.add(utdReview.multiply(RATE3));
							}
							// set data.
							if (utdReview != null) {
								s.setUtdReviewRate(round(utdReviewRate, 1));
							}
							/** 単体テスト設計レビュー進捗率	end.	**/
							
							
							/** 製造（コーディング）進捗率	start.	**/
							s.setCodingRate(round(coding, 1));
							/** 製造（コーディング）進捗率	end.	**/
							
							
							/** CDI進捗率	start.	**/
							s.setCdiRate(round(codingReview, 1));
							/** CDI進捗率	end.	**/
							
							
							/** 単体テスト進捗率	start.	**/
							s.setUtRate(round(utReview, 1));
							/** 単体テスト進捗率	end.	**/
							
							// 実際の着手日.
							s.setActualStartDate(utd1StartDate);
							
							// 仕様理解進捗率.
							s.setUnderstandRate(new BigDecimal(1));
							
							schedules.add(s);
						}
						if (logger) println();
					} 
				} // When found the プログラムID END
			}
		}/************** iterate the cell end	***************/
		return schedules;
	}
	
	/**
	 * get the date string with the format "yyyy/MM/dd".
	 * @param d	the	date.
	 * @return
	 */
	private static String getDateString(Date d) {
		if (d != null) {
			return new SimpleDateFormat("yyyy/MM/dd").format(d);
		}
		return "";
	}

	@SuppressWarnings("unused")
	private static void showSheetAllCells(Sheet sheet) {
		for (Row row  : sheet) {
			for(Cell c : row) {
				println(c);
			}
		}
	}
	
	/**
	 * when the cell is numberic then return the numberic value.
	 * @param c
	 * @return
	 */
	private static BigDecimal getProgress(Cell c) {
		if (c != null && c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			 return new BigDecimal(c.getNumericCellValue());
		}
		return null;
	}
	
	/**
	 * Get the cell's string value as the cell type.
	 * @param cell
	 * @param wb
	 * @return
	 */
	private static String getCellString(Cell cell, Workbook wb) {
		if (cell == null) return "no-cell";
		
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK : {
				return "";
			}
			case Cell.CELL_TYPE_BOOLEAN : {
				return String.valueOf(cell.getBooleanCellValue());
			}
			case Cell.CELL_TYPE_ERROR : {
				return "error";
			}
			case Cell.CELL_TYPE_FORMULA : {
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				CellValue value = null;
				try {
					value = evaluator.evaluate(cell);
				} catch (RuntimeException e) {
					return null;
				}
				if (value != null) return value.getStringValue();
				return "formula-novalue";
			}
			case Cell.CELL_TYPE_NUMERIC : {
				return String.valueOf(cell.getNumericCellValue());
			}
			case Cell.CELL_TYPE_STRING : {
				return cell.getRichStringCellValue().getString();
			}
		}
		return null;
	}
}