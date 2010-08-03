package com.tan.svn;

import static com.tan.util.TanUtil.appendln;
import static com.tan.util.TanUtil.print;
import static com.tan.util.TanUtil.println;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.tan.bean.SvnFile;
import com.tan.io.SvnFileList;

public class SvnComparer {
	private String source;
	private String destination;

	/**
	 * Compare the svn paths.
	 * 
	 * @param source
	 *            the source svn path.
	 * @param destination
	 *            the dest svn path.
	 */
	public SvnComparer(final String source, final String destination) {
		this.source = source;
		this.destination = destination;
	}

	/**
	 * Compare the directory. debug the informatioin of the command.
	 */
	public void compareDirectory(final String c) {
		List<SvnFile> sourcesDirs = recusiveDirectories(source);
		List<SvnFile> delDirs = recusiveDirectories(destination);
		List<SvnFile> addDirs = new ArrayList<SvnFile>();
		List<SvnFile> updDirs = new ArrayList<SvnFile>();
		Collections.sort(sourcesDirs);
		StringBuilder svn = new StringBuilder();
		StringBuilder commit = new StringBuilder();
		StringBuilder svndelete = new StringBuilder();
		if (c != null && c.trim().length() != 0)
			appendln(commit, c);

		for (Iterator<SvnFile> siter = sourcesDirs.iterator(); siter.hasNext();) {
			SvnFile dir = siter.next();
			File dest = new File(destination + dir.getName());
			if (delDirs.contains(dir)) {  // find in dest
				updDirs.add(dir);
				delDirs.remove(dir);
			} else {  // not in dest
				// delete if is file
				if (dest.exists() && dest.isFile()) {
					appendln(svndelete, "svn delete --force \"", dest, '"');
					dest.delete();
				}
				// mkdir list
				addDirs.add(dir);
			}
		}
		

		
//		List<SvnFile> sourcesFiles = recusiveFiles(source);
//		List<SvnFile> destFiles = recusiveDirectories(destination);
//		for (Iterator<SvnFile> siter = sourcesDirs.iterator(); siter.hasNext();) {
//			SvnFile dir = siter.next();
//			File dest = new File(destination + dir.getName());
//			if (sourcesDirs.contains(dir)) {
//				delDirs.remove(dir);
//			} else {
//				if (dest.exists() && dest.isFile()) {
//					appendln(svndelete, "svn delete --force \"", f, '"');
//					dest.delete();
//				}
//				addDirs.add(dir);
//			}
//		}
		

//		if (delDirs != null && delDirs.size() != 0) {
//			Collections.sort(delDirs);
//			for (Iterator<SvnFile> iter = delDirs.iterator(); iter.hasNext();) {
//				SvnFile d = iter.next();
//				File f = d.getFile();
//				if (f.exists() && f.isDirectory()) {
//					appendln(svndelete, "rmdir /s/q \"", f, '"');
//					appendln(svndelete, "svn delete --force \"", f, '"');
//				}
//			}
//		}
//
//		for (Iterator<SvnFile> siter = addDirs.iterator(); siter.hasNext();) {
//			SvnFile dir = siter.next();
//			File dest = new File(destination + dir.getName());
//			appendln(svn, "mkdir \"", dest.getAbsolutePath(), '\"');
//			appendln(svn, "svn add --force \"", dest.getAbsolutePath(), '\"');
//		}
//		
//		println(svndelete);
//		if (svndelete.length() != 0)
//			println("svn commit -m \"\" \"", destination, '\"');
//		println(svn);
//		if (svn.length() != 0)
//			println("svn commit -m \"\" \"", destination, '\"');
	}

	/**
	 * Compare the files. debug the informatioin of the command.
	 */
	public void compareFiles() {
		List<SvnFile> sourceFiles = recusiveFiles(source);
		List<SvnFile> destFiles = recusiveFiles(destination);
		StringBuilder copy = new StringBuilder();
		StringBuilder delete = new StringBuilder();
		boolean runnable = false;

		for (Iterator<SvnFile> siter = sourceFiles.iterator(); siter.hasNext();) {
			SvnFile file = siter.next();
			File dest = new File(destination + file.getName());
			if (dest.exists()) {
				runnable = destFiles.remove(dest);
				// appendln(copy, "COPY \"", file.getAbsolutePath() , "\" \"",
				// dest, '\"');
			} else {
				// appendln(copy, "COPY \"", file.getAbsolutePath(), "\" \"",
				// dest, '\"');
				appendln(copy, "svn add --force \"", dest, '\"');
			}

		}
		// appendln(copyCommit, "svn commit -m \"\" \"", destination, '"');
		if (runnable) // if run the remove.
			for (Iterator<SvnFile> diter = destFiles.iterator(); diter.hasNext();) {
				SvnFile file = diter.next();
				appendln(delete, "svn delete --force \"", file.getAbsolutePath(), '"');
				// appendln(deleteCommit, "svn commit --force-log --file ",
				// file.getAbsolutePath());
				// appendln(deleteCommit, "svn commit -m \"\" \"",
				// file.getAbsolutePath(), '"');
			}

		// appendln(deleteCommit, "svn commit -m \"\" \"", destination, '"');

		println(delete);
		if (delete.length() != 0)
			print("svn commit -m \"\" \"", destination, '"');
		print(copy);
		if (copy.length() != 0)
			println("svn commit -m \"\" \"", destination, '"');
	}

	/**
	 * Create the directory.
	 * 
	 * @param dest
	 * @return
	 */
	private String makeDirectory(File file) {
		/** 文件存在直接是目录返回 True. **/
		if (file.exists() && file.isDirectory())
			return "EXISTS";
		/** 文件存在直接是文件删除文件. **/
		if (file.exists() && file.isFile()) {
			file.delete();
		}
		StringBuilder svnadd = new StringBuilder();
		/** 文件不存在使用栈来保存文件, 先进后出. **/
		if (!file.exists()) {
			Stack<File> stack = new Stack<File>();
			stack.add(file);
			do {
				file = file.getParentFile();
				stack.add(file);
			} while (!file.exists());

			while (!stack.isEmpty()) {
				File f = stack.pop();
				if (f.mkdir()) {
					appendln(svnadd, "svn add --force ", f);
				}
			}
		}
		return svnadd.toString();
	}

	/**
	 * recusive the files.
	 * 
	 * @param key
	 * @return
	 */
	public static List<SvnFile> recusiveFiles(final String key) {
		return recusive(key, true);
	}

	/**
	 * Recusive directories.
	 * 
	 * @param dir
	 * @return
	 */
	public static List<SvnFile> recusiveDirectories(final String dir) {
		return recusive(dir, false);
	}

	private static List<SvnFile> recusive(final String dir, final boolean filterFile) {
		File file = new File(dir);
		if (file == null || !file.exists() || file.isFile()) {
			warnln(file + " Directory is wrong or is not a directory!");
			return null;
		}
		if (filterFile) {
			return new SvnFileList(dir).getResults();
		}
		return new SvnFileList(dir, false).getResults();
	}

}
