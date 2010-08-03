package com.tan.file;

import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
/**
 * search the directory's excels.
 * @author dolphin
 *
 * 2010/07/07 16:44:05
 */
public class Searcher {
	
	private List<File> results;
	
	private String extensions[];
	
	public List<File> getResults() {
		return results;
	}
	
	public Searcher(String dir) {
		results = new ArrayList<File>();
		File root = getRootDir(dir);
		if (root != null) {
			search(root);
		}
	}
	
	public Searcher(String dir, String ... exts) {
		results = new ArrayList<File>();
		extensions = exts;
		File root = getRootDir(dir);
		if (root != null) {
			search(root);
		}
	}
	
	public Searcher(File dir, String ... exts) {
		results = new ArrayList<File>();
		extensions = exts;
		if (dir != null) {
			search(dir);
		}
	}
	
	private File getRootDir(String dir) {
		if (dir == null || dir.length() == 0) {
			return null;
		}
		File root = new File(dir);
		if (!root.exists()) {
			warnln("文件不存在");
		}
		
		if (root.isFile()) {
			warnln("你输入文件夹的路径");
		}		
		return root;
	}
	
	/**
	 * search the all excel when the root directory's child
	 * had excel file.
	 * @param root
	 */
	public void search(File root) {
		if (extensions == null || extensions.length == 0) {
			return;
		}
		File[] roots = root.listFiles();
		for (File f : roots) {
			if (f.isDirectory()) {
				search(f);
			} else if (f.isFile()) {
				String fileName = f.getName().toLowerCase();
				for (String ext : extensions) {
					if (fileName.endsWith(ext)) {
						results.add(f);
					}
				}
			}
		}
	}
	
	/**
	 * search current path's excels and
	 * do not search child path's excels.
	 * @param root
	 */
	public void search0(File root) {
		if (extensions == null || extensions.length == 0) {
			return;
		}
		File[] roots = root.listFiles();
		for (File f : roots) {
			if (f.isFile()) {
				String fileName = f.getName().toLowerCase();
				for (String ext : extensions) {
					if (fileName.endsWith(ext)) {
						results.add(f);
					}
				}
			}
		}
	}
	
	
	/**
	 * Test find the jar's information.
	 * by the root dir and the key word.
	 * @param args
	 */
	public static void main(String[] args) {
//		final String dir = "D:\\Project\\librarys\\hibernate-distribution-3.5.3-Final-dist";
//		final String dir = "D:\\Project\\librarys\\spring-framework-3.0.3.RELEASE-with-docs";
		final String dir = "D:\\Project\\librarys\\struts-2.1.8.1-all";
		
		search(dir, "Text");
//		search(dir, "org/aopalliance/aop/Advice");
//		search(dir, "AnnotationSessionFactoryBean");
//	search(dir, "PropertyAccessor");
//		search(dir, "CachedIntrospectionResults");
	
	}
	
	
	private static void search(final String dir, final String key) {
//		final String dir = "D:\\eclipse-helios\\eclipse\\plugins";
		
		Searcher search = new Searcher(dir, ".jar");
		
		List<File> results = search.getResults();
//		org.springframework.beans.CachedIntrospectionResults
		for (File jar : results) {
//			System.out.println(jar);
			JarFile jarFile = null;
			try {
				jarFile = new JarFile(jar);
			} catch (IOException e) {
				// ignore
			}
			if (jarFile != null) {
				Enumeration<JarEntry> enumeration = 
					jarFile.entries();
				
				while (enumeration.hasMoreElements()) {
					JarEntry entry = enumeration.nextElement();
					String name = entry.getName();
					if (name.endsWith(".class") && name.contains(key)) {
						if ((key + ".class").equalsIgnoreCase(name))
							System.err.println(name + "\r\n" + jar.getName() +  "\r\n" + jar);
						else
							System.out.println(name + "\r\n" + jar.getName() +  "\r\n" + jar);
						
						System.out.println();
					}
				}
			}
		}
	}
	
	
}
