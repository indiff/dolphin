package com.tan.util;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public final class StringUtil {
	/**
	 * 判断字符串是否为空.
	 * 
	 * @param v
	 * @return
	 */
	public final static boolean isEmpty(String v) {
		return v == null || v.trim().length() == 0;
	}
	
	/**
	 * Not null.
	 * @param v
	 * @return
	 */
	public final static boolean isNotNull(String v) {
		return v != null && v.trim().length() != 0 && !"null".equals(v.trim().toLowerCase());
	}
	/**
	 * 判断字符串是否为空.
	 * 
	 * @param v
	 * @return
	 */
	public final static boolean isNotEmpty(String v) {
		return v != null && v.trim().length() != 0;
	}

	/**
	 * 判断是否为数字.
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isNumber(String v) {
		if (isEmpty(v))
			return false;
		char[] vs = v.toCharArray();
		for (int i = 0; i < vs.length;i++)
			if (vs[i] < 48 || vs[i] > 57)
				return false;
		return true;
	}

	/**
	 * 判断是否为空白字符. 包括 { '\u0009'，水平制表符。 ' '，换行。 '\u000B'，纵向制表符。 '\u000C'，换页。 '
	 * '，回车。 '\u001C'，文件分隔符。 '\u001D'，组分隔符。 '\u001E'，记录分隔符。 '\u001F'，单元分隔符。 }
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isWhitespace(String v) {
		if (v == null)
			return false;
		char[] vs = v.toCharArray();
		for (int i = 0; i < vs.length;i++)
			if (!Character.isWhitespace(vs[i]))
				return false;
		return true;
	}

	/**
	 * 过滤字符. Example:<br>
	 * 
	 * <pre>
	 * 	value :"a", "b"
	 *  oldChar: "
	 *  newChar: '
	 *  return : 'a', 'b'
	 * </pre>
	 * 
	 * @param value
	 *            要过滤的值.
	 * @param oldChar
	 *            需要过滤的字符.
	 * @param newChar
	 *            将要替换的字符.
	 * @return
	 */
	public static String filter(String value, char oldChar, char newChar) {
		char[] chars = value.toCharArray();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < chars.length;i++) {
			if (chars[i] == oldChar)
				buf.append(newChar);
			else
				buf.append(chars[i]);
		}
		return buf.toString();
	}



	/**
	 * 过滤字符. "A".replace('A', 'B');
	 * 
	 * @param value
	 * @param oldChar
	 * @param newChar
	 * @return
	 */
	public static String filter0(String value, char oldChar, char newChar) {
		char[] chars = value.toCharArray();
		char[] results = new char[chars.length];
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == oldChar)
				results[i] = newChar;
			else
				results[i] = chars[i];
		}
		return new String(results, 0, results.length);
	}

	/**
	 * 过滤字符串. 类似 String.replaceAll("oldString", "newString");
	 * 
	 * @param value
	 *            .
	 * @param oldStr
	 *            .
	 * @param newStr
	 *            .
	 * @return
	 */
	public static String filter(String value, String oldStr, String newStr) {
		StringBuilder buf = new StringBuilder();
		int start = 0;
		int index = value.indexOf(oldStr);
		int length = oldStr.length();
		int lastIndex = value.lastIndexOf(oldStr);
		String last = value.substring(lastIndex + length);
		while (index >= 0) {
			buf.append(value.substring(start, index)).append(newStr);
			value = value.substring(start + index + length);
			index = value.indexOf(oldStr);
		}
		buf.append(last);
		return buf.toString();
	}
	
	public static String getString(String basename, String key) {
		ResourceBundle bundle = ResourceBundle.getBundle(basename);
		return  bundle.getString(key);
	}
	
	public static String getString(String basename, String key, Object [] args) {
		ResourceBundle bundle = ResourceBundle.getBundle(basename);
		String message = bundle.getString(key);
		return  MessageFormat.format(message, args);
	}


	public final static Object getInstanceSentence(final String owner) {
		return owner + " start = new " + owner + "();";
//		return owner + ' ' + owner.toLowerCase() + " = new " + owner + "();";
	}
	
	public static String getComment(String string, boolean isJavaDoc) {
		if (isEmpty(string)) {
			return null;
		}
		string = string.trim();
		if (isJavaDoc) {
			if (string.startsWith("/*")) {
				string = string.substring(2);
			} else if (string.startsWith("/**")) {
				string = string.substring(3);
			}
			
			if (string.endsWith("*/")) {
				string = string.substring(0, string.length() - 2);
			} else if (string.endsWith("**/")) {
				string = string.substring(0, string.length() - 3);
			}
			return string.replaceAll("\\*", "").trim();
		}
		int lineIdx = string.indexOf("\r\n");
		if (lineIdx < 0) {
			lineIdx = string.indexOf("\n");
		}
		if (string.startsWith("//")) {
			if (lineIdx > 0) {
				string = string.substring(2, lineIdx ).trim();
			} else {
				string = string.substring(2).trim();
			}
		} else {
			int start = string.indexOf("//");
			string = string.substring(start + 2).trim();
			
		}
		return string;
	}
	
	public static void main(String[] args) {
		System.out.println(getComment("//common comment 3. public static void main", false));
		System.out.println(getComment("//java comment 4. \r\n private float height;", false));
		System.out.println(getComment("//java comment 5. \n private float height;", false));
		System.out.println(getComment(" \n private float height; //java comment 6.;", false));
		System.out.println(getComment("// some thing. \n private Field field; // fdsafdsafdsao 7.;", false));
		System.out.println(getComment(" \r\n \n private float height; //java comment 8.;", false));
		System.out.println(getComment(" ", true));
	}
}
