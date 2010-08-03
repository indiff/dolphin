package com.tan.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.tan.bean.Table;
import com.tan.excel.util.Generator;

/**
 * 生成简单 Dao
 * @author dolphin
 *
 * 2010/04/28 10:18:59
 */
public class GenerateSimpleDaoByExcel {
	
	private static final String sourceExcel = "エンティティ定義書基地局竣工情報.xls";
	
	private static final String simpleDao = "simpleDao.xls";
	
	private static String primaryKey = null;
	
	public  static String tableName = null;
	
	private static final String dirPath  = "D:\\pswg-masserver\\020 ED\\03 DB\\エンティティー定義書\\";
	
	private static final String absoluteFile = dirPath + sourceExcel;
//	private static final String absoluteFile = GenerateSimpleDaoByExcel.class.getResource("").getPath() + sourceExcel;
	
	private static File dir = new File(dirPath);
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 处理多个文件
		File[] files = getExcels();
		
//		// 检查有多少张 Master 表
//		inspectMasterTable(files, generator);
//		// 检查一共有多少张表
//		inspectTables(files);
		
		
		// 处理多个Entity文件
		Generator generator = new Generator();
//		Map<String, List<Table>> map = generator.readFromExcel(files, false);
//		for (String file : map.keySet()) {
//			String fileName = file.substring(file.lastIndexOf("\\") + 1);
//			System.out.println(fileName.replaceAll("エンティティ定義書", ""));
//			write2excel(simpleDao, fileName, generator, map.get(file));
//		}
		
		
//		for (File f : files) {
//			Generator generator = new Generator();
//			List<Table> tables = generator.readFromExcel(f, false);
//			write2excel(simpleDao,f.getName(), generator, tables);
//		}
		
