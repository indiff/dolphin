package com.tan.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.Table;

/**
 * 
 * @author dolphin
 *
 * 2010/04/28 22:17:58
 */
public @Data class Generator {
	
	private String tableName;
	// エンティティ名 .
	private String entityName;
	
	
	
	public String inspectTableName(File file, boolean logger){
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(file);
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
			}
		}
		
		Sheet sheet = wb.getSheetAt(0);
		Row row = sheet.getRow(3);
		Cell cell = row.getCell(0);
		
		return cell.getRichStringCellValue().toString();
	}
	/**
	 * 
	 * @param file
	 * @param logger
	 * @return
	 */
	public 	List<Table> readFromExcel(File file, boolean logger) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Table> tables = new ArrayList<Table>();
		int numberOfSheets = wb.getNumberOfSheets();
		if (numberOfSheets == 0) {
			if (logger) System.out.println("文件没有 Sheet 页");return null;
		}
		Sheet sheet = wb.getSheetAt(0);
		
		// GET  エンティティ名 
		Row row2 = sheet.getRow(1);
		Cell cellEntity = row2.getCell(0);
		if (logger) System.out.println("エンティティ名:" + cellEntity.getStringCellValue());
		this.setEntityName(cellEntity.getStringCellValue().trim());
		
		// GET テーブル名
		Row row4 = sheet.getRow(3);
		Cell cellTable = row4.getCell(0);
		
		// GET スキーマ
		Cell cellSchema = row4.getCell(3);
		if (logger) System.out.println("テーブル名:" + cellTable.getStringCellValue() +
				"\r\nスキーマ:" + cellSchema.getStringCellValue());
		this.setTableName(cellTable.getStringCellValue().trim());
		
		if (logger) System.out.println(
				"論　理　名\t\t物　理　名\t\t型\t\t長さ\t\t精度\t\t必須\t\t主キー"
				);
		
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
		// TODO MAX ROWNUMBER
		for (short i = 7; i < physicalNumberOfRows; i++) {
			Row rowI = sheet.getRow(i);
			// break the row
			if (rowI.getCell(0).getCellType() != Cell.CELL_TYPE_NUMERIC) break;
			
			if (rowI != null ) {
				Table table = new Table();
				// 論　理　名
				Cell cellB = rowI.getCell(1); table.setLogicName(cellB.getRichStringCellValue().toString());
				// 物　理　名
				Cell cellC = rowI.getCell(2); table.setPhysicalName(cellC.getRichStringCellValue().toString());
				// 型
				Cell cellD = rowI.getCell(3); table.setType(cellD.getRichStringCellValue().toString());
				// 長さ
				Cell cellE = rowI.getCell(4); table.setLength(cellE.getNumericCellValue());
				// 精度
				Cell cellF = rowI.getCell(5); table.setDecimal(cellF.getNumericCellValue());
				// 必須
				Cell cellG = rowI.getCell(6); 
				if (cellG.getCellType() == Cell.CELL_TYPE_STRING && "Y".equals(cellG.getRichStringCellValue().toString())) {
					table.setRequired(true);
				} else table.setRequired(false);
				// 主キー
				Cell cellH = rowI.getCell(7); 
				if (cellH.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					table.setPrimaryKey(true);
				}
					
				// 定　義　内　容
				Cell cellI = rowI.getCell(8); table.setType(cellD.getRichStringCellValue().toString());
				if (cellI.getCellType() == Cell.CELL_TYPE_STRING && cellI.getRichStringCellValue().toString().trim().length() != 0) {
					table.setDefinition(cellI.getRichStringCellValue().toString());
				}
				
				// 如果論　理　名没有值得话就挂掉, 退出循环
				if (cellB.toString().trim().length() == 0) {System.out.println(logger ? "退出循环" : "");break;}
				
				if (logger) 
				System.out.println(
						cellValue(cellB) + "\t\t\t" + cellValue(cellC) + "\t\t\t" + 
						cellValue(cellD) + "\t\t" + cellValue(cellE) + "\t\t" + 
						cellValue(cellF) + "\t\t" + cellValue(cellG) + "\t\t" + 
						cellValue(cellH) //(cellH.getNumericCellValue() == 1.0)
				);
				tables.add(table);
			}
		}
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 遍历
		if (true) System.out.println(file.getAbsolutePath() + "\t遍历成功");
		return tables;
	}
	
	/**
	 * @param files
	 * @param logger
	 * @return
	 */
	@SuppressWarnings("unused")
	private Map<String,List<Table>> readFromExcel(String[] files, boolean logger) {
		// check arguments for files
		if (files == null || files.length == 0) {
			return null;
		}
		Map<String,List<Table>> map = new HashMap<String, List<Table>>();
		for (String file : files) {
			map.put(file, readFromExcel(file, logger));
		}
		return map;
	}
	
	/**
	 * 
	 * @param files
	 * @param logger
	 * @return
	 */
	public Map<String,List<Table>> readFromExcel(File[] files, boolean logger) {
		// check arguments for files
		if (files == null || files.length == 0) {
			return null;
		}
		Map<String,List<Table>> map = new HashMap<String, List<Table>>();
		for (File file : files) {
			map.put(file.getAbsolutePath(), readFromExcel(file, logger));
		}
		return map;
	}
	
	/**
	 * 
	 * @param absoluteFile
	 * @param logger
	 * @return
	 */
	public  List<Table> readFromExcel(String absoluteFile, boolean logger) {
		return readFromExcel(new File(absoluteFile), logger);
	}
	

	/**
	 * Get the cell value
	 * @param cell
	 * @return
	 */
	private Object cellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:  return "";
			case Cell.CELL_TYPE_BOOLEAN: return cell.getBooleanCellValue();
			case Cell.CELL_TYPE_FORMULA: return cell.getCellFormula();
			case Cell.CELL_TYPE_NUMERIC: return cell.getNumericCellValue();
			case Cell.CELL_TYPE_STRING: return cell.getRichStringCellValue().toString();
			default: return null;
		}
	}
}
