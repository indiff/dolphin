package com.tan.config;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tan.compile.RTInvoker;

/**
 * By AutoInvoker.
 * @author dolphin
 *
 * 2010/07/20 18:15:36
 */
public class SvnConfig {
	private Class<?> clazz = null;
	private Field mapField = null;
	private String svnPath = null;
	private Map<String, String> cache = null;
	private Object config = null;
	
	public SvnConfig() {
		try {
			config = RTInvoker.compile("Config").newInstance();
			clazz = config.getClass();
			mapField = clazz.getDeclaredField("map");
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
		setSvnPath();
	}
	
	
	/**
	 * 
	 * @return
	 */
	public void setSvnPath() {
		if (svnPath == null) {
			if (cache == null) init();
			svnPath = cache.get("svn-path");
			cache.remove("svn-path");
		}
	}
	
	public String getSvnpath() {
		return svnPath;
	}
	
	public Map<String, String> getMap() {
		return cache;
	}
	
	public void destroy() {
		clazz = null;
		mapField = null;
		svnPath = null;
		cache = null;
		config = null;
	}
	
	public static boolean deleteFiles() {
		String parent = System.getProperty("user.dir") + 
		File.separatorChar;
		File config = new File(parent,"Config.class");
//		File gogo = new File(parent,"gogo.bat");
//		File update = new File(parent,"update.bat");
		boolean flg = false;
		if (config.exists()) flg = config.delete();
//		if (gogo.exists()) flg = gogo.delete();
//		if (update.exists()) flg = update.delete();
		return flg;
	}
	
	public static void main(String[] args) throws Throwable {
		SvnConfig config = new SvnConfig();
		println("Your svn path :" + config.getSvnpath());
		
		Set<Entry<String, String>> entrys = config.cache.entrySet();
		
		for (Entry<String,String> entry : entrys) {
			println("key:",entry.getKey(),",value:",entry.getValue());
		}
		config.destroy();
	}
}
