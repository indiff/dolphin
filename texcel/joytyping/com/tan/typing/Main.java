package com.tan.typing;

import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.util.DialogUtil;
import com.tan.util.ExcelUtil;
import static com.tan.util.TanUtil.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DialogUtil dialog = new DialogUtil();
		
		File excel = dialog.chooseFile("选择记录文件", "记录锦集.xls", 
				new String[]{ "*.xls"}, 
			"C:\\Users\\TanYuanji\\Desktop\\joytyping-data");
		dialog.forceDispose();
		
//		generateCode();
		
		Workbook wb = ExcelUtil.getWorkbook(excel);
		
		Sheet sheet = wb.getSheetAt(0);
		
		int first = sheet.getFirstRowNum();
		int last = sheet.getLastRowNum();
		
		for (int i = first; i <= last ; i++) {
			final Row row = sheet.getRow(i);
			
			if (i == 0) continue;
			
			if (row != null) {
//				int lastCell = row.getLastCellNum();
				// 姓名	0.
				Cell cell0=row.getCell(0);
				final String name = ExcelUtil.getCellString(cell0);
				// 性别	1.
				Cell cell1=row.getCell(1);
				final String sex = ExcelUtil.getCellString(cell1);
				// 年龄	2.
				Cell cell2=row.getCell(2);
				final String age = ExcelUtil.getCellString(cell2);
				// 生日	3.
				Cell cell3=row.getCell(3);
				
				final String birthdate = ExcelUtil.getCellString(cell3);
				// 英文入门场记录	4.
				Cell cell4=row.getCell(4);
				final String english = ExcelUtil.getCellString(cell4);
				// 英文高中场记录	5.
				Cell cell5=row.getCell(5);
				final String englishMiddle = ExcelUtil.getCellString(cell5);
				// 中文场记录	6.
				Cell cell6=row.getCell(6);
				final String chinese = ExcelUtil.getCellString(cell6);
				// 疯狂背单词记录	7.
				Cell cell7=row.getCell(7);
				final String crazyEnglish = ExcelUtil.getCellString(cell7);
				// 轻松学单词记录	8.
				Cell cell8=row.getCell(8);
				final String studyWord = ExcelUtil.getCellString(cell8);
				// 经典怀旧场记录	9.
				Cell cell9=row.getCell(9);
				final String oldEnglish = ExcelUtil.getCellString(cell9);
//				<TH>姓名</TH>
//				<TH>性别</TH>
//				<TH>年龄</TH>
//				<TH>生日</TH>
//				<TH>英文入门</TH>
//				<TH>英文高中</TH>
//				<TH>中文</TH>
//				<TH>疯狂背单词</TH>
//				<TH>轻松学单词</TH>
//				<TH>经典怀旧</TH>
				//check for the birthdate.
				
				showHTML(name,sex,age,birthdate,english,englishMiddle,
						chinese, crazyEnglish,studyWord,oldEnglish
						);
				
			}
		}
	}
	
	/**
	 * Generate the code for the poi api.
	 */
	@SuppressWarnings("unused")
	private static void generateCode() {
		final String[] ss = "姓名	性别	年龄	生日	英文入门场记录	英文高中场记录	中文场记录	疯狂背单词记录	轻松学单词记录	经典怀旧场记录".split("\t");
		int x = 0;
		for (String s : ss) {
			 int number = x++;
			 println("// ",  s, "\t", number, ".\nCell cell", number, '=', "row.getCell(",number,");");
		}
		System.exit(1);
	}

//	private static void showHTML(String name, String sex, String age, String birthdate, String english,
//			String englishMiddle, String chinese, String crazyEnglish, String studyWord, String oldEnglish) {
//		
//	}
	
	/**
	 * print the htmls for the values.
	 */
	private static void showHTML(final String ... values) {
		println("<TR>");
		if (values != null && values.length != 0) {
			for (int i = 0 ; i < values.length; i++) {
				if (values[i] == null || values[i].trim().length() == 0)  {
					println("\t<TD>", '-', "</TD>");
				} else {
					// when the value had character '.' .
					int idx = -1;
					String v = null;
					if ((idx = values[i].indexOf(".0")) >= 0) {
						v = values[i].substring(0, idx);
					} else {
						v = values[i];
					}
					
					// when the i == 3 for the birthdate.
					if (i == 3) println("\t<TD>", v.length() != 1 ? v.substring(1) : v, "</TD>");
					// when the i == 4 for the english.
					else if (i == 4) println("\t<TD><B>", v, "</B></TD>");
					// other wise.
					else println("\t<TD>",v, "</TD>");
				}
				
			}
		}
		println("</TR>");
	}
}
