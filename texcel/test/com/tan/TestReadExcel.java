package com.tan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestReadExcel extends TestCase{
	private  String dir = "D:\\SoftBankSVN\\020 ED\\01 画面\\機能設計書\\";
	
	public void readExcel() {
		List<String> result = new ArrayList<String>();
		// 是否记录日志
		boolean logger = true;
		
		Workbook wb = readExcel("D:\\pswg-masserver\\020 ED\\06 コード定義\\code.xls");
		// 定位 sheet 页
		Sheet sheet = wb.getSheetAt(1);
		for (Row row : sheet) {
			// 定位行 row.getRowNum()
			for (Cell cell : row) {
				// 单元格的样式
				CellStyle style = cell.getCellStyle();
				int fillBackgroundColor =style.getFillBackgroundColor();
				int fillForegroundColor = style.getFillForegroundColor();
				int fillPattern = style.getFillPattern();
				if (logger) // 记录单元格的样式 
					
				if (
						fillForegroundColor != 43	// 定位背景色为黄色
//						cell.getColumnIndex() != 1 || 
//						cell.getColumnIndex() != 7
					) continue;
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BLANK : 
						//println("Cell is blank");
						break;
					case Cell.CELL_TYPE_BOOLEAN: 
						println(cell, cell.getBooleanCellValue(), logger);
						result.add(String.valueOf(cell.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_FORMULA: 
						println(cell, cell.getCellFormula(), logger);
						result.add(String.valueOf(cell.getBooleanCellValue()));
						break;
					case Cell.CELL_TYPE_NUMERIC: 
						println(cell, cell.getNumericCellValue(), logger);
						result.add(String.valueOf(cell.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING: 
						println(cell, cell.getRichStringCellValue().toString(), logger);
						result.add(String.valueOf(cell.getRichStringCellValue()));
						break;
					default: 
				}
			}
		}
		
		System.out.println("总记录数目: " + result.size());
	}
	
	private void println(Cell cell,Object object, boolean logger) {
		CellStyle style = cell.getCellStyle();
		if (logger)
			System.out.println(
					"FillBackgroundColor: " + style.getFillBackgroundColor() +  "\t" + 
					"FilleForegroundColor: " + style.getFillForegroundColor() +  "\t" + 
					"FillPattern: " + style.getFillPattern() +  
					"\t" + object);
		else System.out.println(object);
	}

	/**
	 * 从路径读取Excel文件返回 Workbook
	 * @param path	路径
	 * @return
	 */
	private Workbook readExcel(final String path) {
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(path);
			wb = WorkbookFactory.create(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return wb;
	}
	
	private static List<String> readXLS(String path, boolean debug) {
		if (path == null || path.trim().length() == 0 ) {
			System.out.println("Path is ERROR");
			return null;
		}
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(path);
			wb = WorkbookFactory.create(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		
		if (wb == null) {return null;}
		List<String> result = new ArrayList<String>();
		Sheet sheet = wb.getSheetAt(0);
		if(debug) System.out.println("SHEET NAME : " + sheet.getSheetName());
		for (Row row : sheet) {
			for (Cell cell : row) {
				CellStyle cs = cell.getCellStyle();
				if ( 
						(
								cell.getColumnIndex() == 1 || cell.getColumnIndex() == 2 ||
								cell.getColumnIndex() == 9 || cell.getColumnIndex() == 10
						 ) 	//定位第1 2 9 10列
						&& cs.getFillForegroundColor() == 43
						) {
//					if (cell.getColumnIndex() == 1 && cs.getFillBackgroundColor() == 64 && cs.getFillPattern() == 1 && cs.getFillForegroundColor() == 43) {
						switch (cell.getCellType()) {
							case  Cell.CELL_TYPE_STRING :
								if(debug) println("CELL_TYPE_STRING : " , cell,  cell.getRichStringCellValue());
								result.add(cell.getRichStringCellValue().getString());
								break;
							case  Cell.CELL_TYPE_NUMERIC :
								if (DateUtil.isCellDateFormatted(cell)) {
									if(debug) println("CELL_TYPE_NUMERIC WITH FORMATTED : ", cell, cell.getDateCellValue());
								} else {
									if(debug) println("CELL_TYPE_NUMERIC : " , cell, cell.getNumericCellValue());
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN : 
								if(debug) println("CELL_TYPE_BOOLEAN : ", cell, cell.getBooleanCellValue());break;
							case Cell.CELL_TYPE_FORMULA: 
								if(debug) println("CELL_TYPE_FORMULA : ", cell, cell.getCellFormula()); break;
							default : //System.out.println();
						}
				}
			}
		}
		return result;
	}
	
	private static int i = 0;
	
	private static void println(String info, Cell cell, Object value) {
		i++;
		System.out.println(
//				info + 
				"Column Index: " + cell.getColumnIndex() + 
				"\tFillBackgroundColor: " + cell.getCellStyle().getFillBackgroundColor() + 
				"\tFillForegroundColor: " + cell.getCellStyle().getFillForegroundColor() + 
				"\tFillPattern: " + cell.getCellStyle().getFillPattern() + 
				"\t\t" + value + "\t" + i);
	}	
}
