package com.tan.excel;

import static com.tan.util.TanUtil.println;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import com.tan.util.ExcelUtil;

public class CellStyleTest {
	@Test
	public void testCellStyle() throws Exception{
		Workbook wb = ExcelUtil.getWorkbook("C:\\demo.xls");

		Sheet sheet = wb.getSheetAt(0);

		Row row = sheet.getRow(0);

		Cell cell = row.getCell(0);

		CellStyle cs = cell.getCellStyle();

		short fillBackgroundColor = cs.getFillBackgroundColor();
		short fillForegounrdColor = cs.getFillForegroundColor();
		short fontIndex = cs.getFontIndex();

		println('|', "filleBackgroundColor", fillBackgroundColor);

		println('|', "filleForegroundColor", fillForegounrdColor);
		println('|', "fontIndex", fontIndex);
			
		
		
		Workbook workbook = ExcelUtil.getWorkbook("c:\\sam.xls");
		
		
		//Sheet demoSheet = workbook.createSheet("Demo");
		Sheet demoSheet = workbook.getSheetAt(0);
		
		Row r0 = demoSheet.createRow(1);
 		
		Cell c0 = r0.createCell(1);

		CellStyle demoCellStyle = workbook.createCellStyle();
//		demoCellStyle.setFillBackgroundColor((short) 64);
//		demoCellStyle.setFillForegroundColor((short) 14);
		
		demoCellStyle.setFillBackgroundColor(HSSFColor.RED.index);
		demoCellStyle.setFillPattern(HSSFCellStyle.BIG_SPOTS);
		demoCellStyle.setFillForegroundColor(HSSFColor.RED.index);
		demoCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		
		Font myFont = workbook.createFont();
		myFont.setColor(Font.COLOR_RED);
		demoCellStyle.setFont(myFont);
		c0.setCellStyle(demoCellStyle);
		c0.setCellValue("The value for cell");
		
		
		FileOutputStream out = new FileOutputStream("c:\\out.xls");
		workbook.write(out);
		
		out.flush();
		out.close();
	}
}
