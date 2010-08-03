package com.tan.excel;

import static com.tan.util.TanUtil.print;
import static com.tan.util.TanUtil.println;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.record.SelectionRecord;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.tan.util.ExcelUtil;

public class WrokbookTest extends TestCase{
	private final String path = "C:\\Documents and Settings\\Administrator\\デスクトップ\\work-softbank\\0423\\";
	
	
	public void testWorkingWithDifferentTypesOfCells() throws Exception{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		Row row = sheet.createRow((short)2);
		row.createCell(0).setCellValue(1.1);
		row.createCell(1).setCellValue(new Date());
		row.createCell(2).setCellValue(Calendar.getInstance());
		row.createCell(3).setCellValue("A string");
		row.createCell(4).setCellValue(true);
		
		// write the output to a file
		FileOutputStream fileOut = new FileOutputStream(path + "diffCells.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	public void testDemonstratesVariousAlignmentOptions() throws Exception{
		Workbook wb = new HSSFWorkbook();
		
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow((short) 2);
		row.setHeightInPoints(30);
		
		
		createCell(wb, row, (short) 0, HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short) 1, HSSFCellStyle.ALIGN_CENTER_SELECTION, HSSFCellStyle.VERTICAL_BOTTOM);
		createCell(wb, row, (short) 2, HSSFCellStyle.ALIGN_FILL, HSSFCellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short) 3, HSSFCellStyle.ALIGN_GENERAL, HSSFCellStyle.VERTICAL_CENTER);
		createCell(wb, row, (short) 4, HSSFCellStyle.ALIGN_JUSTIFY, HSSFCellStyle.VERTICAL_JUSTIFY);
		createCell(wb, row, (short) 5, HSSFCellStyle.ALIGN_LEFT, HSSFCellStyle.VERTICAL_TOP);
		createCell(wb, row, (short) 6, HSSFCellStyle.ALIGN_RIGHT, HSSFCellStyle.VERTICAL_TOP);
		
