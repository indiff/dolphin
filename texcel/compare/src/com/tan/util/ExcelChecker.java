package com.tan.util;

import static com.tan.consts.Consts.LINE;
import static com.tan.util.TanUtil.append;

public class ExcelChecker {
	
	/**
	 * Check the sheet name is right.
	 * @param name
	 * @return
	 */
	public static String checkSheetName(final String name) {
		char[] cs = name.toCharArray();
		StringBuilder b = new StringBuilder();
		String warn = "警告 Sheet {" + name + '}';
		boolean right = true;
		for (char c : cs) {
			if (c >= 65296 && c <= 65305) { // ０ ~ ９.
				// warnln(warn, " 包含全角数字 ", c, LINE);
				right = false;
				append(b, warn, " 包含全角数字:", c, LINE);
			}

			if (c >= 65313 && c <= 65338) { // Ａ ~ Ｚ.
				// warn(warn, " 包含全角大写字母 ", c);
				right = false;
				append(b, warn, " 包含全角大写字母:", c, LINE);
			}

			if (c >= 65345 && c <= 65370) { // ａ ~ ｚ.
				// warn(warn," 包含全角小写字母 ", c);
				right = false;
				append(b, warn, " 包含全角小写字母:", c, LINE);
			}
			switch (c) {
			case ' ': {
				// warn(warn, " 包含半角空格 ");
				right = false;
				append(b, warn, " 包含半角空格:", LINE);
				break;
			}
			case '　': {
				// warn(warn, " 包含全角空格 ");
				right = false;
				append(b, warn, " 包含全角空格:", LINE);
				break;
			}
				// case '（' :{
				// return false;
				// }
				// case '）' :{
				// return false;
				// }
				// case '０' :{
				// return false;
				// }
				// case '１' :{
				// return false;
				// }
				// case '２' :{
				// return false;
				// }
				// case '３' :{
				// return false;
				// }
				// case '４' :{
				// return false;
				// }
				// case '５' :{
				// return false;
				// }
				// case '６' :{
				// return false;
				// }
				// case '７' :{
				// return false;
				// }
				// case '８' :{
				// return false;
				// }
				// case '９' :{
				// return false;
				// }
			}
		}
		if (right)
			return "";
		return b.toString();
	}
	
	/**
	 * Check The Excel Name is right.
	 * @param name
	 * @return
	 */
	public static String checkExcelName(final String name) {
		char[] cs = name.toCharArray();
		StringBuilder b = new StringBuilder();
		String warn = "警告{" + name + '}';
		boolean right = true;
		for (char c : cs) {
			if (c >= 65296 && c <= 65305) { // ０ ~ ９.
				// warnln(warn, " 包含全角数字 ", c, LINE);
				right = false;
				append(b, warn, " 包含全角数字:", c, LINE);
			}

			if (c >= 65313 && c <= 65338) { // Ａ ~ Ｚ.
				// warn(warn, " 包含全角大写字母 ", c);
				right = false;
				append(b, warn, " 包含全角大写字母:", c, LINE);
			}

			if (c >= 65345 && c <= 65370) { // ａ ~ ｚ.
				// warn(warn," 包含全角小写字母 ", c);
				right = false;
				append(b, warn, " 包含全角小写字母:", c, LINE);
			}
			switch (c) {
			case ' ': {
				// warn(warn, " 包含半角空格 ");
				right = false;
				append(b, warn, " 包含半角空格:", LINE);
				break;
			}
			case '　': {
				// warn(warn, " 包含全角空格 ");
				right = false;
				append(b, warn, " 包含全角空格:", LINE);
				break;
			}
				// case '（' :{
				// return false;
				// }
				// case '）' :{
				// return false;
				// }
				// case '０' :{
				// return false;
				// }
				// case '１' :{
				// return false;
				// }
				// case '２' :{
				// return false;
				// }
				// case '３' :{
				// return false;
				// }
				// case '４' :{
				// return false;
				// }
				// case '５' :{
				// return false;
				// }
				// case '６' :{
				// return false;
				// }
				// case '７' :{
				// return false;
				// }
				// case '８' :{
				// return false;
				// }
				// case '９' :{
				// return false;
				// }
			}
		}
		if (right)
			return "";
		return b.toString();
	}	
			
	
}
