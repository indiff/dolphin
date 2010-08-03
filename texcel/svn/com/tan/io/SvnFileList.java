package com.tan.io;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tan.bean.SvnFile;
import com.tan.util.TanUtil;

public class SvnFileList {
	private List<SvnFile> results;
	private String parent;
	private File root;
	
	/**
	 * Default recusive the files.
	 * @param root
	 */
	public SvnFileList(String key) {
		this(key,true);
	}
	
	/**
	 * <pre>
	 * Instance to recusive files.
	 * filterFile is true recusive the files.
	 * is false recusive the directories.
	 * </pre>
	 * @param root
	 */
	public SvnFileList(String key, boolean filterFile) {
		results = new ArrayList<SvnFile>();
		parent = key;
		root = new File(key);
		if (filterFile) {
			filterFiles(root);
		} else {
			filterDirectories(root);
		}
	}
	
	/**
	 * recusive the directories.
	 * @param dir
	 */
	private void filterDirectories(File dir) {
		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (!f.exists()) {
				continue;
			} else if (f.isDirectory()) {
				File pointSvn = new File(f, ".svn");
				if (f.getAbsolutePath().indexOf(".svn") < 0 &&
					pointSvn.exists() && 
					pointSvn.isDirectory()) {
					filterDirectories(f);
					results.add(new SvnFile(parent, f));
				}
			}
		}
	}
	
	private void filterFiles(File dir) {
		File[] fs = dir.listFiles();
		for (File f : fs) {
			if (!f.exists()) {
				continue;
			} else if (f.isDirectory()) {
				File pointSvn = new File(f, ".svn");
				if (f.getAbsolutePath().indexOf(".svn") < 0 &&
					pointSvn.exists() && 
					pointSvn.isDirectory()){
						filterFiles(f);
				}
			} else if (f.isFile()) {
				results.add(new SvnFile(parent, f));
			}
		}
	}
	
	public List<SvnFile> getResults() {
		return results;
	}
	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SvnFileList list = new SvnFileList("C:\\tools\\svn-syn\\mas\\project", true);
		List<SvnFile> results = list.getResults();
		Collections.sort(results);
		TanUtil.println("FILE\tNAME");
		for (SvnFile f : results) {
			TanUtil.println(f.isFile(), '\t', f.getName());
		}

//		MyFile file1 = 
//			new MyFile("C:\\tools\\svn-syn\\mas", new File(
//				"C:\\tools\\svn-syn\\mas\\qa\\提出\\002.xls"));
		
//		MyFile file2 = 
//			new MyFile("C:\\tools\\svn-syn\\mas", new File(
//				"C:\\tools\\svn-syn\\sh\\qa\\提出\\00005.xls"));
		
		
//		TanUtil.println(results.contains(file1));
//		TanUtil.println(file1.equals(file2));
	}
}
