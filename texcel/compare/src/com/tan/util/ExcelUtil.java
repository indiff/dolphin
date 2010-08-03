package com.tan.util;

import static com.tan.consts.Consts.LINE;
import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.Dataset;
import com.tan.bean.Module;

public class ExcelUtil {
	static String SAMPLE_EXCEL_PATH = "C:\\texcel\\input\\sample.xls";
	static final String demoPath = "C:\\compare\\demo.xls";
	
	static CellStyle cellStyle = null;
	
	
	public static void main(String[] args) throws Exception{
		
		//Workbook wb = 
//		getWorkbook(demoPath, true);
		
//		final String outputPath = "c:\\compare\\out.xls";
//		Workbook wb = getSampleWorkbook();
//		
//		// データセット仕様	Sheet.
//		Sheet dataSetSheet = wb.cloneSheet(5);
//		
//		
//		// モジュールIF仕様 Sheet.
//		Sheet moduleSheet = wb.cloneSheet(6);
//		int numberOfSheets = wb.getNumberOfSheets();
//		wb.setSheetName(numberOfSheets - 2, "データセット仕様(Clone)");
//		wb.setSheetName(numberOfSheets - 1, "モジュールIF仕様(Clone)");
//	
//		
//		writerExcel(outputPath, wb);
		
//		Sheet sheet = wb.createSheet();
//		int rownum = 0;
//		Row row = sheet.createRow(rownum);
//		Cell blankCell = row.createCell(0, Cell.CELL_TYPE_BLANK);
//		Cell booleanCell = row.createCell(1, Cell.CELL_TYPE_BOOLEAN); booleanCell.setCellValue(false);
//		// Error Value can only be 0,7,15,23,29,36 or 42.
////		Cell errorCell = row.createCell(2, Cell.CELL_TYPE_ERROR);
////		errorCell.setCellErrorValue(Byte.parseByte("29"));
//		Cell formualCell = row.createCell(3, Cell.CELL_TYPE_FORMULA);formualCell.setCellFormula("now()");
//		Cell numbericCell = row.createCell(4, Cell.CELL_TYPE_NUMERIC);numbericCell.setCellValue(1.0d);
//		Cell stringCell = row.createCell(5, Cell.CELL_TYPE_STRING);stringCell.setCellValue("fuck");
	}
	
	
	/**
	 * 
	 * @param datasets
	 * @param outputPath
	 * @param excels
	 */
	public static void writeExcel(Dataset[] datasets, final String outputPath, final File ... excels) {
		
		/*** The Sample workbook for output. **/
		SAMPLE_EXCEL_PATH = 
			Thread.currentThread()
				  .getContextClassLoader()
				  .getResource("")
				  .getFile() + "input/sample.xls";
		
		Workbook wb = getSampleWorkbook();
		
		/** Create the excels with the datasets by the データセット仕様(Sample).**/
		createDatasetSheet(datasets, wb , excels);
		
		/** TODO 07-13 modified. **/
		wb.removeSheetAt(2);
		wb.removeSheetAt(2);
		
		/** Remove all the print Areas.**/
		removeAllPrintAreas(wb);
//		showAllSheetsName(wb);
		
		// Write The workbook to the output.
		writeExcel(wb, outputPath);
	}

	
	/**
	 * Remove all the print areas for the workbook.
	 * @param wb
	 */
	public static void removeAllPrintAreas(Workbook wb) {
		int numberOfSheets = wb.getNumberOfSheets();
		
		for (int i = 0; i < numberOfSheets ; i++){
			wb.removePrintArea(i);
			
		}
	}
	
	
	/**
	 * Show all sheets name.
	 * @param wb
	 */
	@SuppressWarnings("unused")
	private static void showAllSheetsName(Workbook wb) {
		int number = wb.getNumberOfSheets();
		
		for (int i = 0 ; i < number; i++) {
			println(i + "\t" + wb.getSheetName(i));
		}
	}

	
	/**
	 * Create the Dataset sheet.
	 * @param datasets
	 * @param wb
	 */
	private static void createDatasetSheet(final Dataset[] datasets, 
			Workbook wb, final File ... excels) {
		
		
		/** TODO 07-13 modified. // TODO old index 5.**/
		Sheet datasetSheet = wb.cloneSheet(2);
		wb.setSheetName(wb.getNumberOfSheets() - 1, "データセット仕様(Dataset)");
		int length = datasets.length;
		
		Workbook stylebook = getStylebook(excels); 
		
		
		Sheet oldDatasetSheet = null;
		
		if (stylebook !=  null) {
			oldDatasetSheet = stylebook.getSheetAt(6);
			oldDatasetSheet = getOldDatasetSheet(stylebook);
//			int number = stylebook.getNumberOfSheets();
//			for (int i= 0 ; i < number ; i ++) {
//				println(i + "\t" + stylebook.getSheetName(i));
//			}
			
		}
		
		for (int i = 0 ; i < length; i++) {
			Row row = datasetSheet.getRow(7 + i);
			
			if (oldDatasetSheet != null) {
				Row oldRow = oldDatasetSheet.getRow(7 + i);
				// データテーブルID.
				// データテーブル名.																	
				Cell oldIdCell = oldRow.getCell(1);
				
				Cell old = row.getCell(13);
				
				old.setCellValue(oldIdCell.getStringCellValue());
			}
			
			// No.
			Cell cellNo = row.getCell(0);
			cellNo.setCellValue((i + 1));
			// データテーブルID.
			Cell cellId = row.getCell(1);
			cellId.setCellValue(datasets[i].getTable());
			
			// データテーブル名.
//			Cell cellName = row.getCell(2);
//			cellName.setCellValue(datasets[i].getTable());
			
			// 比較.
			/*** Compare the データテーブル	start. **/
			Cell compareCell = row.getCell(31);
			
			//compareCell.setCellFormula("");
			
			FormulaEvaluator evaluator = 
				wb.getCreationHelper().createFormulaEvaluator();
			
			CellValue compareValue = evaluator.evaluate(compareCell);
			if (compareValue != null) {
				compareCell.setCellValue(compareValue.getStringValue());
			}
			/*** Compare the データテーブル	end  . **/
			
			
			// 備考.
			Cell cellRemark = row.getCell(32);
			cellRemark.setCellValue(datasets[i].getRemark());
			
			/*** モジュールIF仕様(Sample) **/
			createModuleSheet(wb, datasets[i], i, stylebook);
		}
	}
	