		// write it to a file
		FileOutputStream out = new FileOutputStream(path + "hssf-align.xls");
		wb.write(out);
		out.close();
	}
	
	
	public void testIterateOverRowAndCells() throws Exception {
		Workbook wb = new HSSFWorkbook(new FileInputStream(path + "demo.xls"));
		Sheet sheet = wb.getSheetAt(0);
		for (Iterator<Row> iter = sheet.rowIterator(); iter.hasNext();){
			Row row = iter.next();
			for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
				Cell cell = (Cell)cit.next();
				System.out.println(cell);
			}
		}
		
		// also can use java 1.5 foreach loops 
		for (Row row : sheet) {
			for (Cell cell : row) {
				System.out.println(cell);
			}
		}
		
	}
	
	
	public void testGettingCellContents() throws Exception{
		Workbook wb = new HSSFWorkbook(new FileInputStream(path + "demo.xls"));
		Sheet sheet = wb.getSheetAt(0);
		for (Row row : sheet) {
			for (Cell cell : row){ 
				switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING : 
						System.out.println(cell.getRichStringCellValue().getString());break;
					case Cell.CELL_TYPE_NUMERIC :
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.println(cell.getDateCellValue());
						} else {
							System.out.println(cell.getNumericCellValue());
						}break;
					case Cell.CELL_TYPE_BOOLEAN : 
						System.out.println(cell.getBooleanCellValue());break;
					case Cell.CELL_TYPE_FORMULA: 
						System.out.println(cell.getCellFormula());break;
						default : System.out.println();
				}
			}
		}
	}
	
	/**
	 * 导出文本的内容
	 */
	public void testTextExtraction() throws Exception {
		FileInputStream in = new FileInputStream("c:\\demo.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		ExcelExtractor extractor = new ExcelExtractor(wb);
		
		extractor.setFormulasNotResults(true);
		extractor.setIncludeSheetNames(true);
		
		Sheet sheet = wb.getSheetAt(0);
		
		String text = extractor.getText();
		
		System.out.println(text);
		
	}
	
	
	/**
	 * 测试填充和字体的设置
	 */	
	public void testFillsAndColors() throws Exception {
		FileInputStream in = new FileInputStream(path + "demo.xls");
		Workbook wb = new HSSFWorkbook(in);
		Sheet sheet = wb.getSheetAt(0);
		
		// create a row and put some cells in it. Rows are 0 based;
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 24);
		font.setFontName("FixedSys");
		font.setItalic(true);
		font.setStrikeout(true);
		
		// fonts are set into a style so create a new one to use
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		
		
		for (Row row : sheet) {
			for (Cell cell : row) {
				cell.setCellStyle(style);
			}
		}
		FileOutputStream out = new FileOutputStream(path + "demo1.xls");
		wb.write(out);
		in.close();
		out.close();
		
		
	}
	
	
	/**
	 * 在第三行， 第三列上写入一段字符串，使得该单元格能够容纳2行的内容 
	 */
	public void testUsingNewlinesInCells() throws Exception{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		Row row = sheet.createRow(2);
		Cell cell = row.createCell(2);
		cell.setCellValue("Use \n with word wrap on to create a new line");
		
		// to enable newlines you need set a cell styles with wrap(自动换行 ) = true
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);
		cell.setCellStyle(cs);
		
		// increase row height to accomodate(适应，容纳)  two lines of text
		row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
		
		// adjust(调整) column width to fit the content
		// 使得单元格的内容能够容纳
		sheet.autoSizeColumn((short) 2);
		
		FileOutputStream fileOut = new FileOutputStream(path + "demo-newlines.xls");
		wb.write(fileOut);
		fileOut.close();
		
	}
	
	
	/**
	 * 设置数据的格式
	 */
	 public void testDataFormat() throws Exception  {
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet("format sheet");
		 CellStyle style;
		 DataFormat format = wb.createDataFormat();
		 Row row ;
		 Cell cell;
		 short rowNum = 0;
		 short columnNum = 0;
		 
		 // 设置一个单精度个格式 0.0 
		 // set a  data format for the cell which is float content
		 row = sheet.createRow(rowNum++);
		 cell = row.createCell(columnNum);
		 cell.setCellValue(11111.25);
		 style = wb.createCellStyle();
		 style.setDataFormat(format.getFormat("0.0"));
		 cell.setCellStyle(style);
		 
		 // 设置一个单精度格式  #,##0.0000
		 row = sheet.createRow(rowNum++);
		 cell = row.createCell(columnNum);
		 cell.setCellValue(11111.25);
		 style = wb.createCellStyle();
		 style.setDataFormat(format.getFormat("#,##0.0000"));
		 
		 
		 FileOutputStream fileOut = new FileOutputStream(path + "demo-format.xls");
		 wb.write(fileOut);
		 fileOut.close();
		 
	 }
	 
	/**
	 * Fit sheet to one page
	 */
	 public void testFitSheetToOnePage() throws Exception {
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet();
		 PrintSetup ps = sheet.getPrintSetup();
		 
		 sheet.setAutobreaks(true);
		 
		 ps.setFitHeight((short)1);
		 ps.setFitWidth((short)1);
		 
		 // 为电子表格创建多种但 cell 和 row
		 // create various(多种多样的)  cells and rows for speadsheet(电子表格)
		 FileOutputStream out = new FileOutputStream(path + "demo-filt-sheet.xls");
		 wb.write(out);
		 out.close();
	 }
	 
	 
	/**
	 * 重复的行和列
	 */
	 public void testRepeatingRowsAndColumns() throws Exception{
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet1 = wb.createSheet("first sheet");
		 Sheet sheet2 = wb.createSheet("second sheet");
		 
		 writeCells(sheet1, "first");
		 writeCells(sheet2, "second");
		 // set the columns to repeat from column 0 to 2 on the first sheet
		 // int sheetIndex, int startColumn, int endColumn, int startRow, int endRow
		 wb.setRepeatingRowsAndColumns(0, 0, 2, 0, 2);
		 wb.setRepeatingRowsAndColumns(1,4,5,1,2);
		 
		 FileOutputStream out = new FileOutputStream(path + "repeating.xls");
		 wb.write(out);
		 out.close();
	 }
	 
	private void writeCells(Sheet sheet,String value) {
		Row row = sheet.createRow(1);
		Cell cell = row.createCell(1);
		cell.setCellValue(value);
	}


	/**
	 * Creates a cell and aligns it a certain way.
	 * @param wb		the workbook
	 * @param row		the row to create the cell in
	 * @param column	the column number to create the cell in
	 * @param halign	the horizontal alignment for the cell
	 * @param valign	the vertical alignment for the cell.
	 */
	private static void createCell(Workbook wb , Row row, short column, short halign, short valign) {
		Cell cell = row.createCell(column);
		cell.setCellValue(new HSSFRichTextString("hssf it"));
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(halign);
		cs.setVerticalAlignment(valign);
		cell.setCellStyle(cs);
	}
	
	
	
	@Test
	public void testSheet() {
		Workbook wb = ExcelUtil.getWorkbook("c:\\demo.xls");
		int number = wb.getNumberOfSheets();
		boolean selectedFirstSheet = false;
		for (int i = 0; i < number; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (!selectedFirstSheet && !wb.isSheetHidden(i)) {
				// the first sheet.
				sheet.setSelected(true);
				wb.setActiveSheet(i);
				selectedFirstSheet = true;
			} else {
				sheet.setSelected(false);
			}
			
			wb.setPrintArea(i, 0, 0, 0, 0);
			String name = sheet.getSheetName();
			print(name, "\tFirstRowNumber\t", sheet.getFirstRowNum());
			Row firstRow = sheet.getRow(0);
			println("\tFirstCellNumber\t", firstRow.getFirstCellNum());
			Cell firstCell = firstRow.getCell(0);
			firstCell.setAsActiveCell();
			firstCell.setCellValue("FirstCell");
		
			sheet.setZoom(1, 1);
			println("\t" ,firstCell);
		}
		
		ExcelUtil.writeExcel(wb, "c:\\demo_output.xls");
	}
}
