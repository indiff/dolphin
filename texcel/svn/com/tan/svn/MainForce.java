package com.tan.svn;

import static com.tan.util.TanUtil.appendln;
import static com.tan.util.TanUtil.println;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tan.config.SvnConfig;


public class MainForce {
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
		MainForce force = new MainForce();
		String commit = null;
		MyFileFilter filter = new MyFileFilter();
		while (iter.hasNext()) {
			Entry<String,String> entry = iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			force.svn(key, value);
		}
		SvnConfig.deleteFiles();
		
//		svn("C:\\tools\\svn-syn\\work\\mas\\", "C:\\tools\\svn-syn\\work\\sh\\", null);
	}
	
	static class MyFileFilter implements FileFilter {
		@Override
		public boolean accept(File f) {
			return f.exists() && f.isDirectory() && f.getName().indexOf(".svn") < 0;
		}
	}
	private boolean isDir(File dir) {
		return dir != null && dir.exists() &&  dir.isDirectory();
	}

	public void svn(
			final String spath,
			final String dpath) {
		File sfile = new File(spath);
		File dfile = new File(dpath);
		MyFileFilter filter = new MyFileFilter();
		boolean flg = true;
		String commit = "svn commit -m \"\" \"" + dfile + '"';
		StringBuilder add = new StringBuilder();
		if (flg = isDir(sfile) && !isDir(dfile)) {
			if (dfile.isFile() && dfile.exists()) dfile.delete();
			if (dfile.mkdir()) {
				appendln(add, "svn add --force \"", dfile, '"');
			}
		}
		
		if (flg = isDir(dfile)) {
			
		}
	}
}
