package com.tan.generate;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.print;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.DialogUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.StringUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/13 17:18:52
 */
public class Main {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static List<String> tableNamePool = new ArrayList<String>();
	private static Map<String, String> pool = new HashMap<String, String>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String currentPath = BaseUtil.getCurrentPath();

		// Clear arguments.
		pool.clear();
		tableNamePool.clear();

		// TODO delete.
//		currentPath = "C:\\tools\\Generate-Test-Sql";

		DialogUtil dialogUtil = new DialogUtil();

		File file = dialogUtil.chooseFile(
				"Select 単テ仕様書 File",
				"単テ仕様書_EA0660_不動産評価情報入力.xls", 
				new String[]{"*.xls"}, 
				currentPath + File.separatorChar + "単テ仕様書");

		// 単テ仕様書.
		File excelFile = getFile(file);

		// テーブル定義 dir.
		String directory = dialogUtil.chooseDirectory(
				"Select	論理テーブル定義",
				"論理テーブル定義", 
				currentPath + File.separatorChar
				+ "論理テーブル定義");
		
		File dataDir = getDir(directory);

		/** Read from the 単テ仕様書. **/
		readUTDBook(excelFile, dataDir);

		/*** Logger the files. **/
		// println("単テ仕様書:", excelFile);
		//
		// println("テーブル定義:", dataDir);
	}

	/**
	 * search the dir for the tableName and get the column's type.
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static String getColumnType(final String tableName, final String columnName, final File dir) {
		// final String path = "C:\\tools\\Generate-Test-Sql\\論理テーブル定義";

		if (StringUtil.isEmpty(tableName) || StringUtil.isEmpty(columnName)) {
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

				Sheet sheet = ExcelUtil.getSheetByNameAndIndex(wb, "論理テーブル定義【" + tableName + "】", 0);

				if (sheet == null) {
					println("未找到 Sheet");
					return null;
				}

				for (Row row : sheet) {
					for (Cell cell : row) {
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

		println("Not Found Table : ", tableName, ", Column: ", columnName);
		return null;
	}

	public static void saveColumnType(final String tableName, final File dir) {
		// final String path = "C:\\tools\\Generate-Test-Sql\\論理テーブル定義";

		if (StringUtil.isEmpty(tableName)) {
			println("参数有误");
		}

		// File dir = new File(path);

		FileList list = new FileList(dir, ".xls");
		List<File> results = list.getResults();

		int tableNameIdx = -1;
		// int rowIdx = 8;
		// int columnIdx = -1;
		for (File f : results) {
			String fileName = f.getName();

			tableNameIdx = fileName.indexOf(tableName);
			if (tableNameIdx >= 0) {
				// search the table's excel right.
				Workbook wb = ExcelUtil.getWorkbook(f);

				Sheet sheet = ExcelUtil.getSheetByNameAndIndex(wb, "論理テーブル定義【" + tableName + "】", 0);

				if (sheet == null) {
					println("未找到 Sheet");
				}

				for (Row row : sheet) {
					// 項目名.
					Cell columnCell = row.getCell(1);
					String column = ExcelUtil.getCellString(columnCell);
					// 属性.
					Cell typeCell = row.getCell(7);
					String type = ExcelUtil.getCellString(typeCell);
					pool.put(column, type);
				}

			}
		}
	}

	/**
	 * Read utd excel, and read the search the dir.
	 * 
	 * @param excelFile
	 * @param dir
	 */
	private static void readUTDBook(final File excelFile, final File dir) {
		Workbook wb = ExcelUtil.getWorkbook(excelFile);
		Sheet sheet = wb.getSheet("テストデータ");
		if (sheet == null)
			sheet = wb.getSheetAt(6);

		// check sheet.
		if (sheet == null) {
			exitWithMessage("Sheet {テストデータ} not found!");
		}

		int lastRowNum = sheet.getLastRowNum();

		println("LastRowNum:", lastRowNum);

		/**
		 * The argument.
		 */
		String tableName = "";// log the table's english name.
		String columnName = "";// log the column's english name.

		boolean loggerStyle = false;// when debug then log the style for the
									// cell.
		boolean loggerTable = false;// when debug then log the tables's
									// informations.
		boolean loggerColumn = false;// when debug then log the columns's
										// informations.

		int rowIdx = 1;
		for (; rowIdx < lastRowNum; rowIdx++) {
			Row row = sheet.getRow(rowIdx);
			if (row == null)
				continue;

			short lastCellNum = row.getLastCellNum();
			/**
			 * 1: ファイル. 2: コラム. 3. 値. 4. 値なし.
			 */

			Cell tableCell = row.getCell(0);
			String cell0String = ExcelUtil.getCellString(tableCell);

			if (tableCell != null) {
				CellStyle style = tableCell.getCellStyle();
				if (style != null) {
					int foregroundColor = style.getFillForegroundColor();

					if (loggerStyle)
						print(style.getFillForegroundColor(), "\t", style.getFillBackgroundColor(), "\tlastCellNum:"
								+ lastCellNum, "\t");

					switch (foregroundColor) {
					case 43: {
						if (cell0String.endsWith("ファイル") || cell0String.endsWith("マスタ")) {
							Cell tableWordCell = row.getCell(1);
							// log the table's english name.
							tableName = ExcelUtil.getCellString(tableWordCell);
							if (!tableNamePool.contains(tableName)) {
								tableNamePool.add(tableName);
								saveColumnType(tableName, dir);
							}

							if (loggerTable)
								print("Table:" + ExcelUtil.getCellString(tableCell) + '\t', "Table English:"
										+ tableName + '\t');

						}
						break;
					}
					case 42: {
						StringBuffer buf = new StringBuffer();
						// append the columns information.
						for (short cellIdx = 0; cellIdx < lastCellNum; cellIdx++) {
							Cell cell = row.getCell(cellIdx);
							String column = ExcelUtil.getCellString(cell);
							append(buf, column.replace("*", ""), ',');
							if (loggerColumn)
								print(column + '\t');
						}
						buf.deleteCharAt(buf.length() - 1);

						// set the value.
						columnName = buf.toString();
						break;
					}
					default: {
						print("INSERT INTO ", tableName, " (", columnName, ") VALUES (");
						StringBuffer buf = new StringBuffer();
						for (short cellIdx = 0; cellIdx < lastCellNum; cellIdx++) {
							Cell cell = row.getCell(cellIdx);

							/*** Check is date. **/
							Date date = null;
							try {
								date = cell.getDateCellValue();
							} catch (Exception e) {
								// ignore.
							}
							String value = null;
							if (date != null) {
								value = dateFormat.format(date);
							} else
								value = ExcelUtil.getCellString(cell);
							/*** Check is date. **/

							/*** Check and append the value for print. **/
							if (value != null && value.trim().length() != 0) {

								String type = pool.get(StringUtil.getIndexValue(cellIdx, ",", columnName));

								// check format is right.
								if (value.matches("\\d{4}/\\d{1,2}/\\d{1,2}"))
									value = value.replace('/', '-');
								// check is MUMERIC.
								if (type != null && "NUMERIC".equals(type))
									append(buf, value, ",");
								else
									append(buf, "'", value, "'", ",");
							} else {
								append(buf, "NULL", ",");
							}
							/*** Check and append the value for print. **/
						}
						buf.deleteCharAt(buf.length() - 1);
						buf.append(");");
						print(buf.toString() + '\t');
					}
					}
				}
			}
			println();
		}
	}

	/**
	 * check dierctory is right, if wrong then exit.
	 * 
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
	 * 
	 * @param excel
	 */
	private static File getFile(final File excel) {
		if (excel == null )
			exitWithMessage("路径为空.\r\nApp exit.");
		if (!excel.exists()) {
			exitWithMessage("文件不存在.\r\nApp exit.");
		} else if (excel.isDirectory()) {
			exitWithMessage("不是 一个文件.\r\nApp exit.");
		}
		return excel;
	}

	/**
	 * println the message and exit.
	 * 
	 * @param message
	 */
	private static void exitWithMessage(String message) {
		warnln(message);
		System.exit(0);
	}
}