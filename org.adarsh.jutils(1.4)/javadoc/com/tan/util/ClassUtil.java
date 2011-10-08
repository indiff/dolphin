package com.tan.util;

import java.math.BigDecimal;
import java.util.Date;


public final class ClassUtil {
	private ClassUtil() {}
	
	public static Object createDummyType(Class paramType) {
		if (String.class == paramType) {
			return "\"\"";
		} else if (int.class == paramType) {
			return new Integer(0);
		} else if (byte.class == paramType) {
			return "(byte)0";
		} else if (short.class== paramType) {
			return "(short)0";
		} else if (long.class == paramType) {
			return "0L";
		} else if (char.class == paramType) {
			return "'0'";
		} else if (float.class == paramType) {
			return "0F";
		} else if (boolean.class == paramType) {
			return "false";
		} else if (Integer.class == paramType) {
			return new Integer(0);
		} else if (Byte.class == paramType) {
			return "(byte)0";
		} else if (Short.class== paramType) {
			return "(short)0";
		} else if (Long.class == paramType) {
			return "0L";
		} else if (Character.class == paramType) {
			return "'0'";
		} else if (Float.class == paramType) {
			return "0F";
		} else if (Boolean.class == paramType) {
			return "false";
		} else if (BigDecimal.class == paramType) {
			return "new BigDecimal(0)";
		} else if (Date.class == paramType) {
			return "new Date()";
		} 
		return null;
	}
	
	public static Object createDummyType(String paramType) {
		if ("java.lang.String".equals(paramType)) {
			return "\"\"";
		} else if ("java.lang.Integer".equals(paramType)) {
			return new Integer(0);
		} else if ("int".equals(paramType)) {
			return new Integer(0);
		} else if ("java.lang.Byte".equals(paramType)) {
			return "(byte)0";
		} else if ("byte".equals(paramType)) {
			return "(byte)0";
		} else if ("java.lang.Boolean".equals(paramType)) {
			return "true";
		} else if ("boolean".equals(paramType)) {
			return "true";
		} else if ("java.lang.Character".equals(paramType)) {
			return "' '";
		} else if ("char".equals(paramType)) {
			return "' '";
		} else if ("long".equals(paramType)) {
			return new Integer(0);
		} else if ("java.lang.Short".equals(paramType)) {
			return "(short)0";
		} else if ("short".equals(paramType)) {
			return "(short)0";
		} else if ("java.util.Date".equals(paramType)) {
			return "new java.util.Date()";
		} else if ("java.lang.Float".equals(paramType)) {
			return ".0f";
		} else if ("float".equals(paramType)) {
			return ".0f";
		} else if ("java.lang.Double".equals(paramType)) {
			return ".0d";
		} else if ("double".equals(paramType)) {
			return ".0d";
		} else if ("java.math.BigDecimal".equals(paramType)) {
			return "new BigDecimal(0)";
		} else if(StringUtil.isNotNull(paramType)){
			return "new " + paramType + "()";
		}
		return "null";
	}
	
	public static void main(String[] args) {
	}
}
