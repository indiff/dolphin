package com.tan.compile;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Real time to compile and
 * load the class then invoke.
 * @author dolphin
 *
 * 2010/07/21 10:05:12
 */
public final class RTInvoker {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Object r = 
			invokeStaticMethod("Config", "getLeader", new Class[]{String.class}, "唐春根");
		System.out.println(r);
	}
	


	protected static boolean isEmpty(String value) {
		return value == null || value.trim().length() == 0;
	}
	
	public final static Object invokeStaticMethod(final String javaName, 
											final String methodName,
											final Class<?>[] parameterTypes,
											final Object ... args) {
		Class<?> clazz = compile(javaName);
		if (clazz == null) return null;
		if (isEmpty(methodName)) return null;
		Method method = null;
		Object result = null;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
			result = method.invoke(new Object[]{null}, args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static Class<?> compile(String javaName) {
		return CompilerUtil.compile(javaName);
	}


	public final static Method getMethod(
			final Class<?> clazz, 
			final String methodName,
			final Class<?>[] parameterTypes) {
		if (isEmpty(methodName)) return null;
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public final static Object invokeStaticMethod(
			final Method method,
			final Object ... args) {
		try {
			return method.invoke(new Object[]{null}, args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public final static Object invokeStaticMethod(final Class<?> clazz, 
			final String methodName,
			final Class<?>[] parameterTypes,
			final Object ... args) {
		if (clazz == null) return null;
		if (isEmpty(methodName)) return null;
		Method method = null;
		Object result = null;
		try {
			method = clazz.getMethod(methodName, parameterTypes);
			result = method.invoke(null, args);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Field getField(final Class<?> clazz,
			final String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
}
