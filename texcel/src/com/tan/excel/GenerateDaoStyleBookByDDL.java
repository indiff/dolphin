package com.tan.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 生成简单 Dao
 * @author dolphin
 *
 * 2010/04/28 10:18:59
 */
public class GenerateDaoStyleBookByDDL {
	// DLL 文件
	private static final String fileName = "ddl.txt";
	
	// Excel 文件
	private static final String excelName = "first.xls";
	
	// SimpleDao Excel 文件
	private static final String sourceExcel = "simpleDao.xls";
	
	// 主键
	private static String primaryKey = null;
	
	// 表名
	public  static String tableName = null;
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(GenerateSimpleDao.class.getResource(fileName));
		InputStream in = GenerateDaoStyleBookByDDL.class.getResourceAsStream(fileName);
		String ddl = readFromTxt(in);
		
		// close the input stream
		try {
			if (in != null) in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[] ddlSegments = ddl.split("\r\n");
		
		if (ddlSegments != null && ddlSegments.length > 0) {
			String tableName = ddlSegments[0].substring(ddlSegments[0].indexOf("TABLE ") + 6);
			GenerateDaoStyleBookByDDL.tableName = tableName;
			//System.out.println("表名 :" + GenerateSimpleDao.tableName);
		}
		
		List<DaoInfo> daoInfos = parseColumns0(ddlSegments, true);
		
		/**
		 * 
		 * 
		 * DEBUG INFORMATION START 
		 * 
			for (DaoInfo info : daoInfos) {
				if (info.column == null) continue;
				System.out.println(
						info.column + '\t' + 
						info.columnType + '\t' + 
						(info.columnDefault == null ? "" : info.columnDefault) + '\t' + 
						info.other.trim()
					);
			}
		 * DEBUG INFORMATION END  
		 * **/
		
//		for (short i = 0 ; i < ddlSegments.length ; i++) {
//			System.out.println(i + "-->" + ddlSegments[i]);
//		}
		
//		write2excel("test.xls", GenerateSimpleDao.tableName, GenerateSimpleDao.primaryKey, daoInfos);
		write2excel(sourceExcel, "generator.xls", GenerateDaoStyleBookByDDL.tableName, GenerateDaoStyleBookByDDL.primaryKey, daoInfos);
		System.out.println("文件生成成功");
	}
	
