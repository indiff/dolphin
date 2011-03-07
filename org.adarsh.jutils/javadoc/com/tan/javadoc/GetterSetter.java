/*
 * Copyright 2000-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.tan.javadoc;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

import com.sun.tools.javadoc.JavDocMain;
import com.tan.util.TanUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/15 13:50:20
 */
public class GetterSetter {

	public static void main(String[] args) {
		System.setProperty("sun.jnu.encoding", "utf-8");
		System.setProperty("file.encoding", "UTF-8");
//		File file = TanUtil.chooseDir("D:\\PSWG\\workspace\\pswg\\src\\main\\java\\jp\\co\\pswg\\entity\\", "title");
		String path = "D:\\PSWG\\workspace\\pswg\\src\\main\\java\\jp\\co\\pswg\\entity\\SummaryDb.java";
//		String path = "D:\\PSWG\\workspace\\sshe\\src\\beans\\People.java";
		Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
//		String path = "D:\\PSWG\\workspace\\pswg\\src\\main\\java\\jp\\co\\pswg\\entity\\";
		File f = new File(path);
		if (!f.exists()) {
			TanUtil.warnln("文件不存在");
		} else if (f.isFile() && path.toLowerCase().endsWith(".java")) {
			JavDocMain.showSetterGetter(new String[] { "javadoc", path, 
					"-encoding", "utf-8", });
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
				JavDocMain.showSetterGetter(new String[] { "javadoc", java.getAbsolutePath(), "-encoding", "utf-8" });
			}
		}
	}
}