	private static Sheet getOldDatasetSheet(Workbook stylebook) {
		int number = stylebook.getNumberOfSheets();
		for (int i = 0 ; i < number; i++) {
			String name = stylebook.getSheetName(i);
			if (name.contains("データセット仕様")) {
				return stylebook.getSheetAt(i);
			}
		}
		return null;
	}


	/**
	 * When has the stylebook.
	 * @param excels
	 * @return
	 */
	private static Workbook getStylebook(File ... excels) {
		if (excels != null && excels.length == 1 && excels[0] != null) {
			return getWorkbook(excels[0]);
		}
		return null;
	}


	/**
	 * Create the Module Sheet.
	 * @param wb
	 * @param dataset
	 * @param index
	 */
	private static void createModuleSheet(
							final Workbook wb, 
							final Dataset dataset, 
							final int index, 
							final Workbook stylebook) {
		
		/** TODO 07-13 modified. // TODO old index 6.**/
		Sheet moduleSheet = wb.cloneSheet(3); 
		String sheetName = "モジュールIF仕様(" + index + dataset.getTable() + ")";
		
		// クラス間IF仕様(CKTHJ1).
		Sheet oldModuleSheet = null;
		String table = dataset.getTable();
		if (table != null && stylebook != null) {
			if (table.length() >= 21){
				table = table.substring(0, 21);
			}
			String oldSheetName = "クラス間IF仕様(" + table + ')';
			
			oldModuleSheet = stylebook.getSheet(oldSheetName);
//			warnln(sheetName + "\r\n" + oldSheetName);
//			warnln("The module sheet is right!" + oldModuleSheet);
		}
		
		wb.setSheetName(wb.getNumberOfSheets() - 1, sheetName);
		
		List<Module> modules = dataset.getModule();
		
		int idx = 0;
		int rowNum = 7;
		for (Iterator<Module> iter = modules.iterator(); iter.hasNext();) {
			// idx 后面 ++.
			Row row = moduleSheet.getRow(rowNum + idx);
			
			Module module = iter.next();
			// No.
			Cell cellNo = row.getCell(0);
			cellNo.setCellValue(module.getNo());
			// 項目名.
			Cell cellName = row.getCell(1);
			cellName.setCellValue(module.getName());
			
			// 項目ID.
			Cell cellId = row.getCell(13);
			cellId.setCellValue(module.getId());
			
			// データ型.
			Cell cellType = row.getCell(25);
			
 			String type = module.getDataType();
			if (type != null && type.length() >= 3) {
				cellType.setCellValue("" + Character.toUpperCase(type.charAt(3)) + type.substring(4));
			} else {
				cellType.setCellValue(type);
			}
			
			// Old項目名.
			Cell oldNameCell = row.getCell(31);
			// Oldデータ型.
			Cell oldTypeCell = row.getCell(32);
			
			//項目名比較.
			Cell compareNameCell = row.getCell(33);
			
			//データ型比較.
			Cell compareTypeCell = row.getCell(34);

			/************* Write the old data to the excel start. ****************/
			// Old Module Row.
			Row oldRow = null;
			if (oldModuleSheet != null) {
				oldRow = oldModuleSheet.getRow(rowNum + idx++);
				//warnln(rowNum + idx + "*************");
			}
			
			if (oldRow != null) {
				oldNameCell.setCellValue(oldRow.getCell(1).getRichStringCellValue());
				oldTypeCell.setCellValue(oldRow.getCell(25).getRichStringCellValue());
				
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				
				CellValue evalName = evaluator.evaluate(compareNameCell);
				CellValue evalType = evaluator.evaluate(compareTypeCell);
				
				if (evalName != null && evalName.getStringValue().length() > 0) {
					/** Set backgorund color pink. Which the row of the cell has 'wrong'.*/
					setFillBackgroundColor(wb, cellNo);
				}
				
				if (evalType != null && evalType.getStringValue().length() > 0) {
					setFillBackgroundColor(wb, cellNo);
				}
				
				compareNameCell.setCellValue(evalName.getStringValue());
				compareTypeCell.setCellValue(evalType.getStringValue());
			}
			
			/************* Write the old data to the excel end . ****************/
			
//			cellNum.setCellValue(module.getRowNum());
			
			// PKEY.
			Cell cellPkey = row.getCell(35);
			cellPkey.setCellValue(module.getPkey());
			
			// 必須.
//			Cell cellNecess = row.getCell(36);
//			cellNecess.setCellValue(module.get);
			
			// 備考.
			Cell cellRemark = row.getCell(37);
			
			if (module.getRemark() == null) {
				cellRemark.setCellValue("");
			} else {
				CellStyle cellStyle = getCellStyle(wb);
				cellRemark.setCellStyle(cellStyle);
				cellRemark.setCellValue(module.getRemark());
			}
		}
		
	}

	
	/**
	 * The the cell style for the workbook.
	 * @param wb
	 * @param cells
	 */
	private static void setFillBackgroundColor(final Workbook wb, Cell ... cells) {
		if (cells == null || cells.length == 0) return;
		CellStyle cs = wb.createCellStyle();
		
		cs.setFillBackgroundColor(IndexedColors.RED.getIndex());
		cs.setFillPattern(HSSFCellStyle.BIG_SPOTS);
		cs.setFillForegroundColor(IndexedColors.RED.getIndex());
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		for (Cell cell : cells) {
			cell.setCellStyle(cs);
		}
	}

