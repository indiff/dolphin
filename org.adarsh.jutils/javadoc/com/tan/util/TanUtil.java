package com.tan.util;

/**
 * 工具类.
 * 
 * @author dolphin
 * 
 *         2010/06/02 16:50:59
 */
public final class TanUtil {

	public static void appendln(StringBuilder b, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			b.append(objects[i]);
		}
		b.append("\r\n");
	}

	public static boolean isNotNull(String name) {
		return null != name;
	}

	public static void append(StringBuilder b, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			b.append(objects[i]);
		}
	}}