package com.tan.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class ReadBook {
	public String name = "default";
	
	public final static void main(String[] args) throws Exception{
		System.out.println(readBookByIO());
	}

	public final static String readBookByIO() throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStream in = ReadBook.class.getResourceAsStream("/com/application/book.tan");
		byte[] b = new byte[8192];
		int len = -1;
		while ((len = in.read(b, 0, b.length)) != -1) {
			builder.append((new String(b, 0, len, "utf-8")));
		}
		return builder.toString();
	}
	
	public final static String readBookByName(final String name) throws IOException {
		StringBuilder builder = new StringBuilder();
		InputStream in = ReadBook.class.getResourceAsStream("/com/application/" + name+ ".tan");
		byte[] b = new byte[8192];
		int len = -1;
		while ((len = in.read(b, 0, b.length)) != -1) {
			builder.append((new String(b, 0, len, "utf-8")));
		}
		return builder.toString();
	}
	
	public final static String readBookByNio(final String p) {
		FileInputStream fis = null;
		FileChannel fc = null;
		try {
			fis = new FileInputStream(p);
			fc = fis.getChannel();
			
			ByteBuffer buf =ByteBuffer.allocate((int) fc.size());
			fc.read(buf);
			buf.flip();
			String content = new String(buf.array(), "utf-8");
			if (content != null && !"".equals(content)) {
				return content;
			}
		} catch (Exception e) {}
		finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}
}
