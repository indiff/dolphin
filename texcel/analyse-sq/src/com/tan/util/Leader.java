package com.tan.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tan.loader.CompileLoader;

/**
 * by CompileLoader.
 * @author dolphin
 *
 * 2010/07/20 18:15:46
 */
public class Leader {
	private Class<?> clazz = null;
	private Method method = null;
	private String oldPath = null;
	private Map<String, String> cache = null;
	
	public Leader() {
		clazz = CompileLoader.compile("Config");
		method = CompileLoader.getMethod(
				clazz, 
				"getLeader", 
				new Class[]{String.class});
		cache = new HashMap<String,String>();
	}
	
	public String getLeader(final String name) {
		String result = cache.get(name);
		String value = null;
		if (result == null) {
			Object o = 
				CompileLoader.invokeStaticMethod(method, new Object[]{name});
			value = String.valueOf(o);
			cache.put(name, value);
		} else {
			return result;
		}
		if (value == null) return "No-Value";
		return value;
	}
	
	public String getOldPath() {
		if (oldPath != null)
			return oldPath;
		Object r = 
			CompileLoader.invokeStaticMethod(clazz, 
					"getOldPath", 
					new Class<?>[]{}, 
					new Object[]{});
		oldPath = String.valueOf(r);
		return oldPath;
	}
	
	public static void main(String[] args) {
		Leader leader = new Leader();
		TanUtil.println(leader.getLeader("x"));
		TanUtil.println(leader.getOldPath());
	}
}
