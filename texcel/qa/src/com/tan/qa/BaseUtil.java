package com.tan.qa;

import static com.tan.consts.Consts.LINE;
import static com.tan.util.TanUtil.append;
import static com.tan.util.TanUtil.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.tan.util.TanUtil;

public class BaseUtil {

	private static MathContext MATH_CONTEXT = new MathContext(3, RoundingMode.HALF_UP);

	private static BigDecimal PERCENT100 = new BigDecimal(100);

	public static String getCurrentPath() {
		return System.getProperty("user.dir");
		// return
		// Thread.currentThread().getContextClassLoader().getResource("").getFile();
	}

	public static File getCurrentPathFile() {
		return new File(System.getProperty("user.dir"));
	}

	/**
	 * show percent for the task.
	 * 
	 * @param index
	 * @param size
	 */
	public static synchronized void showPercent(final int index, final int size) {
		BigDecimal i = new BigDecimal(index);
		BigDecimal s = new BigDecimal(size);
		warnln(" ", i.divide(s, MATH_CONTEXT).multiply(PERCENT100, MATH_CONTEXT), "%!");
	}
	
	/**
	 * show percent for the task.
	 * 
	 * @param index
	 * @param size
	 */
	public static synchronized String percent(final int index, final int size) {
		StringBuilder b = new StringBuilder();
		BigDecimal i = new BigDecimal(index);
		BigDecimal s = new BigDecimal(size);
		appendln(b, " ", i.divide(s, MATH_CONTEXT).multiply(PERCENT100, MATH_CONTEXT), "%!");
		return b.toString();
	}

	public static String checkName(final String name) {
		if (name == null)
			return null;
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
			}
		}
		if (right)
			return "";
		return b.toString();
	}

	public static String checkFullAngle(final String name) {
		if (name == null)
			return null;
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
			case '　': {
				right = false;
				append(b, warn, " 包含全角空格:", LINE);
				break;
			}
				// case '，': {
				// right = false;
				// append(b, warn, " 包含全角逗号:", LINE);
				// break;
				// }
				//
				// case '；': {
				// right = false;
				// append(b, warn, " 包含全角分号:", LINE);
				// break;
				// }
				//
				// case '：': {
				// right = false;
				// append(b, warn, " 包含全角冒号:", LINE);
				// break;
				// }
				//
				// case '。': {
				// right = false;
				// append(b, warn, " 包含全角句号:", LINE);
				// break;
				// }
				// case '＂': {
				// right = false;
				// append(b, warn, " 包含全角双引号:", LINE);
				// break;
				// }
				// case '＇': {
				// right = false;
				// append(b, warn, " 包含全角单引号:", LINE);
				// break;
				// }
				//
				// case '《': {
				// right = false;
				// append(b, warn, " 包含全角左书名号:", LINE);
				// break;
				// }
				//
				// case '》': {
				// right = false;
				// append(b, warn, " 包含全角右书名号:", LINE);
				// break;
				// }
			}
		}
		if (right)
			return "";
		return b.toString();
	}

	/**
	 * Check The File Name is right.
	 * 
	 * @param name
	 * @return
	 */
	public static String checkFileName(final String name) {
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

	public static String filter(String value) {
		return value.replaceAll("[ 　]", "");
	}

	public static String add(String number) {
		int n = Integer.parseInt(number);
		++n;
		String newNumber = String.valueOf(n);
		System.out.println(formatNumber(newNumber));
		return formatNumber(newNumber);
	}

	public static String formatNumber(String number) {
		int length = number.length();
		if (number == null || length == 0) {
			TanUtil.warnln("番号有误!");
		}
		if (length < 4) {
			if (length == 3)
				return "0" + number;
			if (length == 2)
				return "00" + number;
			if (length == 1)
				return "000" + number;
		}
		return number;
	}

	public static void exitWithMessage(final String message) {
		TanUtil.warnln(message, " App Exit!");
		System.exit(1);
	}

	public static void exitWithMessage(final Object... messages) {
		if (messages == null)
			return;
		TanUtil.warnln(messages);
		System.exit(1);
	}

	public static void main(String[] args) {
		String number = "0021";

		number = add(number);
		number = add(number);
		number = add(number);

		// System.out.println(getLeader("潘啓柱"));
		// System.out.println(getLeader("　潘啓柱"));
		// System.out.println(filter("　潘啓柱"));
		// System.out.println(filter(" 潘啓柱    "));
		// System.out.println(checkName("　潘啓柱"));
		// System.out.println(checkName("潘啓柱"));
	}
}
