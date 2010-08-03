package com.tan.util;

import static com.tan.util.TanUtil.warnln;

import java.lang.reflect.Field;
import java.util.Map;

import lombok.Getter;

import com.tan.compile.RTInvoker;

public abstract class AbstractConfig {
	private Class<?> clazz = null;
	private Field mapField = null;
	private @Getter Map<String, String> cache = null;
	private Object config = null;
	
	public AbstractConfig() {
		this("Config", "map");
	}
	
	public AbstractConfig(
			final String javaName,
			final String mapFieldName) {
		try {
			config = RTInvoker.compile(javaName).newInstance();
			clazz = config.getClass();
			mapField = clazz.getDeclaredField(mapFieldName);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		if (mapField == null) {
			warnln("can not get the map field.");System.exit(1);
		}
		if (!mapField.isAccessible())
			mapField.setAccessible(true);
		try {
			cache = (Map<String, String>) mapField.get(config);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		setMapValues();
	}
	
	abstract void setMapValues();
	
	public void destroy() {
		clazz = null;
		mapField = null;
		cache = null;
		config = null;
	}
}
