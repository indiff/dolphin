package com.tan.javadoc;

import java.io.File;
import java.io.FileFilter;

import com.sun.tools.javadoc.JavDocMain;
import com.tan.util.TanUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/15 13:50:20
 */
public class FieldsMain {

	public static void main(String[] args) {
		System.setProperty("sun.jnu.encoding", "utf-8");
		System.setProperty("file.encoding", "UTF-8");
//		String path = "D:\\PSWG\\workspace\\sshe\\src\\beans\\People.java";
		String path = "D:\\PSWG\\workspace\\pswg\\src\\main\\java\\jp\\co\\pswg\\entity\\";
		File f = new File(path);
		if (!f.exists()) {
			TanUtil.warnln("文件不存在");
		} else if (f.isFile() && path.toLowerCase().endsWith(".java")) {
			JavDocMain.showFieldsInfo(new String[] { "javadoc", path, "-encoding", "utf-8" });
		} else if (f.isDirectory()) {
			File[] javas = f.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile() && file.getName().toLowerCase().endsWith(".java");
				}
			});
			if (javas == null || javas.length == 0)
				TanUtil.warnln("该目录下没有java文件");
			for (File java : javas) {
				JavDocMain.showFieldsInfo(new String[] { "javadoc", java.getAbsolutePath(), "-encoding", "utf-8" });
			}
		}
	}
}
