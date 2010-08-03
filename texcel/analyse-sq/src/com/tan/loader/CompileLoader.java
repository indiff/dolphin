package com.tan.loader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.tan.util.TanUtil;

public class CompileLoader {
	private static final String CURRENT_PATH = System.getProperty("user.dir");
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Object r = 
			invokeStaticMethod("Config", "getLeader", new Class[]{String.class}, "唐春根");
		TanUtil.println(r);
	}
	
	public static Class<?> compile(String javaName) {
		return compile(javaName, "utf-8");
	}
	
	public static Class<?> compile(final String javaName,
			final String encoding) {
		if (isEmpty(javaName))
			return null;
		File classpath = new File(CURRENT_PATH + File.separatorChar);
		File javaFile = new File(classpath, javaName + ".java");
		if (classpath == null || !classpath.exists() || classpath.isFile()) {
			TanUtil.warnln("Class Path File's Path Wrong");
			return null;
		}
		if (javaFile == null || !javaFile.exists() || javaFile.isDirectory()) {
			TanUtil.warnln("Java File's Path Wrong");
			return null;
		}
		URL url = null;
		try {
			url = classpath.toURI().toURL();
			com.sun.tools.javac.Main.compileAbsolutePath(javaFile.getAbsolutePath(), encoding);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		TanUtil.println("Loading... " + url);
		URLClassLoader loader = null;
		loader = new URLClassLoader(new URL[] { url });
		Class<?> c = null;
		try {
			c = loader.loadClass(javaName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return c;
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
	
	
	
	
	
	protected static void invokeConfig() {
		Class<?> c = compile("Config");
		TanUtil.println(c + " The url is right");
		Method m = null;
		try {
			m = c.getMethod("getLeader", new Class[]{String.class});
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			Object o = m.invoke(null, "xx");
			TanUtil.println(o);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
