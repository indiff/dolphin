package com.tan.util;

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
		for (char c : vs)
			if (c < 48 || c > 57)
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
		for (char c : vs)
			if (Character.isWhitespace(c) || c == '　')
				return true;
		return false;
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
		for (char c : chars) {
			if (c == oldChar)
				buf.append(newChar);
			else
				buf.append(c);
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
		String temp = value.substring(lastIndex + length);
		while (index >= 0) {
			buf.append(value.substring(start, index)).append(newStr);
			value = value.substring(start + index + length);
			index = value.indexOf(oldStr);
		}
		buf.append(temp);
		return buf.toString();
	}
	
	
	public static void main(String[] args) {
		String names = "資金識別子,レコードＩＤ,住居区分名称,極度額上限利率,限度額上限利率,登録日時,登録者,登録端末,更新日時,更新者,更新端末";
		
		 names = "A,B,C";
		
		String values = "'22','2','集合住宅','65.55','60.00','2010-6-25',NULL,NULL,NULL,NULL,NULL";
		
		
		
	}

	public static String getIndexValue(int index, String separator, String names) {
		int count = -1;
		int begin = 0;
		int end = -1;
		while (true) {
			end = names.indexOf(separator, begin);
			if (end < 0) {
					if (count <= index - 2)
						return null;
					return names.substring(names.lastIndexOf(separator) + 1);
			}
			if (end >= 0) {
				if (++count == index) {
					return names.substring(begin, end);
				} else {
					begin = end + 1;
				}
			}
		}
	}
}
