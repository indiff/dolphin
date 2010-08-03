package com.tan.svn;

import static com.tan.util.TanUtil.appendln;
import static com.tan.util.TanUtil.println;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SVNSync {

	public static void main(String[] args) {
		SVNSync svn = new SVNSync();
		svn.sync("C:\\tools\\svn-syn\\mas", "C:\\tools\\svn-syn\\sh");
	}

	public final void sync(final String srcDir, final String dstDir) {
		List<String> srcDirs = getFiles(srcDir, srcDir.length());
		List<String> dstDirs = getFiles(dstDir, dstDir.length());
		Collections.sort(srcDirs);
		Collections.sort(dstDirs);
		StringBuilder svndelete = new StringBuilder();
		StringBuilder svnadd = new StringBuilder();
//		String commit = "svn commit --force-log --file \"" + dstDir + '"';
		// the svn commit's command.
		String commit = "svn commit -m \"\" \"" + dstDir + '"';
		String lastfile = "";
		
		for(String s : dstDirs){
			if(!exist(dstDir, srcDir, s)){
				if(lastfile.length() > 0 && s.startsWith(lastfile)){
					continue;
				}
				String filename = dstDir + s;
				File file = new File(filename);
				// append the svn delete's commands with "--force".
				appendln(svndelete, "svn delete --force \"", filename, '"');
				if(file.isDirectory()){
					lastfile = s + "\\";
				}
			}
			
		}
		// the svn delete's commands.
		println(svndelete);
		
		if (svndelete.length() > 0) {
			// the svn commit's commands.
			println(commit);
		}
		
		// the xcopy /s /k /r/ /y srcDir dstDir
		//s	复制目录和子目录，不包括空目录
		//e	复制目录和子目录，包括空目录
		println("xcopy /e/k/r/y \"", srcDir, "\" \"" , dstDir, '"');
		for(String s : srcDirs){
			if(!exist(srcDir, dstDir, s)){
				String filename = dstDir + s;
				//TODO: svn add "filename"
				appendln(svnadd, "svn add --force \"", filename, '"');
			}
		}
		
		// the svn add commands.
		println(svnadd);
		
		// the svn commit commands.
		println(commit);
	}

	private final boolean ignore(File file){
		if(".svn".equals(file.getName()) && file.isDirectory()){
			return true;
		}
		return false;
	}
	
	private final boolean exist(final String srcDir, 
			final String dstDir, 
			final String file) {
		File src = new File(srcDir + file);
		File dst = new File(dstDir + file);
		//file / dir exist
		if(!dst.exists()){
			return false;
		}
		//compare case
		String srcname = src.getAbsolutePath().substring(srcDir.length());
		String dstname = dst.getAbsolutePath().substring(dstDir.length());
		if(!srcname.equals(dstname)){
			return false;
		}
		//same type
		if(src.isFile() && !dst.isFile()){
			return false;
		}
		if(src.isDirectory() && !dst.isDirectory()){
			return false;
		}
		return true;
	}

	private final List<String> getFiles(final String dir, final int prefix) {
		ArrayList<String> result = new ArrayList<String>();
		String[] files = new File(dir).list();
		if (files != null) {
			for (String s : files) {
				String filename = dir + "\\" + s;
				File file = new File(filename);
				if (!ignore(file)) {
					if (file.isDirectory()) {
						File pointSvn = new File(file, ".svn");
						if (pointSvn.exists() && pointSvn.isDirectory()) {
							result.add(filename.substring(prefix));
							result.addAll(getFiles(filename, prefix));
						}
					}
					if (file.isFile()) {
						result.add(filename.substring(prefix));
					}
				}
			}
		}
		return result;
	}
}
