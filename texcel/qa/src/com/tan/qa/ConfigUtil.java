package com.tan.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.tan.util.TanUtil;


public class ConfigUtil {
	private ConfigUtil() {
	}

	
	private static String[] teams;
	
	/**
	 * 初始化 teams.
	 */
	public static void init() {
		teams = getConfig().split("\r\n");
	}
	
	/**
	 * 读取 config.txt 文件 .
	 * @return
	 */
	private static String getConfig() {
		String currentPath = BaseUtil.getCurrentPath();
		File file = new File(currentPath + 
				File.separatorChar + 
				"config.txt");
		
		TanUtil.println("Load :" + currentPath + 
				File.separatorChar + "config.txt");
		
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
			
			try {
				return new String(buf, 0, len, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		return "文件不存在#文件不存在";
	}
	
	/**
	 * 搜寻leader.
	 * @param name
	 * @return
	 */
	final public static String getLeader(String name) {
//		if (teams == null){
//			teams = getConfig().split("\r\n");
//		}
		init();

		if (teams == null || teams.length == 0) {
			return "文件没有记录 ";
		}

		for (String n : teams) {
			if (n.indexOf(name) >= 0) {
				int index = n.indexOf("#");
				if (index >= 0)
					return n.substring(0, index);
				else
					return "未找到Leader";
			}
		}
		return "没有Leader";
	}
	
	final public static String getOldPath() {
//		if (teams == null){
//			teams = getConfig().split("\r\n");
//		}
		
		init();
		
		if (teams == null || teams.length == 0) {
			return "文件没有记录 ";
		}

		for (String n : teams) {
			if (n.indexOf("OLDPATH") >= 0) {
				int index = n.indexOf("#");
				if (index >= 0)
					return n.substring(0, index);
				else
					return "NO OLDPATH";
			}
		}
		return "NO OLDPATH";
	}	
	
	public static void main(String[] args) {
	}
}
