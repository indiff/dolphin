package com.tan.svn;

import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tan.config.SvnConfig;


public class MainDebug {
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
		boolean flg = true;
		String commit = null;
		while (iter.hasNext()) {
			Entry<String,String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			File sfile = new File(key);
			File dfile = new File(value);
			
			/* source is directory, 
			 * but the destination directory is not a file
			 */
			if (flg = isDir(sfile) && !isDir(dfile)) {
				if (dfile.isFile() && dfile.exists()) dfile.delete();
				if (dfile.mkdir()) {
					println("svn add --force \"", dfile, '"');
//					commit = "svn commit --force-log --file " + dfile;
					commit = "svn commit -m \"\" \"" + dfile + '"';
				}
			}
			
			if (flg = isDir(dfile))
				svn(key, value, commit);
		}
		
		SvnConfig.deleteFiles();
		warnln("See the log debug.log");
//		svn("C:\\tools\\svn-syn\\mas\\qa\\", 
//				"C:\\tools\\svn-syn\\sh\\qa\\", null);
	}
	
	private static boolean isDir(File dir) {
		return dir != null && dir.exists() &&  dir.isDirectory();
	}

	public static void svn(
			final String spath,
			final String dpath,
			final String commit) {
		println("ECHO START\t********************");
		println("ECHO ", spath, "\tSource");
		println("ECHO ", spath, "\tDest");
		println("ECHO \t\t********************");
		SvnComparer comparer = new SvnComparer(spath, dpath);
		comparer.compareDirectory(commit);
		
		println("xcopy /k/r/y \"", spath, "\" \"" , dpath, '"');
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		comparer.compareFiles();
	}
}
