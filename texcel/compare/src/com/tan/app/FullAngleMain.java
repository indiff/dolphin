package com.tan.app;

import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.showInputDialog;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.io.FileList;
import com.tan.qa.BaseUtil;
import com.tan.util.ExcelUtil;
import com.tan.util.TanUtil;

/**
 * Check Excel全角.
 * @author dolphin
 *
 * 2010/07/12 13:54:11
 */
public class FullAngleMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String currentPath = BaseUtil.getCurrentPath();
		
		// config setting.
//		String[] extensions = ConfigUtil.config2string().split("#");
		
		currentPath = showInputDialog("请输入文件路径", currentPath);
		FileList list =  new FileList(new File(currentPath), ".xls");
		
		List<File> excels = list.getResults();
		int size = 0;
		if (excels == null || (size = excels.size()) == 0) {
			BaseUtil.exitWithMessage("Dir is empty with xls\t", currentPath);
		}
		
		warnln("Checking ....");
		warnln("文件个数:" + size);
		
		if (TanUtil.isEmpty(excels)){
			println("未找到Excel文件");System.exit(0);
		}
		StringBuilder buf = new StringBuilder();
	
		for (File excel : excels) {
			Workbook wb = ExcelUtil.getWorkbook(excel);
			int number = wb.getNumberOfSheets();
			
			String fileNameWarn = BaseUtil.checkFullAngle(excel.getAbsolutePath());
			
			if (fileNameWarn != null && fileNameWarn.length() != 0) {
				append(buf, "文件名Check:\r\n", excel.getAbsolutePath(), "\r\n",  fileNameWarn, "\r\n");
			}
			
			for (int i = 0 ; i < number; i++) {
				Sheet sheet = wb.getSheetAt(i);
				if (sheet == null) continue;
				String sheetName = sheet.getSheetName();
				String sheetNameWarn = BaseUtil.checkFullAngle(sheetName);
				if (sheetNameWarn != null && sheetNameWarn.length() != 0)
					append(buf, "Sheet名Check:\r\nSheet{",  sheetName, "}\r\n\t", sheetNameWarn, "\r\n");
				
				int rowNum = sheet.getLastRowNum();
				for (int rowIdx = 0 ; rowIdx < rowNum; rowIdx++){
					Row row = sheet.getRow(rowIdx);
					
					if (row == null) continue;
					
					int cellNum = row.getLastCellNum();
					
					for(int cellIdx = 0; cellIdx < cellNum; cellIdx++) {
						Cell cell = row.getCell(cellIdx);
						String value = getCellString(cell, wb);
						String cellValueWarn = BaseUtil.checkFullAngle(value);
						if (cellValueWarn != null && cellValueWarn.length() != 0)
							append(buf,"Cell名Check:\r\n", excel.getAbsolutePath(), "\r\nSheet:", sheetName + "[RowIndex: " + (rowIdx + 1) + ", CellIndex: " + (cellIdx + 1)+ " ]", "\r\n", cellValueWarn, "\r\n");
					}
				}
			}
		}
		
		println(buf);
		
		println("文件个数:" + size);
		if (buf.length() == 0) {
			println("未找到全角信息");
		}
		warnln("App Over See The log fullAngleMain.log");
	}

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
