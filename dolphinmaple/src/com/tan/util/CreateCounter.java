package com.tan.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class CreateCounter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		char[] cs = "tanyuanji".toCharArray();
		int count = 0;
		for (char c : cs) {
			count += (int)c;
			System.out.println( c + Integer.toHexString((int)c));
			/**
			 * 74
				61
			6e
			79
75
61
6e
6a
69*/
		}
		System.out.println("count --> " + count);
		System.out.println("length --> " + cs.length);
		System.out.println(checkSecurity("tatatatat"));
	}
	
	public static boolean checkSecurity(String password) {
		char[] cs = password.toCharArray();
		boolean r = checkLength(password);
		if (!r) return false;
		
		// 第一个和最后一个
		if (cs[0] == cs[cs.length-1]) return false;
		
		// 范围
		for (int i = 0 ; i < cs.length; i++) {
			r = checkSecurity(cs[i]);
			if (!r) return false;
			
			// check for the character 'n' and 'u'
			if (cs[i] == 0x6e || cs[i] == 0x75) continue;
			
//			System.out.println("cs[i] == cs[cs.leng - 1 - i" + cs[i] + " " + cs[cs.length -1 -i]);
			// 两边对应
			if (cs[i] == cs[cs.length - 1 - i]) return false;		
			
			// 前后不能相等
			if (i != 0 && cs[i] == cs[i - 1]) {
				return false;
			}
		
		}
		return r;
	}
	
	private static boolean checkSecurity(int v) {
		// 字母的范围
		switch (v) {
			case 0x74: return true;
			case 0x61: return true;
			case 0x6e: return true;
			case 0x79: return true;
			case 0x75: return true;
			case 0x69: return true;
			case 0x6a: return true;
			default : return false;
		}
	}
	
	private static boolean checkLength(String password) {
		// 字符串的长度
		return password.length() == 9;
	}

	public static int getCounter() throws IOException {
		boolean flg = false;
		int result = -1;
		final File parent = new File(CreateCounter.class.getResource("/").getFile());
		File[] fs = parent.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".counter");
//				return name.toLowerCase().indexOf(".") == -1;
			}
		});
		if (fs == null || fs.length == 0) {
			flg = new File(parent, "1.counter").createNewFile();
			return 1;
		}
		for (File f : fs) {
			String temp = f.getName();
			String digit = temp.substring(0, temp.lastIndexOf("."));
			if (digit.matches("^\\d+$")) {
				result = Integer.parseInt(digit) + 1;
				flg = new File(parent, new StringBuilder(".counter").insert(0, result).toString()).createNewFile();
				flg = f.delete();
			}
			break;
		}
		return result;
	}

	public static int getCounterBak() throws IOException {
		boolean flg = false;
		int result = -1;
		File parent = new File(CreateCounter.class.getResource("/").getFile());
		File[] fs = parent.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".counter");
//				return name.toLowerCase().indexOf(".") == -1;
			}
		});
		if (fs == null || fs.length == 0) {
			flg = new File(parent, "1.counter").createNewFile();
			System.out.println("you run me 1 counter.");
			return 1;
		}
		for (File f : fs) {
			String temp = f.getName();
			String digit = temp.substring(0, temp.lastIndexOf("."));
			if (digit.matches("^\\d+$")) {
				result = Integer.parseInt(digit) + 1;
				System.out.println("you run me " + result + " counter.");
				flg = new File(parent, new StringBuilder(".counter").insert(0, result).toString()).createNewFile();
				flg = f.delete();
			}
			break;
		}
		if (!flg) {
			System.out.println("error");
		} else {
			System.out.println("successful");
		}
		return result;
	}
}
