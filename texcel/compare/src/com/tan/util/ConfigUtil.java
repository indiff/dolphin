package com.tan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.tan.bean.Config;
import com.tan.consts.Consts;
import com.tan.qa.BaseUtil;

public class ConfigUtil {
	private ConfigUtil() {
	}

	private static Config config;

	public static Config getConfig() {
		if (config != null)
			return config;
		String currentPath = Thread.currentThread().getContextClassLoader().getResource("").getFile();
		File file = new File(currentPath + "config.txt");

		TanUtil.println(currentPath + "config.txt");

		if (file.isFile()) {
			FileInputStream in = null;
			FileChannel channle = null;
			ByteBuffer buffer = null;

			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			channle = in.getChannel();
			buffer = ByteBuffer.allocate(1024);

			buffer.clear();

			int len = -1;

			try {
				len = channle.read(buffer);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				in = null;
			}

			if (len == -1) return null;
				

			buffer.flip();

			byte[] buf = buffer.array();

			String value = new String(buf, 0, len);
			
//			demo(value);
			
			
			String[] values = value.split("=");
			
			for (String v : values) {
				System.out.println(v);
			}
		}

		return null;
	}
	
	public static String config2string() {
		String configPath = BaseUtil.getCurrentPath() + File.separator + "extensions-config.txt";
		
		File file = new File(configPath);
		if (!file.exists()) {
			TanUtil.warnln("文件不存在"); return null;
		}
		if (file.isDirectory()) {
			TanUtil.warnln("文件是目录"); return null;
		}
		TanUtil.println("Loading.... " + configPath);

		if (file.isFile()) {
			FileInputStream in = null;
			FileChannel channle = null;
			ByteBuffer buffer = null;

			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			channle = in.getChannel();
			buffer = ByteBuffer.allocate(1024);

			buffer.clear();

			int len = -1;

			try {
				len = channle.read(buffer);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				in = null;
			}

			if (len == -1) return null;

			buffer.flip();

			byte[] buf = buffer.array();
			
//			demo(value);
			
			try {
				return new String(buf, 0, len, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected static void demo(String value) {
		int firstPos = value.indexOf('=');
		if (firstPos == -1) return ;
		String name = value.substring(0, firstPos);
		
		System.out.print("name :" + name);
		int secondPos = -1 ;
		secondPos = value.indexOf(Consts.LINE, firstPos);
		while (secondPos != -1) {
			String v = value.substring(firstPos + 1, secondPos);
			System.out.println(" value: " + v);
			firstPos = value.indexOf("=", secondPos); if (firstPos == -1) break;
			System.out.print("name: " + value.substring(secondPos + 2, firstPos));
			secondPos = value.indexOf(Consts.LINE, firstPos);
		}
	}

	public static void main(String[] args) {
//		String[] extensions = config2string().split("#");
//		TanUtil.println(Arrays.toString(extensions));
		
		TanUtil.println("some=one".indexOf('='));
	}
}