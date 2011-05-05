package com.tan.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Editor {
	private static String EDITPLUS_PATH = null; // 缓存 Editplus路径.
	
	final static String ABSOLUTE_PATH = "HKEY_CLASSES_ROOT\\Applications\\EDITPLUS.EXE\\shell\\edit\\command"; //注册表的绝对项
	final static String DATA_TYPE = "REG_SZ"; //注册表的值的数据类型
	final static String[] EIDTPLUS_KYE_WORDS = { "EDITPLUS.EXE",
			"EDITPLUS1.EXE", "EDITPLUS2.EXE", "EDITPLUS3.EXE", "EDITPLUS4.EXE",
			"EDITPLUS5.EXE", "EDITPLUS6.EXE" }; //查找Editplus注册表的关键字.
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getEditplusPath());
	}

	public static String getEditplusPath() {
		if (EDITPLUS_PATH != null) {
			return EDITPLUS_PATH;
		} else {
			String absolutePath = null;
			absolutePath = analyse(readCmd(ABSOLUTE_PATH));
			if (null != absolutePath && new File(absolutePath).isFile()) {
				EDITPLUS_PATH = "\"" + absolutePath + "\"";
			}
		}
		return EDITPLUS_PATH;
	}



	final static String analyse(final String data) {
		final String dataUpperCase = data.toUpperCase();
		int start = dataUpperCase.indexOf(DATA_TYPE) + DATA_TYPE.length();
		if (start < 0) {
			return null;
		}
		int end = -1;
		for (int i = 0; i < EIDTPLUS_KYE_WORDS.length; i++) {
			end = dataUpperCase.indexOf(EIDTPLUS_KYE_WORDS[i]);
			if (end >= 0) {
				end += EIDTPLUS_KYE_WORDS[i].length();
				break;
			}
		}
		if (end < 0) {
			return null;
		}

		if (end >= start)
			return data.substring(start, end).trim();
		else {
			System.err.println("查找位置出错!");
			return null;
		}
	}

	private static String readCmd(final String path) {
		Process p = null;
		final String command = "reg query \"" + path + "\" /ve "; ///t " + DATA_TYPE;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line;
		StringBuffer buf = new StringBuffer();
		try {
			while (null != (line = reader.readLine())) {
				buf.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return StringUtil.replace(buf.toString(), path, "").trim();
	}

}
