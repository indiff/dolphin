package com.tan.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author dolphin
 *
 * 2010/06/08 11:31:36
 */
public class FrontStyleBook {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		oldCode();
		
//		createTwoExcelsByNewCode();
		
		// Iterate over row and cells
//		String path = "C:\\Documents and Settings\\Administrator\\デスクトップ\\work-softbank\\0422\\test.xls";
		String path = "C:\\Documents and Settings\\Administrator\\デスクトップ\\work-softbank\\0422\\機能設計書(免許)_免許情報.xls";
		List<String> results = readXLS(path, false);
		for (String result : results) {
			System.out.println(result);
		}
		
//		writerXLS("C:\\Documents and Settings\\Administrator\\デスクトップ\\work-softbank\\0423\\demo-write.xls", results);
	}
	
	
	
	public static void writerXLS(String path, List<String> results) {
		FileInputStream in = null;
		try {
			 in = new FileInputStream("C:\\Documents and Settings\\Administrator\\デスクトップ\\work-softbank\\0422\\機能設計書(免許)_免許情報画面.xls");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 定位到第二个sheet
		Sheet sheet = wb.getSheetAt(1);
		
		// create the cells 创建单元格 
		CreationHelper helper = wb.getCreationHelper();
		// 创建一行
		short rowNum = 9;
		short colNum = 3;
		
		// creat a row and put some cells in it. Row are 0 based
		
		for (int i = 0 ; i < results.size(); i++) {
			Row row = sheet.createRow(rowNum++);
			Cell cell = row.createCell(colNum);
			cell.setCellValue(helper.createRichTextString(results.get(i).trim().replace("　", "")));
		}
//		for (short i = 0 ; i < 10; i++) {
//			Row row = sheet.createRow(rowNum++);
//			// create the cell and put a value in it;
//			Cell cell = row.createCell(colNum);
//			// 在第一行第一列写入一个int值
//			cell.setCellValue("This is a test" + i);
//		}
		
		
		// creating date cells
//		row.createCell(4).setCellValue(new Date());
		
//		// create format date
//		CellStyle cellStyle = wb.createCellStyle();
//		cellStyle.setDataFormat(helper.createDataFormat().getFormat("m/d/yy h:mm"));
//		Cell dateCell5 = row.createCell(5);
//		dateCell5.setCellValue(new Date());
//		dateCell5.setCellStyle(cellStyle);
		
		// write the output to a file
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		Sheet sheet = wb.getSheetAt(1);
		if(debug) System.out.println("SHEET NAME : " + sheet.getSheetName());
		for (Row row : sheet) {
			for (Cell cell : row) {
				CellStyle cs = cell.getCellStyle();
				if ( (
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


	public static void createTwoExcelsByNewCode() {
		Workbook[] wbs = new Workbook[]{new HSSFWorkbook(), new XSSFWorkbook()};
		for (short i = 0 ; i < wbs.length; i++) {
			Workbook book = wbs[i];
			CreationHelper createHelper = book.getCreationHelper();
			
			// create a new sheet
			Sheet s = book.createSheet();
//			Row row = null;
//			Cell cell = null;
			
			
			// create two cells styles
			CellStyle cs = book.createCellStyle();
			CellStyle cs1 = book.createCellStyle();
			DataFormat dataFormat = book.createDataFormat();
			
			
			// create two fonts objects;
			Font f = book.createFont();
			Font f2 = book.createFont();
			
			// set font 1 to 12 point type
			f.setFontHeightInPoints((short) 12);
			f.setColor(IndexedColors.RED.getIndex());
			f.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			// set font 2 to 10 point type
			f2.setFontHeightInPoints((short) 10);
			f2.setColor(IndexedColors.RED.getIndex());
			f2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
			cs.setFont(f);
			cs.setDataFormat(dataFormat.getFormat("#,##0.0"));
			
			// set the other cell style and formatting
			cs1.setBorderBottom(CellStyle.BORDER_THIN);
			cs1.setDataFormat(dataFormat.getFormat("text"));
			cs1.setFont(f2);
			
			// Define a few rows
			for (int rownum = 0; rownum < 30; rownum++) {
				Row r = s.createRow(rownum);
				for (int cellnum = 0; cellnum < 10; cellnum += 2) {
					Cell c = r.createCell(cellnum);
					Cell c2 = r.createCell(cellnum + 1);
					
					c.setCellValue((double) rownum + (double)(cellnum/10));
					c2.setCellValue(createHelper.createRichTextString("Hello! " + cellnum));
					
				}
			}
			
			// Save to file
			String fileName = "workbook.xls";
			
			if (book instanceof XSSFWorkbook) {
				fileName = fileName + 'x';
			}
			
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				book.write(out);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	private static void oldCode() {
		HSSFWorkbook wb = new HSSFWorkbook();
		
		// create a new sheet
		HSSFSheet sheet = wb.createSheet();
		// declare a row object reference
		HSSFRow row = null;
		// declare a cell object reference
		HSSFCell cell = null;
		
		// create two cells styles
		HSSFCellStyle cellStyle = wb.createCellStyle();
		HSSFCellStyle cellStyle1 = wb.createCellStyle();
		HSSFDataFormat dataFormat = wb.createDataFormat();
		
		
		// create two fonts objects
		HSSFFont font = wb.createFont();
		HSSFFont font1 = wb.createFont();
		
		// set font 1 to 12 point type, blue and bold
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	}

}
