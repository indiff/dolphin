package com.tan.qa.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * The directory's filter.
 * format is yyyyddMMまで.
 * @author dolphin
 *
 * 2010/07/13 16:02:48
 */
public class QaFileFilter implements FileFilter{
		@Override
		public boolean accept(File f) {
			String name = f.getName().toLowerCase();
			return f.isDirectory() && name.matches("^\\d{8}まで$");
		}
}
