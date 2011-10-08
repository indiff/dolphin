package com.tan.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Number工具类 .
 * 
 * @author dolphin
 * 
 *         2010/06/21 17:04:21
 */
public class NumberUtil {

	/**
	 * 返回阶乘.
	 * 
	 * @param i
	 * @return
	 */
	public static int factorial(int i) {
		if (i == 1)
			return 1;
		else {
			// println(i + "*" + (i - 1));
			return i * factorial(i - 1);
		}
	}

	/**
	 * 判断是否为数字.
	 * 
	 * @param v
	 * @return
	 */
	public static boolean isNumber(String v) {
		if (StringUtil.isEmpty(v))
			return false;
		char[] vs = v.toCharArray();
		for (int i = 0; i < vs.length;i++)
			if (vs[i] < 48 || vs[i] > 57)
				return false;
		return true;
	}

	/**
	 * 将整型转换成32位2进制字符串. speed: 47 31 47 47 31 31 31.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString(int num) {
		StringBuilder buf = new StringBuilder(32);
		for (byte j = 31; j >= 0; j--) {
			if (((1 << j) & num) != 0) {
				// shift left 31 & with 'i', get the higest.
				buf.append('1');
			} else
				buf.append('0');
		}
		return buf.toString();
	}

	/**
	 * 将整型转换成32位2进制字符串. speed: 16 16 15 16 31 31 16.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString0(int num) {
		char[] datas = new char[32];
		for (byte j = 31; j >= 0; j--) {
			if (((1 << j) & num) != 0) {
				// shift left 31 & with 'i', get the higest.
				datas[31 - j] = '1';
			} else
				datas[31 - j] = '0';
		}
		return new String(datas);
	}

	/**
	 * 将整型转换成32位2进制字符串. speed: 46 47 47 47 47 47 63.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString00(int num) {
		byte[] datas = new byte[32];
		for (byte j = 31; j >= 0; j--) {
			if (((1 << j) & num) != 0) {
				// shift left 31 & with 'i', get the higest.
				datas[31 - j] = 49; // 49
			} else
				datas[31 - j] = 48;
			// datas[31 - j] = (((1 << j) & num ) != 0) ? Byte.valueOf("49") :
			// Byte.valueOf("48");
		}
		return new String(datas);
	}

	/**
	 * 二进制字符串转换成整型.
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static int binaryToInteger(String number) {
		int length = number.length();
		if (number == null || !number.matches("^[01]+$") || length < 0 || length > 32)
			throw new RuntimeException("不是二进制数字!");
		int result = 0;
		char[] cs = number.toCharArray();
		for (int i = 0; i < length; i++) {
			if (cs[i] == '1' && i == length - 1)
				result += 1;
			else if (cs[i] == '1')
				result += (1 << (length - i - 1));
		}
		return result;
	}

	/**
	 * 二进制字符串转换成Short整型.
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static short binaryToShort(String number) {
		int length = number.length();
		if (number == null || !number.matches("^[01]+$") || length < 0 || length > 16)
			throw new RuntimeException("不是二进制数字!");
		short result = 0;
		char[] cs = number.toCharArray();
		for (byte i = 0; i < length; i++) {
			if (cs[i] == '1' && i == length - 1)
				result += 1;
			else if (cs[i] == '1')
				result += (1 << (length - i - 1));
		}
		return result;
	}

	/**
	 * 将byte转换成8位2进制字符串.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString(byte num) {
		StringBuilder buf = new StringBuilder(8);
		for (byte j = 7; j >= 0; j--) {
			if (((1 << j) & num) != 0) { // shift left 31 & with 'i', get the
											// higest.
				buf.append('1');
			} else
				buf.append('0');
		}
		return buf.toString();
	}

	/**
	 * 将short转换成16位2进制字符串.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString(short num) {
		StringBuilder buf = new StringBuilder(16);
		for (byte j = 15; j >= 0; j--) {
			if (((1 << j) & num) != 0) { // shift left 31 & with 'i', get the
											// higest.
				buf.append('1');
			} else
				buf.append('0');
		}
		return buf.toString();
	}

	/**
	 * 将long转换成64位2进制字符串.
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinaryString(long num) {
		StringBuilder buf = new StringBuilder(64);
		for (byte j = 63; j >= 0; j--) {
			if (((1 << j) & num) != 0) { // shift left 31 & with 'i', get the
											// higest.
				buf.append('1');
			} else
				buf.append('0');
		}
		return buf.toString();
	}

	/**
	 * 去除指定的字符：. 0001 wipe("0001", '0') 1
	 * 
	 * @param value
	 * @return
	 */
	public static String wipe(String value, char c) {
		char[] vs = value.toCharArray();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < vs.length;i++)
			if (vs[i] != c)
				buf.append(vs[i]);
		return buf.toString();
	}

	/**
	 * 去除指定字符的数字.
	 * 
	 * @param value
	 * @param c
	 * @return
	 */
	public static String wipeDigit(String value, char c) {
		char[] vs = value.toCharArray();
		int index = -1;
		for (int i = 0; i < vs.length; ++i) {
			if (vs[i] != c) {
				index = i;
				break;
			}
		}
		char[] ns = null;
		if (index != -1) {
			ns = new char[vs.length - index];
			System.arraycopy(vs, index, ns, 0, ns.length);
		}
		return new String(ns);
	}

	/**
	 * 将toBinaryString 和 wipeDigit 方法整合一起.
	 * 
	 * <pre>
	 * int 10 --> 
	 * 00000000000000000000000000001010 -- > 1010
	 * </pre>
	 * 
	 * @param num
	 * @return
	 */
	public static String toBinary(int num) {
		if (num == 0)
			return "0";
		String result = toBinaryString0(num);
		if (result == null)
			return "";
		char c = result.charAt(0);
		if (c == '0')
			return wipeDigit(result, c);
		else
			return result;
	}
	
	/**
	 * BigDecimal 四舍五入.
	 * @param src
	 * @param precision
	 * @return
	 */
	public static BigDecimal round(BigDecimal src, int precision) {
		return src.round(new MathContext(precision, RoundingMode.HALF_UP));
	}
}