		// 处理单个Entity文件
		List<Table> tables = generator.readFromExcel(absoluteFile, false);
		write2excel(simpleDao, "xxx.xls", generator, tables);
		
//		System.out.println("文件生成成功" + map.keySet().size());
		
//		if (this.getTableName() != null && this.getTableName().length() != 0) {
//			wb.setSheetName(1, this.getTableName());
//		}
	}

	
	/**
	 * 检查一共有多少张表
	 * @param files
	 */
	private static void inspectTables(File[] files) {
		for (File f: files) {
			System.out.println(f);
		}
		System.out.println("一共有:" + files.length + "张表");
	}

	
	/**
	 * 检查有多少张Master表
	 * @param files
	 */
	private static void inspectMasterTable(File[] files, Generator generator) {
		int tableMaster = 0;
		for (File f : files) {
			if (f.toString().indexOf("マスタ") >= 0) {
				System.out.println(f.toString().replace("D:\\pswg-masserver\\020 ED\\03 DB\\エンティティー定義書\\エンティティ定義書", "") + "\t" + generator.inspectTableName(f, true));
				tableMaster++;
			}
		}
		System.out.println("Master 表有: " + tableMaster + "张表");
	}
	
	
	/**
	 * 通过模板 simpleDao.xls 文件生成 Excel 文件
	 * @param sourceExcel
	 * @param excelName
	 * @param tableName
	 * @param primaryKey
	 * @param daoInfos
	 */
	private static void write2excel(final String simpleDao,
									final String fileName,
									final Generator generator, 
									final List<Table> tables) {
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(GenerateSimpleDaoByExcel.class.getResource(simpleDao).getFile());
			wb = WorkbookFactory.create(in);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (wb == null) {
			return;
		}
		// 定位第二个 Sheet 页面
		Sheet sheet = wb.getSheetAt(1);
		wb.setSheetName(1, generator.getTableName());
		// 定位第一行
		Row row0 = sheet.getRow(0);
		// 设置 EntityName
		Cell entityCell = row0.getCell(3);
		entityCell.setCellValue(generator.getEntityName());
		
		// 设置表名 Cell
		Cell tableNameCell = row0.getCell(10);
		tableNameCell.setCellValue(generator.getTableName());
		
		// 定位第三行
		Row row2 = sheet.getRow(2);
		// 表名
		Cell c25 = row2.getCell(5);
		c25.setCellValue(tableName);
		int size = tables.size();
		Row lastRow = null;
		for (short i = 0; i < size; i++) {
			Table table = tables.get(i);
			Row row = sheet.getRow(i+2);
			// 标记最后一行
			if (i == size - 1) lastRow = row;
			
			// 属性名			
			Cell cellColumnName = row.getCell(1); cellColumnName.setCellValue(table.getLogicName());
			// カラム名
			Cell cellColumn = row.getCell(5);cellColumn.setCellValue(table.getPhysicalName());
			// 型
			Cell cellColumnType = row.getCell(11);cellColumnType.setCellValue(table.getType());
			// 整数桁
			Cell cellDigit = row.getCell(14);
			if (table.getLength() != 0.0) cellDigit.setCellValue(table.getLength());
			// 小数桁	
			Cell cellDecimal = row.getCell(16);
			if (table.getDecimal() != 0.0) cellDecimal.setCellValue(table.getDecimal());
			// PK --> ○
			if (table.isPrimaryKey()) {
				Cell cellPk = row.getCell(18);cellPk.setCellValue("○");
			}
			// UK
			Cell cellUk = row.getCell(19);	
			//TODO 设置唯一键  cellUk.setCellValue("UK");
			
			// NULL --> 不可
			if (table.isRequired()) {
				Cell cellNull = row.getCell(20);cellNull.setCellValue("不可");
			}
			
			// デフォルト値	
//			Cell cellDefault = row.getCell(21);cellDefault.setCellValue("デフォルト");
			// IDENTITY初期値
			Cell cellIdentity =  row.getCell(32);
			//TODO 设置 IDENTITY 开始值 cellIdentity.setCellValue("IDENTITY初期値");
			// IDENTITYインクリメント
			Cell cellIncrement =  row.getCell(36);
			
			//TODO  设置 IDENTITY 增量 cellIncrement.setCellValue("IDENTITYインクリメント");
		}
		
//		
//		int start = lastRow.getRowNum() + 1;
//		int end = sheet.getPhysicalNumberOfRows() - 16;
//		for (int i = start; i <=end; i++) {
//			sheet.removeRow(sheet.getRow(i));
//		}
		
		
//		Cell cell0 = row0.createCell(0);
//		cell0.setCellValue(tableName);
//		
//		Row row1 = sheet.createRow(1);
//		Cell cell1 = row1.createCell(0);
//		cell1.setCellValue(primaryKey);
//		
//		for (short i = 2; i < daoInfos.size(); i++) {
//			DaoInfo info = daoInfos.get(i - 2);
//			Row row = sheet.createRow(i);
//			Cell columnCell = row.createCell(0); columnCell.setCellValue(info.column);
//			Cell columnTypeCell = row.createCell(1);columnTypeCell.setCellValue(info.columnType);
//			Cell columnDefaultCell = row.createCell(2);columnDefaultCell.setCellValue(info.columnDefault);
//			Cell otherCell = row.createCell(3);otherCell.setCellValue(info.other);
//		}
//		
//		sheet.autoSizeColumn(0);
//		sheet.autoSizeColumn(1);
//		sheet.autoSizeColumn(2);
//		sheet.autoSizeColumn(3);
		
		//  write to a file
		FileOutputStream out = null;
		try {
			// TODO 处理生成的文件名称
			if (fileName.indexOf("エンティティ定義書") >= 0) {
				out = new FileOutputStream(fileName.replaceAll("エンティティ定義書", ""));
			} else {
				out = new FileOutputStream(fileName);
			}
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static File[] getExcels() {
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String fileName) {
				return fileName.toLowerCase().endsWith(".xls");
			}
		});
		return files;
	}

}