	/**
	 * get the cell style which background color is red.
	 * @param wb
	 * @return
	 */
	private static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setColor(Font.COLOR_RED);
		style.setFont(font);
		return style;
	}

	/**
	 * 将Excel文件写入到指定路径.
	 * @param outputPath	路径.
	 * @param wb			Workbook.
	 */
	public static void writeExcel(final Workbook wb, final String outputPath) {
		writeExcel(wb, new File(outputPath));
	}
	
	/**
	 * 将Excel文件写入到指定路径.
	 * @param output		Excel指定输出的文件.
	 * @param wb			Workbook.
	 */
	public static void writeExcel(final Workbook wb, final File output) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(output);
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				} out = null;
		}
	}	
	
	public static Workbook getSampleWorkbook() {
		return getWorkbook(SAMPLE_EXCEL_PATH);
	}
	
	/**
	 * 读取Excel文件，返回 Workbook.
	 * @param excelPath	Excel文件路径.
	 * @return
	 */
	public static Workbook getWorkbook(final String excelPath) {
		return getWorkbook(excelPath, false);
	}
	
	public static Workbook getWorkbook(final File excelPath) {
		return getWorkbook(excelPath, false);
	}
	
	
	public static Workbook getWorkbook(final String excelPath, final boolean logger) {
		if (StringUtil.isEmpty(excelPath) ||
				!excelPath.toLowerCase().endsWith(".xls")
				) {
			warnln("Excel 文件路径有误!");
		}
		return getWorkbook(new File(excelPath), logger);
	}	
	/**
	 * 读取Excel文件，返回Workbook，如果 looger 为 true 并且记录日志。
	 * @param excelPath	Excel文件路径.
	 * @param logger	是否记录日志.
	 * @return
	 */
	public static Workbook getWorkbook(final File excelPath, final boolean logger) {
		FileInputStream in =null;
		Workbook wb = null;
		try {
			in = new FileInputStream(excelPath);
			wb = new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} in = null;
		}
		
		if (logger) {
			int numberOfSheets = wb.getNumberOfSheets();
			println("Excel文件的Sheet个数:", numberOfSheets);
			for (int index = 0 ; index < numberOfSheets; index++) {
				String sheetName = wb.getSheetName(index);
				println(
				"Sheet", 
				index , 
				':', 
				sheetName
				);
			}
		}
		return wb;
	}
	
	/**
	 * Check the sheet and return the info string.
	 * @param excel
	 * @return
	 */
	public static String getCheckSheetInfo(final File excel) {
		StringBuilder b = new StringBuilder();
		Workbook wb = getWorkbook(excel);
		int numberOfSheets = wb.getNumberOfSheets();
		for (int index = 0 ; index < numberOfSheets; index++) {
			String sheetName = wb.getSheetName(index);
			String right = ExcelChecker.checkSheetName(sheetName);
			if (right.length() > 0) {
				append(b, index, ' ', right, LINE);
			}
		}
		if (b.length() > 0)
		b.insert(0, "Excel文件的Sheet个数:" + numberOfSheets);
		return b.toString();
	}
	
	/**
	 * Check the sheet and return the info string.
	 * @param excelPath
	 * @return
	 */
	public static String getCheckSheetInfo(final String excelPath) {
		return getCheckSheetInfo(new File(excelPath));
	}
	
	/**
	 * Sleep millis.
	 * @param millis
	 */
	public static void sleep(final long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Inspect the workbook's sheet names.
	 * @param wb
	 */
	public static void inspectSheetNames(Workbook wb) {
		int numberOfSheets = wb.getNumberOfSheets();
		
		for (int i = 0; i < numberOfSheets; i++) println(i , "\t", wb.getSheetName(i));
	}
	
	
	public static String getCellString(Cell cell) {
		if (cell == null) return "";
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK : return "";
			case Cell.CELL_TYPE_BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_ERROR: return "error";
			case Cell.CELL_TYPE_NUMERIC: return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_STRING : 
				RichTextString rich = cell.getRichStringCellValue();
				if (rich != null) return rich.getString();
				break;
				
		}
		return "";
	}
	
	public static String getCellDateString(Cell cell) {
		Date date = cell.getDateCellValue();
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static Date getCellDate(Cell cell) {
		if (cell == null) return null;
		Date date = null;
		try {
			date = cell.getDateCellValue();
		} catch(RuntimeException e) {
			// ignore.
		} catch (Exception e1) {
			// ignore.
		}
		return date;
	}
	
	/**
	 * 
	 * @param cell
	 * @param wb
	 * @return
	 */
	public static String getCellString(Cell cell, Workbook wb) {
		if (cell == null) return null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK : return "";
			case Cell.CELL_TYPE_BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
			case Cell.CELL_TYPE_ERROR: return "error";
			case Cell.CELL_TYPE_NUMERIC: return String.valueOf(cell.getNumericCellValue());
			case Cell.CELL_TYPE_FORMULA : {
				FormulaEvaluator evaluator = 
					wb.getCreationHelper().createFormulaEvaluator();
				CellValue value = evaluator.evaluate(cell);
				if (value != null) return value.getStringValue();
				break;
			}
			case Cell.CELL_TYPE_STRING : 
				RichTextString rich = cell.getRichStringCellValue();
				if (rich != null) return rich.getString();
				break;
				
		}
		return null;
	}


	public static Sheet getSheetByNameAndIndex(final Workbook wb ,final String string, final int index) {
		if (wb == null) return null;
		if (string == null || string.trim().length() == 0) return null;
		Sheet sheet = wb.getSheet(string);
		if (sheet == null) sheet = wb.getSheetAt(index);
		return sheet;
	}	
}