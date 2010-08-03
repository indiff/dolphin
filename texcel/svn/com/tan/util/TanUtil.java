package com.tan.util;

import static java.lang.System.err;
import static java.lang.System.out;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 工具类.
 * 
 * @author dolphin
 * 
 *         2010/06/02 16:50:59
 */
public final class TanUtil {
	private static final String LINE = System.getProperty("line.separator", "\r\n");

	private TanUtil() {}
	/**
	 * 打印数组中的元素.
	 * 
	 * @param ts
	 *            数组.
	 */
	public final static <T> void printArray(T[] ts) {
		/*********************************
		 * version 0
		 * 
		 * for (T t : ts) { System.out.print(t + " "); } System.out.flush();
		 *********************************/
		System.out.println(Arrays.toString(ts));
	}

	/**
	 * 交换字节数组.
	 * 
	 * @param bs
	 *            数组引用.
	 * @param from
	 *            交换的位置1.
	 * @param to
	 *            交换的位置2.
	 * @throws Exception
	 */
	public final static void swap(byte bs[], int from, int to) throws Exception {
		if (from == to) {
			return;
		}
		if (from > bs.length || to > bs.length) {
			throw new Exception("输入的参数有误!");
		}

		byte temp = bs[from];

		bs[from] = bs[to];

		bs[to] = temp;
	}

	/**
	 * 交换数组from索引元素和头索引元素.
	 * 
	 * @param ts
	 *            数组.
	 * @param from
	 *            from索引.
	 * @param to
	 *            to索引.
	 * @throws Exception
	 */
	public final static <T> void swap(T[] ts, int from, int to) throws Exception {
		if (from == to) {
			return;
		}
		if (from > ts.length || to > ts.length) {
			throw new Exception("输入的参数有误!");
		}

		T temp = ts[from];

		ts[from] = ts[to];

		ts[to] = temp;
	}

	/**
	 * Do nothing for eclipse.
	 * 
	 * @param t
	 *            content.
	 */
	public static <T> void doNothing(T t) {
		// do nothing.
	}

	/**
	 * show the message.
	 * 
	 * @param t
	 *            content.
	 */
	public static <T> void alert(T t) {
		JOptionPane.showMessageDialog(null, t);
	}

	/**
	 * show the message and print the message in the console. when the alert is
	 * false can not showMessageDialog.
	 * 
	 * @param t
	 *            content.
	 * @param alert
	 *            isAlert.
	 */
	public static <T> void alert(T t, boolean alert) {
		if (alert)
			JOptionPane.showMessageDialog(null, t);
		System.out.println(t);
	}

	/**
	 * 启动Mysql服务.
	 */
	public static boolean startMysql() {
		stopMysql();
		return cmdMySQL("net start mysql");
	}

	/**
	 * 停止Mysql服务.
	 */
	public static boolean stopMysql() {
		return cmdMySQL("net stop  mysql");
	}

