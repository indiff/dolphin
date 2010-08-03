package com.tan.excel.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tan.bean.Kubun;

/**
 * @author dolphin
 *
 * 2010/06/08 11:31:06
 */
public class ReadKubunUtil {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println();
		// final String path = "C:\\1.xls";
		// final String path =
		// ReadKubunUtil.class.getResource("kubun.xls").getFile();
		final String path = "D:\\pswg-masserver\\020 ED\\06 コード定義\\duplicate.xls";

		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(path);
			wb = new HSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (in != null)
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		System.out.println(wb);
		// try {
		// readKubunExcel(path);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	private static void parseTxt(String content) {
		String[] record = content.split("\r\n");
		String[] items = record[0].split("\t");
		System.out.println(items[0] +"\t" +  items[1]);
	}
	
	

	public static List<Kubun> readKubunExcel(String path) throws Exception {
		InputStream in = null;
		try {
			in = new FileInputStream(path);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet0 = wb.getSheetAt(0);
		// 第一个sheet 的物理行数 
		int sheet0RowNumber = sheet0.getPhysicalNumberOfRows();
		for (int i = 1 ; i < sheet0RowNumber; i++) {
			Row row = sheet0.getRow(i);
			// 共通コード管理区分
			Cell cellKubun = row.getCell(0);
			// 共通コード管理区分名
			Cell cellKubunName = row.getCell(1);
			// 备注
			Cell cellRemark = row.getCell(2);
		}
		
		// 第二个 Sheet 
		Sheet sheet1 = wb.getSheetAt(1);
		List<Kubun> kubuns = new ArrayList<Kubun>();
		
		int rowsNumber = sheet1.getPhysicalNumberOfRows();
		for (int i = 1; i < rowsNumber; i++) {
			Row row = sheet1.getRow(i);
			// 共通コード管理区分.	
			Kubun ko = null;
			Cell kubun = row.getCell(0);
			if (kubun.getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			} else if (kubun.getCellType() == Cell.CELL_TYPE_STRING) {
				ko = new Kubun();
				ko.setKey(kubun.getRichStringCellValue().toString());
			}
			// 共通コード.
			Cell kubunCode = row.getCell(1);
			if (kubunCode.getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			} else if (kubunCode.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setCode(kubunCode.getRichStringCellValue().toString());
			} else if (kubunCode.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//				System.out.println(kubunCode.getNumericCellValue());
			}
			// 共通コード名.	
			Cell kubunName = row.getCell(2);
			if (kubunName.getCellType() == Cell.CELL_TYPE_BLANK) {
				continue;
			} else if (kubunName.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setName(kubunName.getRichStringCellValue().toString());
			} 
			// 付加フィールド1.
			Cell kubunField1 = row.getCell(3);
			if (kubunField1.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setField1(parseInt(kubunField1.getRichStringCellValue().toString()));
			}
			// 付加フィールド2.
			Cell kubunField2 = row.getCell(4);
			if (kubunField2.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setField2(parseInt(kubunField2.getRichStringCellValue().toString()));
			}
			// 付加フィールド3.
			Cell kubunField3 = row.getCell(5);
			if (kubunField3.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setField3(parseInt(kubunField3.getRichStringCellValue().toString()));
			}
			// 備考.
			Cell kubunInfo = row.getCell(6);
			
			if (kubunInfo.getCellType() == Cell.CELL_TYPE_STRING) {
				ko.setInfo(kubunInfo.getRichStringCellValue().toString());
			}
			
			if (ko != null) kubuns.add(ko);
		}
		
		in.close(); in = null;
		return kubuns;
	}
	
	
	
	private static int parseInt(String v) {
		if ("　　　　　　　　　　".equals(v)) {
			return 0;
		}
		return Integer.parseInt(v.toString().trim());
	}

	/**
	 * read ddl from txt file
	 * @param in
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String readFromTxt(String path) {
		try {
			return readFromTxt(new FileInputStream(path), Charset.defaultCharset().name());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return "";
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
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

}
