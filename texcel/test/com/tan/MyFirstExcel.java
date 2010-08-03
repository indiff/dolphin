package com.tan;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyFirstExcel extends TestCase{
	
	private final String path = "C:\\Users\\";
	/**
	 * 创建一个Excel
	 */
	public void testCeateExcel() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		
		// create a row and put some cells in it. Rows are 0 based;
		Row row = sheet.createRow((short) 0);
		
		// Agua background
		CellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
		style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		Cell cell = row.createCell((short)0 );
		cell.setCellValue("x");
		cell.setCellStyle(style);
		
		// write the out to a file
		FileOutputStream out = new FileOutputStream(path + "first.xls");
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * Merging cells
	 * 合并单元格
	 */
	public void testMergingCells() throws Exception{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("合并单元格测试");
		Row row = sheet.createRow((short) 1);
		Cell cell = row.createCell((short) 1);
		cell.setCellValue("这是一个例子测试合并单元格 merging cells");
		
		sheet.addMergedRegion(new CellRangeAddress(
				1, // first row (0-based) 
				1, // last row (0-based)
				1, // first column (0-based)
				2 // last columjn (0-based)
		));
		
		
		// write the output to a file
		FileOutputStream out = new FileOutputStream(path + "merging_cells.xls");
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * 设置字体
	 * Working with font 
	 */
	public void testWorkingWithFont() throws Exception {
		// HSSF horrible spreadSheet 
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("设置字体");
		
		// create a row and put some cells in it
		Row row = sheet.createRow((short) 0);
		
		// crate a new font and alert it
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 0);
		font.setFontName("Courier New");
		font.setBoldweight((short) 2);
		// Fonts are set into a style so create a new one to use
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		
		// create a cell and put a value in it.
		for (byte i = 0; i < 10; i++) {
			Cell cell = row.createCell((short) i);
			cell.setCellValue("字体" + i);
			cell.setCellStyle(style);
		}
		
		// write the output to a file
		FileOutputStream out = new FileOutputStream(path + "font.xls");
		wb.write(out);
		out.close();
	}
	
	/**
	 * 自定义颜色
	 * Custom colors
	 */
	public void testCustomColors() throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow((short) 0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("Default Palette");
		
		// apply some colors from the standard palette
		// as in the previous example.
		// we'll use red text on a linme background
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIME.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		Font font = wb.createFont();
		font.setColor(IndexedColors.RED.getIndex());
		style.setFont(font);
		
		cell.setCellStyle(style);
		
		// save with the default palette
		FileOutputStream out = new FileOutputStream(path + "custom-colors.xls");
		wb.write(out);
		out.close();
		
		// now, lets replace RED and LIME in the palette
		// with a more attractive combination
		// (logingly borrowed from freebsd.org)
		cell.setCellValue("Modified Palette");
		
		// creating a custom palette for the workbook
		HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex(IndexedColors.RED.getIndex(),
				(byte) 153,	// RGB red (0 - 255)
				(byte) 0,	// RGB green
				(byte) 0	// RGB blue
		);
		
		// replacing lime with freebsd.org gold
		palette.setColorAtIndex(IndexedColors.LIME.getIndex(),
				(byte) 255,
				(byte) 204,
				(byte) 102
		);
		
		out = new FileOutputStream(path + "modified_palette.xls");
		wb.write(out);
		out.close();
		out = null;
	}
	
	/**
	 * 替换
	 * @throws Exception
	 */
	public void testShiftRowsUpOrDownOnSheet() throws Exception{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("shift rows up or down on the sheet");
		
		for (short i = 0; i < 10; i++) {
			Row row = sheet.createRow(i);
			Cell cell = row.createCell((short) 0);
			cell.setCellValue(i);
		}
		/**
		 * 将 0 到 2 的内容替换掉第5行内容
		 * 0			
		 * 1			
		 * 2			
		 * 3	-->		0
		 * 4			1
		 * 5			2
		 */
		sheet.shiftRows(0, 2, 3);
		
		writeWorkbook(wb, path + "shift-rows.xls");
	}
	
	/**
	 * 选中一个 sheet
	 */
	public void testSelectSheet() throws Exception {
		Workbook wb = new HSSFWorkbook();
		wb.createSheet("first sheet");
		Sheet sheet2 = wb.createSheet("second sheet");
		Row row = sheet2.createRow(0);
		row.createCell(0).setCellValue("I am in the second sheet");
		
		/**
		 * 设置选中的
		 */
		sheet2.setSelected(false);
		
		writeWorkbook(wb, path + "select-sheet.xls");
	}
	
	
	/**
	 * 设置扩大率
	 */
	public void setZoomMagnification() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("Zoom magnification");
		sheet.setZoom(3,4); // 75 percent magnification
		
		writeWorkbook(wb, path + "zoom-magnification.xls");
	}
	
	/**
	 * 创建 splits 类型或者 freeze pane
	 * @throws Exception
	 */
	public void testSplitsAndFreezePanes() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet1 = wb.createSheet("first sheet");
		Sheet sheet2 = wb.createSheet("second sheet");
		Sheet sheet3 = wb.createSheet("third sheet");
		Sheet sheet4 = wb.createSheet("fourth sheet");
		// freeze just one row
		sheet1.createFreezePane(0,1,0,1);
		
		// freeze just one column
		sheet2.createFreezePane(1, 0, 1, 0);
		
		// freeze the columns and rows ( forget about scolling position of the lower right quadrant )
		sheet3.createFreezePane(2,2);
		
		// create a split with the lower left side being the active quadrant
		sheet4.createSplitPane(2000, 2000, 0, 0 , Sheet.PANE_LOWER_LEFT);
		writeWorkbook(wb, path + "splits-freeze-panes.xls");
	}
	
	/**
	 * 
	 */
	public void testRepeatingRowsAndColumns() throws Exception {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet1 = wb.createSheet("first sheet"); 
		setValuesInSheet(sheet1);
		Sheet sheet2 = wb.createSheet("second sheet");
		setValuesInSheet(sheet2);
		
		sheet1.autoSizeColumn(0);
		
		sheet2.autoSizeColumn(0);
		// set the columns to repeat from column 0 to 2 on the first sheet
		wb.setRepeatingRowsAndColumns(0, 0, 2, 0, 0);
		
		// set the repeating rows and columns on the second sheet
		wb.setRepeatingRowsAndColumns(1, 0, 0, 0, 2);
		
		// write it the a file
		writeWorkbook(wb, path + "repeat-rows-columns.xls");
	}
	
	/**
	 * write ten values in the sheet
	 * @param sheet
	 * @throws Exception
	 */
	private void setValuesInSheet(Sheet sheet) throws Exception{
		for (short i = 0; i < 10; i++) {
			Row row = sheet.createRow(i);
			Cell cell = row.createCell(0);
			cell.setCellValue("value in " + sheet.getSheetName() + i);
		}
	}
	
	
	
	/**
	 * 按path路径写入一个excel 文件
	 * @param wb	Workbook
	 * @param path	path 绝对路径
	 * @throws Exception
	 */
	private void writeWorkbook(Workbook wb,String path) throws Exception {
		FileOutputStream out = new FileOutputStream(path);
		wb.write(out);
		out.close();
	}
	
	public void testAllAutoSizeColumn() throws Exception {
		FileInputStream in = new FileInputStream(path + "demo.xls");
		Workbook wb = WorkbookFactory.create(in);
		int number = wb.getNumberOfSheets();
		// System.out.println(number);
		for (short i = 0 ; i < number; i++) {
			autoSizeColumn(wb.getSheetAt(i));
		}
		in.close();
		
		writeWorkbook(wb, path + "進捗管理表2.xls");
	}
	
	/**
	 * 设置所有的列 autoSizeColumn 
	 */
	private void autoSizeColumn(Sheet sheet) {
		short colNum = 0;
//		System.out.println("Last row number : " + sheet.getLastRowNum());
//		Row row = sheet.getRow(1);
//		if (row != null)
//			System.out.println("Last cell number : " + row.getLastCellNum());
		for (Row row : sheet) {
			for (Cell cell : row) {
				cell = null;
				colNum++;
				System.out.println(colNum + " In the cell loops");
				sheet.autoSizeColumn(colNum);
			}
//			colNum = 0;
			break;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

