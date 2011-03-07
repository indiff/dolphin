package com.tan.app;


import static com.tan.util.TanUtil.chooseDir;
import static com.tan.util.TanUtil.warn;
import static com.tan.util.TanUtil.warnln;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Locale;

import com.sun.tools.javadoc.JavDocMain;
import com.tan.util.StringUtil;

/**
 * 
 * @author dolphin
 *
 * 2010/07/15 13:50:20
 */
public class SetterGetterByDirMain {
	private static BigDecimal PERCENT100 = new BigDecimal(100);
	
	private static MathContext MATH_CONTEXT = new MathContext(3, RoundingMode.HALF_UP);

	
	public static void main(String[] args) {
//		System.setProperty("sun.jnu.encoding", "utf-8");
//		System.setProperty("file.encoding", "UTF-8");
		String encoding = System.getProperty("sun.jnu.encoding", "utf-8");
		String defaultDir = "c:\\";
//		String defaultDir = "D:\\PSWG\\workspace\\pswg\\src\\main\\java\\jp\\co\\pswg\\entity\\";
		if (null != args && args.length > 0 &&  null != args[0]) {
			encoding = args[0];
			if (args.length > 1) {
				if ("en".equalsIgnoreCase(args[1])) {
					Locale.setDefault(Locale.SIMPLIFIED_CHINESE);
				} else if ("en".equalsIgnoreCase(args[1])) {
					Locale.setDefault(Locale.ENGLISH);
				} else if ("ja".equalsIgnoreCase(args[1])) {
					Locale.setDefault(Locale.JAPANESE);
				}
			}
			if (args.length > 2) {
				defaultDir = args[2];
			}
		}
		File f = chooseDir(defaultDir, 
				StringUtil.getString("tan", "tan.choose.directory.title"));
		if (f == null) {
			exitWithMessage(StringUtil.getString("tan", "tan.message.nullpath"));
		}
		if (!f.exists()) {
			exitWithMessage(StringUtil.getString("tan", "tan.message.filenotexists"));
		} else if (f.isDirectory()) {
			try {
				System.setOut(new PrintStream("setter-getter-by-dir.log"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			warnln("Directory: " + f);
			File[] javas = f.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile() && file.getName().toLowerCase().endsWith(".java");
				}
			});
			if (javas == null || javas.length == 0)
				exitWithMessage(StringUtil.getString("tan", "tan.message.no_java_file"));
			
			int length = javas.length;
			int index = 0;
			for (File java : javas) {
				index++;
				JavDocMain.showSetterGetter(new String[] { "javadoc", java.getAbsolutePath(), "-encoding", encoding});
				warn(StringUtil.getString("tan", "tan.message.finish", java.getName()));
				showPercent(index, length);
			}
		}
		
		
	}

	private static void exitWithMessage(final String message) {
		warnln(message); System.exit(0);
	}
	
	
	/**
	 * show percent for the task.
	 * @param index
	 * @param size
	 */
	private static synchronized void showPercent(final int index, final int size) {
		BigDecimal i = new BigDecimal(index);
		BigDecimal s = new BigDecimal(size);
		warnln(" ",i.divide(s,MATH_CONTEXT).multiply(PERCENT100,MATH_CONTEXT) ,"%!");
	}
}
