package com.tan.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Editor {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(getEditplusPath());
	}
	public static String getEditplusPath() {
		try {
			return "\"" + analyse(readCmd(ABSOLUTE_PATH)) + "\"";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	final static String ABSOLUTE_PATH = "HKEY_CLASSES_ROOT\\Applications\\EDITPLUS.EXE\\shell\\edit\\command";
	final static String DATA_TYPE = "REG_SZ";
	final static String[] EIDTPLUS_KYE_WORDS = {
			"EDITPLUS.EXE",
			"EDITPLUS1.EXE", "EDITPLUS2.EXE", "EDITPLUS3.EXE", "EDITPLUS4.EXE",
			"EDITPLUS5.EXE", "EDITPLUS6.EXE"};

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
	
	
	
	
	
	
	private static String readCmd(String path) throws IOException {
		Process p = Runtime.getRuntime().exec(
				"reg query \"" + path + "\" /ve /t " + DATA_TYPE);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p
				.getInputStream()));
		String line;
		StringBuffer buf = new StringBuffer();
		while (null != (line = reader.readLine())) {
			buf.append(line);
		}
		reader.close();
		return StringUtil.replace(buf.toString(), path, "").trim();
	}

}
