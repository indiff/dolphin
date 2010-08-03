package com.tan.svn;

import static com.tan.util.TanUtil.println;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tan.config.SvnConfig;


public class UpdaterMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SvnConfig config = new SvnConfig();
		
		String svnPath = config.getSvnpath();
		println("ECHO OFF");
		println("ECHO SvnPath: ", svnPath);
		println("SET PATH=" + svnPath + ";%PATH%");
		Map<String,String> map = config.getMap();
		
		Set<Entry<String,String>> entrys = map.entrySet();
		int size = entrys.size();
		println("ECHO Operate directory size :" ,size);
		
		Iterator<Entry<String,String>> iter = entrys.iterator();
		while (iter.hasNext()) {
			Entry<String,String> entry = iter.next();
			String key = entry.getKey(); File sfile = new File(key);
			String value = entry.getValue(); File dfile = new File(value);
			
			if (isDir(sfile)) {
				println("svn update ",key, " -r HEAD --force");
			}
			if (isDir(dfile)) {
				println("svn update ",value, " -r HEAD --force");
			}
		}
	}
	
	private static boolean isDir(File dir) {
		return dir != null && dir.exists() &&  dir.isDirectory();
	}
}