	private static boolean cmdMySQL(String cmd) {
		Process p = null;
		InputStream in = null;
		byte[] bs = new byte[256];
		int len = -1;
		StringBuilder builder = new StringBuilder();
		try {
			p = Runtime.getRuntime().exec(cmd);
			in = p.getInputStream();
			while ((len = in.read(bs)) != -1) {
				builder.append(new String(bs, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			in = null;
		}
		return builder.indexOf("MySQL") >= 0;
	}


	

	/**
	 * 简化的打印方法(换行).
	 */
	public static void println(Object t) {
		out.println(t);
	}

	/**
	 * 简化的打印方法(换行).
	 */
	public static void println(Object... keys) {
		println((char) -1, keys);
	}

	/**
	 * char sep
	 * 
	 * @param sep
	 *            分隔符.
	 * @param keys
	 *            要打印的对象列表.
	 */
	public static void println(char sep, Object... keys) {
		boolean flg = ((int) sep == 0xffff);

		if (keys == null || keys.length == 0) {
			return;
		}

		if (flg) {
			StringBuilder b = new StringBuilder();
			for (Object o : keys) {
				if (o != null) {
					b.append(o.toString());
				}
			}
			println(b);
			return;
		} else {
			StringBuilder builder = new StringBuilder("[");
			for (Object o : keys) {
				if (o != null) {
					builder.append(o.toString() + sep);
				}
			}
			int len = builder.length();
			if (len == 1)
				return;
			builder.append(']');
			builder.deleteCharAt(len - 1);
			println(builder);
		}
	}

	/**
	 * 简化的打印方法(换行).
	 */
	public static void println() {
		out.println();
	}

	/**
	 * 简化的打印方法(换行).
	 */
	public static void warnln() {
		err.println();
	}

	/**
	 * 简化的打印方法.
	 */
	public static void print(Object t) {
		out.print(t);
	}

	/**
	 * 简化的打印方法(换行).
	 */
	public static void print(Object... keys) {
		print((char) -1, keys);
	}

	/**
	 * char sep
	 * 
	 * @param sep
	 *            分隔符.
	 * @param keys
	 *            要打印的对象列表.
	 */
	public static void print(char sep, Object... keys) {
		boolean flg = ((int) sep == 0xffff);

		if (keys == null || keys.length == 0) {
			return;
		}

		if (flg) {
			StringBuilder b = new StringBuilder();
			for (Object o : keys) {
				if (o != null) {
					b.append(o.toString());
				}
			}
			print(b);
			return;
		} else {
			StringBuilder builder = new StringBuilder("[");
			for (Object o : keys) {
				if (o != null) {
					builder.append(o.toString() + sep);
				}
			}
			int len = builder.length();
			if (len == 1)
				return;
			builder.append(']');
			builder.deleteCharAt(len - 1);
			print(builder);
		}
	}

	/**
	 * 简化的打印方法(警告-换行).
	 */
	public static void warnln(Object t) {
		err.println(t);
	}

	/**
	 * 简化的打印方法(警告-换行).
	 */
	public static void warnln(Object... keys) {
		warnln((char) -1, keys);
	}

	/**
	 * char sep
	 * 
	 * @param sep
	 *            分隔符.
	 * @param keys
	 *            要打印的对象列表.
	 */
	public static void warnln(char sep, Object... keys) {
		boolean flg = ((int) sep == 0xffff);

		if (keys == null || keys.length == 0) {
			return;
		}

		if (flg) {
			StringBuilder b = new StringBuilder();
			for (Object o : keys) {
				if (o != null) {
					b.append(o.toString());
				}
			}
			warnln(b);
			return;
		} else {
			StringBuilder builder = new StringBuilder("[");
			for (Object o : keys) {
				if (o != null) {
					builder.append(o.toString() + sep);
				}
			}
			int len = builder.length();
			if (len == 1)
				return;
			builder.append(']');
			builder.deleteCharAt(len - 1);
			warnln(builder);
		}
	}

	/**
	 * 简化的打印方法(警告-换行).
	 */
	public static void warn(Object... keys) {
		warn((char) -1, keys);
	}

	/**
	 * char sep
	 * 
	 * @param sep
	 *            分隔符.
	 * @param keys
	 *            要打印的对象列表.
	 */
	public static void warn(char sep, Object... keys) {
		boolean flg = ((int) sep == 0xffff);

		if (keys == null || keys.length == 0) {
			return;
		}

		if (flg) {
			StringBuilder b = new StringBuilder();
			for (Object o : keys) {
				if (o != null) {
					b.append(o.toString());
				}
			}
			warn(b);
			return;
		} else {
			StringBuilder builder = new StringBuilder("[");
			for (Object o : keys) {
				if (o != null) {
					builder.append(o.toString() + sep);
				}
			}
			int len = builder.length();
			if (len == 1)
				return;
			builder.append(']');
			builder.deleteCharAt(len - 1);
			print(builder);
		}
	}

	/**
	 * 简化的打印方法(警告).
	 */
	public static void warn(Object t) {
		err.print(t);
	}


	/**
	 * 简化StringBuffer append方法.
	 * 
	 * @param b
	 * @param o
	 */
	public static void append(StringBuffer b, Object o) {
		b.append(o);
	}

	/**
	 * 简化StringBuffer append方法.
	 * 
	 * @param b
	 * @param o
	 */
	public static void append(StringBuffer b, Object... keys) {
		if (keys == null || keys.length == 0) {
			return;
		} else {
			for (Object o : keys) {
				if (o != null) {
					b.append(o);
				}
			}
		}
	}

	/**
	 * 简化StringBuilder append方法.
	 * 
	 * @param b
	 * @param o
	 */
	public static void append(StringBuilder b, Object o) {
		b.append(o);
	}

	/**
	 * 简化StringBuilder append方法.
	 * 
	 * @param b
	 * @param o
	 */
	public static void append(StringBuilder b, Object... keys) {
		if (keys == null || keys.length == 0) {
			return;
		} else {
			for (Object o : keys) {
				if (o != null) {
					b.append(o);
				}
			}
		}
	}
	
	public static void appendln(StringBuilder b, Object... keys) {
		if (keys == null || keys.length == 0) {
			return;
		} else {
			for (Object o : keys) {
				if (o != null) {
					b.append(o);
				}
			}
			b.append(LINE);
		}
	}
	
	/**
	 * show the input dialog, and return the input value.
	 * @return	the input value.
	 */
	public static String showInputDialog() {
		return (String) JOptionPane.showInputDialog(null, "Enter the :", "Po.lice",
				JOptionPane.PLAIN_MESSAGE, null, null, "http://htmlparser.sourceforge.net");
	}
	
	/**
	 * show the input dialog, and return the input value.
	 * @param title	Dialog's title.
	 * @return	the input value.
	 */
	public static String showInputDialog(String title) {
		return (String) JOptionPane.showInputDialog(null, title, "Po.lice",
				JOptionPane.PLAIN_MESSAGE, null, null, "http://htmlparser.sourceforge.net");
	}
	
	/**
	 * show the input dialog, and return the input value.
	 * @param title		Dialog's title.
	 * @param defaultValue	The default value for the Dialog.
	 * @return	the input value.
	 */
	public static String showInputDialog(String title, String defaultValue) {
		return (String) JOptionPane.showInputDialog(null, title, "Po.lice",
				JOptionPane.PLAIN_MESSAGE, null, null, defaultValue);
	}
	
	/**
	 * Chose the directory.
	 * @param dir	default directory.
	 * @param title	Dialog title.
	 * @return
	 */
	public static File chooseDir(final String dir, final String title) {
		Component parent = null; // Dialog上级组件
		JFileChooser chooser = new JFileChooser(dir);
		chooser.setDialogTitle(title);
		javax.swing.filechooser.FileFilter dirFilter = 
			new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory();
			}

			public String getDescription() {
				return "";
			}
		};
		chooser.setFileFilter(dirFilter);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * choose the file.
	 * @param dir
	 * @param title
	 * @return
	 */
	public static File chooseFile(final String dir, final String title) {
		Component parent = null; // Dialog上级组件
		JFileChooser chooser = new JFileChooser(dir);
		chooser.setDialogTitle(title);
		javax.swing.filechooser.FileFilter fileFilter = 
			new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.isFile();
			}
			public String getDescription() {
				return "";
			}
		};
		chooser.setFileFilter(fileFilter);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	public static boolean isEmpty(List<?> lists) {
		return lists == null || lists.size() == 0;
	}

}