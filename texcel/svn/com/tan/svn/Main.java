package com.tan.svn;

import static com.tan.util.TanUtil.println;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tan.config.SvnConfig;


public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SvnConfig config = new SvnConfig();
		
		String svnPath = config.getSvnpath();
		println("ECHO OFF");
		println("ECHO SVN-PATH: ", svnPath);
		println("SET PATH=" + svnPath + ";%PATH%");
		Map<String,String> map = config.getMap();
		
		Set<Entry<String,String>> entrys = map.entrySet();
		int size = entrys.size();
		println("ECHO Operate directory size :" ,size);
		
		Iterator<Entry<String,String>> iter = entrys.iterator();
		boolean flg = true;
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
				}
			}
			
			if (flg = isDir(dfile))
			svn(key, value);
		}
		SvnConfig.deleteFiles();
		
//		svn("C:\\tools\\svn-syn\\work\\mas\\", "C:\\tools\\svn-syn\\work\\sh\\", null);
	}
	
	private final static boolean isDir(File dir) {
		return dir != null && dir.exists() &&  dir.isDirectory();
	}

	public final static void svn(
			final String spath,
			final String dpath) {
		println("ECHO ****************************************");
		println("ECHO ", spath, "\tSource");
		println("ECHO ", dpath, "\tDestination");
		println("ECHO ****************************************");
		
		SVNSync svn = new SVNSync();
		svn.sync(spath, dpath);
	}
	
	
	public void oldSvn() {
//		SvnComparer comparer = new SvnComparer(spath, dpath);
//		comparer.compareDirectory(commit);
//
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		comparer.compareFiles();
	}
}
