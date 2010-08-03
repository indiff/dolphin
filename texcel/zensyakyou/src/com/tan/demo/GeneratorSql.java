package com.tan.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author dolphin
 *
 * 2010/06/29 16:04:32
 */
public class GeneratorSql {
	/**Excel 文件路径. **/
	static final String path = "c:/コピーテーブル定義(T商品在庫日別履歴).xls";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/** 初始化 Excel. **/
		Workbook wb = initialize(path);
		
		/** 定位 Sheet 页. **/
		Sheet sheet = wb.getSheetAt(0);
		
		/** 遍历单元格. **/
		for (int i = 8 ; i < 94; ++i) {
			Row row = sheet.getRow(i);
			
			Cell idCell = row.getCell(2); 	// 項目ID.
			/** 未用到的 cells. **/
//			Cell cell1 = row.getCell(1);
//			Cell cell3 = row.getCell(3);	// PK.
//			Cell cell4 = row.getCell(4);	// UK.
//			Cell cell5 = row.getCell(5);	// FK.
//			Cell cell6 = row.getCell(6);	// INDEX.
//			Cell cell7 = row.getCell(7);// ドメイン.
//			Cell cell13 = row.getCell(13);// サイズ.
//			Cell cell14 = row.getCell(14);// 初期値.
			
			Cell typeCell = row.getCell(10);// 属性.
			Cell lengthCell = row.getCell(11);// 長さ.
			Cell decimalCell = row.getCell(12);// 精度.
			Cell isNullCell = row.getCell(15);// NOT NUll.
			// [項目ID] 	[属性] NOT NUll
//			[Id]	[uniqueidentifier]	NOT NULL,
//			[UpdateCounter]	[int](1)	NOT NULL,
//			[RegistUserId]	[uniqueidentifier]	NOT NULL,
//			[RegistPrgId]	[uniqueidentifier]	NOT NULL,
//			[RegistPrgDtm]	[datetime]	NOT NULL,
//			[UpdateUserId]	[uniqueidentifier]	NOT NULL,
//			[UpdatePrgId]	[uniqueidentifier]	NOT NULL,
//			[UpdatePrgDtm]	[datetime]	NOT NULL,
//			[DelFlg]	[bit](1)	NOT NULL,
//			[DelPrgDtm]	[datetime]	,
//			[ZaikoNo]	[varchar](9)	NOT NULL,
//			[ZaikoDate]	[datetime]	NOT NULL,
//			[ShohinId]	[uniqueidentifier]	NOT NULL,
//			[SeimaiLotNo]	[varchar](1)	,
//			[SokoId]	[uniqueidentifier]	,
//			[Seimaibi]	[datetime]	,
//			[NyukoKbn]	[int](1)	NOT NULL,
//			[ShohinDaiBunruiId]	[uniqueidentifier]	NOT NULL,
//			[GengoId]	[uniqueidentifier]	,
//			[WarekiNensan]	[int](2)	,
//			[ShokaiSeimaiNyuShukkoId]	[uniqueidentifier]	,
//			[ShokaiSeimaiNyukoDate]	[datetime]	,
//			[AreaCd]	[varchar](7)	,
//			[ShiireTanka]	[decimal](9,2)	NOT NULL,
//			[Irisu]	[decimal](6,1)	NOT NULL,
//			[MikomiZaikoJuryo]	[decimal](9,3)	NOT NULL,
//			[MikomiZaikoSuryo]	[decimal](5)	NOT NULL,
//			[MikomiHikiateZaikoJuryo]	[decimal](9,3)	NOT NULL,
//			[MikomiHikiateZaikoSuryo]	[decimal](5)	NOT NULL,
//			[RonriZaikoJuryo]	[decimal](9,3)	NOT NULL,
//			[RonriZaikoSuryo]	[decimal](5)	NOT NULL,
//			[HikiateZaikoJuryo]	[decimal](9,3)	NOT NULL,
//			[HikiateZaikoSuryo]	[decimal](5)	NOT NULL,
//			[NohinSekisoJuryo]	[decimal](9,3)	NOT NULL,
//			[NohinSekisoSuryo]	[decimal](5)	NOT NULL,
//			[IdoSekisoJuryo]	[decimal](9,3)	NOT NULL,
//			[IdoSekisoSuryo]	[decimal](5)	NOT NULL,
//			[NyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[NyukoSuryo]	[decimal](5)	NOT NULL,
//			[SekisoNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[SekisoNyukoSuryo]	[decimal](5)	NOT NULL,
//			[IdoNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[IdoNyukoSuryo]	[decimal](5)	NOT NULL,
//			[IdoSeiksoNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[IdoSekisoNyukoSuryo]	[decimal](5)	NOT NULL,
//			[UriageHenpinJuryo]	[decimal](9,3)	NOT NULL,
//			[UriageHenpinSuryo]	[decimal](5)	NOT NULL,
//			[HataiNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[HataiNyukoSuryo]	[decimal](5)	NOT NULL,
//			[ChoseiNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[ChoseiNyukoSuryo]	[decimal](5)	NOT NULL,
//			[HigireNyukoJuryo]	[decimal](9,3)	NOT NULL,
//			[HigireNyukoSuryo]	[decimal](5)	NOT NULL,
//			[NohinJuryo]	[decimal](9,3)	NOT NULL,
//			[NohinSuryo]	[decimal](5)	NOT NULL,
//			[SekisoShukkoJuryo]	[decimal](9,3)	NOT NULL,
//			[SekisoShukkoSuryo]	[decimal](5)	NOT NULL,
//			[IdoShukkoJuryo]	[decimal](9,3)	NOT NULL,
//			[IdoShukkoSuryo]	[decimal](5)	NOT NULL,
//			[IdoSekisoShukkoJuryo]	[decimal](9,3)	NOT NULL,
//			[IdoSekisoShukkoSuryo]	[decimal](5)	NOT NULL,
//			[ShiireHenpinJuryo]	[decimal](9,3)	NOT NULL,
//			[ShiireHenpinSuryo]	[decimal](5)	NOT NULL,
//			[HataiJuryo]	[decimal](9,3)	NOT NULL,
//			[HataiSuryo]	[decimal](5)	NOT NULL,
//			[ChoseiShukkoJuryo]	[decimal](9,3)	NOT NULL,
//			[ChoseiShukkoSuryo]	[decimal](5)	NOT NULL,
//			[HigireShukkoJuryo]	[decimal](9,3)	NOT NULL,
//			[HigireShukkoSuryo]	[decimal](5)	NOT NULL,
//			[ChokusoJuryo]	[decimal](9,3)	NOT NULL,
//			[ChokusoSuryo]	[decimal](5)	NOT NULL,
//			[ChokusoNohinJuryo]	[decimal](9,3)	NOT NULL,
//			[ChokusoNohinSuryo]	[decimal](5)	NOT NULL,
//			[HarikomiJuryo]	[decimal](9,3)	NOT NULL,
//			[HarikomiSuryo]	[decimal](5)	NOT NULL,
//			[HozaiHatchuZaikoSu]	[decimal](5)	NOT NULL,
//			[HigireShoriKbn]	[int](1)	,
//			[HigireHasseiDate]	[datetime]	,
//			[HigireShoriDate]	[datetime]	,
//			[SaishuNyukoDate]	[datetime]	,
//			[SaishuShukkoDate]	[datetime]	,
//			[SaishuShiyoDate]	[datetime]	,
//			[ShikakariZaikoSuryo]	[decimal](5)	NOT NULL,
//			[ShikakariZaikoJuryo]	[decimal](9,3)	NOT NULL,
//			[ShikakariZaikoKingaku]	[decimal](9)	NOT NULL,
//			[ZaikoId]	[uniqueidentifier]	NOT NULL,			
			System.out.println(
					 "[" + 
					 filter(idCell) + "]\t" +  
					 '[' + filter(typeCell) + "]" +
					 getDecimalValue(filter(lengthCell), filter(decimalCell)) + "\t" + 
					 getNullValue(filter(isNullCell)) + ','
			);
		}
	}
	
	/**
	 * 获取长度和精度数字.
	 * @param v1
	 * @param v2
	 * @return
	 */
	private static String getDecimalValue(String v1, String v2) {
		StringBuilder buf = new StringBuilder("(");
		if (isNumber(v1)) {
			buf.append(v1.charAt(0));
		}
		if (isNumber(v2)) {
			buf.append("," + v2.charAt(0));
		}
		buf.append(')');
		if (buf.length() == 2) return "";
		return buf.toString();
	}	
	
	/**
	 * 是否为精度数字类型.
	 * @param v
	 * @return
	 */
	private static boolean isNumber(String v) {
		return v != null && v.matches("\\d+\\.?\\d*");
	}
	
	/**
	 * 判断是否需要 NOT NULL 值.
	 * @param v
	 * @return
	 */
	private static String getNullValue(String v) {
		if (v == null || v.isEmpty()) return "";
		if (v.charAt(0) == '○') {
			return "NOT NULL";
		}
		return "";
	}
	
	/**
	 * 过滤  Cell 内容，返回字符串.
	 * @param c
	 * @return
	 */
	private static String filter(Cell c) {
		int type = c.getCellType();
		switch (type) {
			case Cell.CELL_TYPE_BLANK : 
				return ""; 
			case Cell.CELL_TYPE_BOOLEAN : 
				return String.valueOf(c.getBooleanCellValue());
			case Cell.CELL_TYPE_STRING : {
				RichTextString text = c.getRichStringCellValue();
				return text.getString();
			}
			case Cell.CELL_TYPE_FORMULA : {
				return c.getCellFormula();
			}
			case Cell.CELL_TYPE_NUMERIC : {
				return String.valueOf(c.getNumericCellValue());
			}
		}
		return "no-value";
	}
	
	/**
	 * 初始化 Excel.
	 * @param path
	 * @return
	 */
	public static Workbook initialize(String path) {
		FileInputStream in = null;
		Workbook wb = null;
		try {
			in = new FileInputStream(path);
			wb = new HSSFWorkbook(in);
			if (wb == null) 
				wb = new XSSFWorkbook(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return wb;
	}
}