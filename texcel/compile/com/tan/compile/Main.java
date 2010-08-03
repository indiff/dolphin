package com.tan.compile;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null  && args.length == 2) {
			invoke(CompilerUtil.compile(args[0]),args[1]);
		} else {
			Class<?> clazz = CompilerUtil.compile("Demo");
			invoke(clazz, "demo");
		}
	}

	private static void invoke(final Class<?> clazz, String string) {
		Object obj =  null;
		try {
			obj = clazz.newInstance();
			Method m = clazz.getMethod(string, new Class[]{});
			m.invoke(obj, new Object[]{});
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
