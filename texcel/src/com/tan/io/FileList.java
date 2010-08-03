package com.tan.io;

import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.tan.util.StringUtil;
import com.tan.util.TanUtil;

public class FileList {
	private @Getter List<File> results;
	private File root;
	private @Setter String extensions[];
	
	public FileList() {
		results = new ArrayList<File>();
	}
	
	public FileList(File r) {
		this();
		root = r;
	}
	
	
	public FileList(File r, String  ... exts) {
		this();
		root = r;
		if (exts == null || exts.length == 0) {
			try {
				throw new Exception("扩展名不能为空.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		extensions = exts;
		filter();
	}
	
	public void filter() {
		if (root == null) {
			warnln("The root directory can not be null."); return;
//			warnln("目录为空!"); return;
		}
		
		for (String ext : extensions) {
			if (StringUtil.isEmpty(ext)) {
				warnln("The file's extension can not be null."); return;
//				warnln("文件扩展名为空!"); return;
			}
		}
		
		File[] files = root.listFiles();
		
		for (File f : files) {
			if (!f.exists()) {
				continue;
			} else if (f.isDirectory()) {
				filter(f);
			} else if (f.isFile()) {
				String name = f.getName().toLowerCase();
				for (String ext : extensions) {
					if (name.endsWith(ext)) {
						results.add(f);
					}
				}
			}
		}
			//root.listFiles(new Filter(extension));
	}
	
	public void filter(File dir) {
		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (!f.exists()) {
				continue;
			} else if (f.isDirectory()) {
				filter(f);
			} else if (f.isFile()) {
				String name = f.getName().toLowerCase();
				for (String ext : extensions) {
					if (name.endsWith(ext)) {
						results.add(f);
					}
				}
			}
		}
	}
	
	private static class Filter implements FileFilter {
		private String name;
		private boolean isDir;
		
		public Filter(String n) {
			name = n;
		}
		
		public Filter(String n, boolean isDir) {
			this.isDir = isDir;
		}
		
		@Override
		public boolean accept(File file) {
			if (StringUtil.isEmpty(name)) return false;
			String path = file.getAbsolutePath().toLowerCase();
			if (isDir) {
				return file.isDirectory() && path.endsWith(name);
			} else 
				return file.isFile() && path.endsWith(name);
		}
		
	}
	
	public static void main(String[] args) {
		FileList list =  new FileList(new File("c:\\tools"), ".xls", ".txt");
		
		List<File> results = list.getResults();
		for (File f : results) {
			TanUtil.println(f);
		}
	}
}