	/**
	 * 创建一个新文件
	 * @param excelName
	 * @param tableName
	 * @param primaryKey
	 * @param daoInfos
	 */
	private static void write2excel(String excelName,String tableName, String primaryKey, List<DaoInfo> daoInfos) {
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("TABLE INFO");
		// 第一行
		Row row0 = sheet.createRow(0);
		
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue(tableName);
		
		Row row1 = sheet.createRow(1);
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(primaryKey);
		
		for (short i = 2; i < daoInfos.size(); i++) {
			DaoInfo info = daoInfos.get(i - 2);
			Row row = sheet.createRow(i);
			Cell columnCell = row.createCell(0); columnCell.setCellValue(info.column);
			Cell columnTypeCell = row.createCell(1);columnTypeCell.setCellValue(info.columnType);
			Cell columnDefaultCell = row.createCell(2);columnDefaultCell.setCellValue(info.columnDefault);
			Cell otherCell = row.createCell(3);otherCell.setCellValue(info.other);
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		
		// write to a file
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(excelName);
			wb.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 通过模板 simpleDao.xls 文件生成 Excel 文件
	 * @param sourceExcel
	 * @param excelName
	 * @param tableName
	 * @param primaryKey
	 * @param daoInfos
	 */
	private static void write2excel(String sourceExcel,
									String excelName,
									String tableName, 
									String primaryKey, 
									List<DaoInfo> daoInfos) {
		InputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(GenerateDaoStyleBookByDDL.class.getResource(sourceExcel).getFile());
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
		// 定位第一行
		Row row0 = sheet.getRow(0);
		
		
		// 设置表名 Cell
		Cell tableNameCell = row0.getCell(10);
		tableNameCell.setCellValue(tableName);
		
		
		// 定位第三行
		Row row2 = sheet.getRow(2);
		// 表名
		Cell c25 = row2.getCell(5);
		c25.setCellValue(tableName);
		
		for (short i = 0; i < daoInfos.size(); i++) {
			DaoInfo info = daoInfos.get(i);
			Row row = sheet.getRow(i+2);
			
			// 属性名			
			Cell cellColumnName = row.getCell(1); cellColumnName.setCellValue("属性名");
			// カラム名
			Cell cellColumn = row.getCell(5);cellColumn.setCellValue(info.column);
			// 型
			Cell cellColumnType = row.getCell(11);cellColumnType.setCellValue(info.columnType);
			// 整数桁
			Cell cellDigit = row.getCell(14);cellDigit.setCellValue("整数桁");
			// 小数桁	
			Cell cellDecimal = row.getCell(16);cellDecimal.setCellValue("小数桁");
			// PK --> ○
			if (primaryKey.equals(info.column)) {
				Cell cellPk = row.getCell(18);cellPk.setCellValue("○");
			}
			// UK
			Cell cellUk = row.getCell(19);cellUk.setCellValue("UK");
			// NULL --> 不可
			if ("NOT NULL".equals(info.other)) {
				Cell cellNull = row.getCell(20);cellNull.setCellValue("不可");
			}
			
			// デフォルト値	
			Cell cellDefault = row.getCell(21);cellDefault.setCellValue("デフォルト");
			// IDENTITY初期値
			Cell cellIdentity =  row.getCell(32);cellIdentity.setCellValue("IDENTITY初期値");
			// IDENTITYインクリメント
			Cell cellIncrement =  row.getCell(36);cellIncrement.setCellValue("IDENTITYインクリメント");
		}
//		
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
			out = new FileOutputStream(excelName);
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
	
	private static List<DaoInfo> parseColumns0(String[] ddlSegments, boolean logger) {
		List<DaoInfo> daoInfos = new ArrayList<DaoInfo>();
		for (short i = 0 ; i < ddlSegments.length ; i++) {
			if (
					ddlSegments[i].indexOf("CREATE TABLE") >= 0  || // 跳过 CREATE 语法
					ddlSegments[i].indexOf('(') == 0	|| // 表信息开始
					ddlSegments[i].indexOf(')') == 0	|| // 表信息开始
					ddlSegments[i].indexOf("go") == 0 || ddlSegments[i].indexOf("GO") == 0 || // 跳过 GO 语法
					ddlSegments[i].indexOf("ALTER TABLE") >= 0 // 跳过 ALTER 语法 
				) {
				continue;
			}
			DaoInfo info = new DaoInfo();
			int primaryKeyIdx = ddlSegments[i].indexOf(" PRIMARY KEY" );
			if (primaryKeyIdx >= 0) {
				if(logger) System.out.println("主键 :" + ddlSegments[i].substring(primaryKeyIdx + 15, ddlSegments[i].length() - 1));
				primaryKey = ddlSegments[i].substring(primaryKeyIdx + 15, ddlSegments[i].length() - 1);
			} else {
				StringTokenizer tokenizer = new StringTokenizer(ddlSegments[i], " ,");
				byte sqlTypeFlg = 0;
				int startIndex = -1;
				int endIndex = -1;
				while(tokenizer.hasMoreTokens()) {
					// 字符串分析器
					String token = tokenizer.nextToken();
					// 标志位
					sqlTypeFlg++;
					// 第一个位置是字段名
					if (sqlTypeFlg == 1) {
						if(logger)System.out.print("字段 :" + token);
						info.column = token;
					} else if (sqlTypeFlg == 2) {// 第二个字段是字段类型
						if(logger) System.out.print("\t字段类型 :" + token);
						info.columnType = token;
					} else if (token.indexOf("NOT") >= 0) {
						info.other = "NOT NULL";
						endIndex = ddlSegments[i].indexOf("NOT");
						if (logger) System.out.print("\t其他:" + info.other);
						break;
					} else if(token.indexOf("NULL") >= 0) {
						info.other = "NULL";
						endIndex = ddlSegments[i].indexOf("NULL");
						if (logger) System.out.print("\t其他:" + info.other);
						break;
					}
				}
				
				// 默认值的信息
				startIndex = ddlSegments[i].indexOf(info.columnType);
				String middle = ddlSegments[i].substring(startIndex + info.columnType.length() , endIndex);
				if (middle.indexOf("DEFAULT") >= 0) {
					info.columnDefault =  middle.trim().replace("NOT", "");
					if (logger) System.out.print("\t默认值:" + info.columnDefault);
				}
				
				if (logger)System.out.println();
			}
			daoInfos.add(info);
		}
		return daoInfos;
	}
	
	/**
	 * 将 字符串数组转换成List类型的 DaoInfo
	 * @param ddlSegments
	 * @param logger
	 * @return
	 */
	private static List<DaoInfo> parseColumns(String[] ddlSegments, boolean logger) {
		List<DaoInfo> daoInfos = new ArrayList<DaoInfo>();
		for (short i = 0 ; i < ddlSegments.length ; i++) {
			if (
					ddlSegments[i].indexOf("CREATE TABLE") >= 0  || // 跳过 CREATE 语法
					ddlSegments[i].indexOf('(') == 0	|| // 表信息开始
					ddlSegments[i].indexOf(')') == 0	|| // 表信息开始
					ddlSegments[i].indexOf("go") == 0 || ddlSegments[i].indexOf("GO") == 0 || // 跳过 GO 语法
					ddlSegments[i].indexOf("ALTER TABLE") >= 0 // 跳过 ALTER 语法 
				) {
				continue;
			}
			DaoInfo info = new DaoInfo();
			int primaryKeyIdx = ddlSegments[i].indexOf(" PRIMARY KEY" );
			if (primaryKeyIdx >= 0) {
				if(logger) System.out.println("主键 :" + ddlSegments[i].substring(primaryKeyIdx + 15, ddlSegments[i].length() - 1));
				primaryKey = ddlSegments[i].substring(primaryKeyIdx + 15, ddlSegments[i].length() - 1);
			} else {
				StringTokenizer tokenizer = new StringTokenizer(ddlSegments[i], " ,");
				byte sqlTypeFlg = 0;
				int count = tokenizer.countTokens();
				boolean defaultFlg = false;
				StringBuilder defaultInfo = new StringBuilder();
				StringBuilder endInfo = new StringBuilder();
				while(tokenizer.hasMoreTokens()) {
					// 字符串分析器
					String token = tokenizer.nextToken();
					// 标志位
					sqlTypeFlg++;
					// 第一个位置是字段名
					if (sqlTypeFlg == 1) {
						if(logger)System.out.print("字段 :" + token);
						info.column = token;
					} else if (sqlTypeFlg == 2) {// 第二个字段是字段类型
						if(logger) System.out.print("\t字段类型 :" + token);
						info.columnType = token;
					} else if (sqlTypeFlg > 2 && token.indexOf("DEFAULT") >= 0 && !defaultFlg) {
						// 遍历 default 值
						defaultInfo.append(token);
						defaultFlg = true; continue;
					} else if (defaultFlg) defaultInfo.append(' ' + token) ;
					else {
						// 遍历其他信息
						endInfo.append(' ' + token);
					}
					
					/**
					 * 将默认值赋值给 DaoInfo
					 */
					if ( defaultInfo != null && 
							defaultInfo.toString().trim().length() != 0){ 
						if(logger) System.out.print("\t默认值:" + defaultInfo); 
						info.columnDefault = defaultInfo.toString();
						defaultInfo = null;
					}
					
					defaultFlg = false;
					
					/**
					 * 将Other赋值给 DaoInfo
					 */
					if (sqlTypeFlg == count && endInfo.toString().trim().length() != 0) {
						if(logger) System.out.print("\t其他信息:" + endInfo.toString().trim());
						info.other = endInfo.toString().trim();
					}
					
				}
				if (logger)System.out.println();
			}
			daoInfos.add(info);
		}
		return daoInfos;
	}

	/**
	 * read ddl from txt file
	 * @param in
	 * @return
	 */
	private static String readFromTxt(InputStream in) {
		return readFromTxt(in, Charset.defaultCharset().name());
	}
	
	
	/**
	 * read ddl from txt file, and by the encoding
	 * @param in
	 * @param encoding
	 * @return
	 */
	private static String readFromTxt(InputStream in, String encoding) {
		if (in == null || !Charset.isSupported(encoding)) return "";
		StringBuilder builder = new StringBuilder();
		int l = -1;
		byte[] buf = new byte[2049];
		try {
			while ( ( l = in.read(buf, 0, buf.length)) != -1) {
				builder.append(new String(buf, 0, l, encoding));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

}
